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
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

/**
 * Meant to create and dispatch a JBox2D simulation. This acts as the bridge
 * betweden Obrik's internal representation of items and the way it needs to be
 * to work in JBox2D.
 * 
 * @author jamoozy
 */
public class JBox2DDispatcher
{
  /**
   * The bodies to simulate (do I need these?).
   */
  private final Vector<Body> bodies;

  /**
   * The JBox2D world.
   */
  private final World world;
  
  /**
   * The "body" that acts as the barrier at the bottom of the screen.
   */
  private final Body groundBody;

  /**
   * Creates a new dispatcher.
   */
  public JBox2DDispatcher()
  {
    bodies = new Vector<Body>(50);

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
    BodyDef bodyDef = new BodyDef();
    bodyDef.position.set(0.0f, 4.0f);
    Body body = world.createBody(bodyDef);
    
    PolygonDef shapeDef = new PolygonDef();
    shapeDef.setAsBox(1.0f, 1.0f);
    shapeDef.density = 1.0f;
    shapeDef.friction = 0.3f;
    body.createShape(shapeDef);
    body.setMassFromShapes();
    
    bodies.add(body);
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
    float timeStep = 1.0f / 60.0f;
    int iterations = 10;
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
