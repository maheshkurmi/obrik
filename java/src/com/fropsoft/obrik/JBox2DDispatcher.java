/*
 * This file is part of Obrik, a free body diagram simulator.
 * Copyright (C) 2010  Andrew Correa <jamoozy@gmail.com>
 *                     Ali Mohammad <alawibaba@gmail.com>
 *
 * Obrik is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.fropsoft.obrik;

import java.util.Vector;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.MassData;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import com.fropsoft.geometry.Point2D;

/**
 * Meant to create and dispatch a JBox2D simulation. This acts as the bridge
 * betweden Obrik's internal representation of items and the way it needs to be
 * to work in JBox2D.
 *
 * @author jamoozy
 */
public class JBox2DDispatcher
{
  /** The bodies to simulate (do I need these?). */
  private final Vector<Body> bodies;

  /** The JBox2D world. */
  private final World world;
  
  /** The "body" that acts as the barrier at the bottom of the screen. */
  private final Body groundBody;

  /** The amount of time each step takes. */
  private final float timeStep = 1.0f / 60.0f;
  
  /** Number of iterations in a run. */
  private final int iterations = 10;
  
  /** Linear factor in Vec2 -> Point2D conversion. */
  public final float m;
  
  /** Linear offset in Vec2 -> Point2D conversion. */ 
  public final float b;

  /**
   * Creates a new {@link JBox2DDispatcher} with default values.
   */
  public JBox2DDispatcher()
  {
    this(8.0f, 0.0f);
  }
  
  /**
   * Creates a new dispatcher with the given scaling values for
   * {@link Point2D} to {@link Vec2} conversion.
   * 
   * @param scaleFactor
   *          'm' in 'y = mx+b' linear scaling to JBox2D format.
   * @param offset
   *          'b' in 'y = mx+b' linear scaling to JBox2D format.
   */
  public JBox2DDispatcher(float scaleFactor, float offset)
  {
    bodies = new Vector<Body>(50);
    m = scaleFactor;
    b = offset;

    world = new World(new AABB(new Vec2(-100.0f, -100.0f),
                               new Vec2(100.0f, 100.0f)),
                      new Vec2(0.0f, -10.0f), true);

    BodyDef groundBodyDef = new BodyDef();
    groundBodyDef.position.set(0.0f, -100.0f);
    groundBody = world.createBody(groundBodyDef);
    PolygonDef groundShapeDef = new PolygonDef();
    groundShapeDef.setAsBox(50.0f, 10.0f);
    groundBody.createShape(groundShapeDef);
  }
  
  /**
   * Resets the state of the world as though it was newly created.
   */
  public void reset()
  {
    for (Body body : bodies)
      world.destroyBody(body);
    bodies.clear();
  }

  /**
   * Loads an item into the dispatcher.
   * 
   * @param item The item to load.
   */
  public void add(Item item)
  {
    if (!(item instanceof ClosedRegion))
    {
      System.err.printf("Unknown class \"%s\" in JBox2DDispatcher#add(Item)\n",
          item.getClass().getSimpleName());
      return;
    }
    ClosedRegion cr = (ClosedRegion)item;
    
    BodyDef bodyDef = new BodyDef();
    bodyDef.userData = item;
    bodyDef.position = toVec2(item.getPosition());
    if (cr.isAnchored())
    {
      MassData md = new MassData();
      md.center = bodyDef.position;
      md.I = 0.5f;
      md.mass = 0.5f;
      bodyDef.massData = md;
    }
    Body body = world.createBody(bodyDef);

    PolygonDef shapeDef = new PolygonDef();
    Point2D[] points = cr.getPoints();
    for (int i = 0; i < points.length; i++)
      shapeDef.addVertex(toVec2(points[i]));
    shapeDef.density = 1.0f;
    shapeDef.friction = 0.3f;
    body.createShape(shapeDef);
    body.setMassFromShapes();

    bodies.add(body);
  }

  /**
   * Convert the JBox2D {@link Vec2} object into an Obrik {@link Point2D}
   * object.
   * @param vec The JBox2D representation.
   * @return The Obrik representation.
   */
  public Point2D toPoint2D(Vec2 vec)
  {
    return new Point2D(Math.round(m * vec.x + b),
                       Math.round(m * vec.y + b));
  }

  /**
   * Convert the Obrik {@link Point2D} object into a JBox2D {@link Vec2}
   * object.
   * @param vec The Obrik representation.
   * @return The JBox2D representation.
   */
  public Vec2 toVec2(Point2D point)
  {
    return new Vec2((point.getX() - b) / m,
                    (point.getY() - b) / m);
  }

  /**
   * Loads a series of itemes into the dispatcher.
   * 
   * @param items The series of items to load.
   */
  public void add(Item... items)
  {
    for (Item item : items)
      add(item);
  }

  /**
   * Runs the JBox2D simulation.
   */
  public void run()
  {
    for (int i = 0; i < 60; ++i)
    {
      for (Body body : bodies)
      {
        world.step(timeStep, iterations);
        Vec2 position = body.getPosition();
        float angle = body.getAngle();
        System.out.printf("%4.2f %4.2f %4.2f\n", position.x, position.y, angle);
      }
    }
  }
}
