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

import java.util.Iterator;
import java.util.Vector;

import com.fropsoft.geometry.Point2DT;
import com.fropsoft.geometry.Shape;
import com.fropsoft.geometry.Stroke;
import com.fropsoft.sketch.ActionRecognizerMain;
import com.fropsoft.sketch.AnchorActionRecognizer;
import com.fropsoft.sketch.AnchorRecognizer;
import com.fropsoft.sketch.ClosedRegionRecognizer;
import com.fropsoft.sketch.DotRecognizer;
import com.fropsoft.sketch.ItemRecognizerMain;
import com.fropsoft.sketch.LineRecognizer;
import com.fropsoft.sketch.ShapeRecognizerMain;

/**
 * Keeps track of the state of the Obrik "game".
 *
 * @author jamoozy
 */
public class State
{
  /** Points collected from the user via one of the GUIs. */
  private final Vector<Point2DT> points;

  /** Stroke created when the final user point comes from the GUI.  */
  private Stroke stroke;

  /** The shapes waiting to be recognized as something.  */
  private final Vector<Shape> shapes;

  /** The shape recognition module.  */
  private final ShapeRecognizerMain srec;

  /** The items waiting to be simulated.  */
  private final Vector<Item> items;

  /** The item recognizer.  */
  private final ItemRecognizerMain irec;
  
  /** Global action recognizer. */
  private final ActionRecognizerMain grec;

  /**
   * Create a new Obrik game state.
   */
  public State()
  {
    points = new Vector<Point2DT>(200);
    stroke = null;

    shapes = new Vector<Shape>();
    srec = new ShapeRecognizerMain();
    srec.add(new DotRecognizer());
    srec.add(new LineRecognizer());

    items = new Vector<Item>();
    irec = new ItemRecognizerMain();
    irec.add(new ClosedRegionRecognizer());
    // irec.add(new BoxRecognizer());
    irec.add(new AnchorRecognizer());

    grec = new ActionRecognizerMain();
    grec.add(new AnchorActionRecognizer());
  }

  /**
   * Mouse clicked at (x,y).
   *
   * @param x
   *          X coordinate of mouse.
   * @param y
   *          Y coordinate of mouse.
   */
  public void mouseClicked(int x, int y)
  {
    // Up & down, so do no action.
    // XXX maybe add a dot shape or click gesture? interact?
  }

  /**
   * Mouse entered screen at (x,y).  Starts a new stroke if the button is
   * down.  Will throw a RuntimeException if the previous stroke hasn't been
   * handled yet.
   *
   * @param x
   *          X coordinate of mouse.
   * @param y
   *          Y coordinate of mouse.
   * @param isPressed
   *          Whether the mouse has a button pressed.
   */
  public void mouseEntered(int x, int y, boolean isPressed)
  {
    if (isPressed)
    {
      if (points.size() > 0)
        throw new RuntimeException("points not empty!");
      points.add(new Point2DT(x, y, System.currentTimeMillis()));
    }
  }

  /**
   * Mouse exited the screen at (x,y).  Ends the current stroke, if the button
   * is down.
   *
   * @param x
   *          X coordinate of mouse.
   * @param y
   *          Y coordinate of mouse.
   * @param isPressed
   *          Whether the mouse has a button pressed.
   */
  public void mouseExited(int x, int y, boolean isPressed)
  {
    if (isPressed)
    {
      points.add(new Point2DT(x, y, System.currentTimeMillis()));
      makeStroke();
    }
  }

  /**
   * Mouse pressed at (x,y).
   *
   * @param x
   *          X coordinate of mouse.
   * @param y
   *          Y coordinate of mouse.
   */
  public void mousePressed(int x, int y)
  {
    if (points.size() > 0)
      throw new RuntimeException("points not empty!");
    points.add(new Point2DT(x, y, System.currentTimeMillis()));
  }

  /**
   * Mouse released at (x,y).
   *
   * @param x
   *          X coordinate of mouse.
   * @param y
   *          Y coordinate of mouse.
   */
  public void mouseReleased(int x, int y)
  {
    points.add(new Point2DT(x, y, System.currentTimeMillis()));
    makeStroke();
  }

  /**
   * Mouse moved to (x,y) with no button pressed.
   *
   * @param x
   *          X coordinate of mouse.
   * @param y
   *          Y coordinate of mouse.
   */
  public void mouseMoved(int x, int y)
  {
    // No movement action (when mouse unpressed).
  }

  /**
   * Mouse dragged to (x,y).
   *
   * @param x
   *          X coordinate of mouse after drag.
   * @param y
   *          Y coordinate of mouse after drag.
   */
  public void mouseDrag(int x, int y)
  {
    points.add(new Point2DT(x, y, System.currentTimeMillis()));
  }

  /**
   * Returns the X coordinates of the stroke in an array. This is here because
   * it's useful for many "draw poly-line" functions, including the one in
   * Swing.
   *
   * @return The array of x coordinates from the user's stroke.
   */
  public int[] buildStrokeXCoords()
  {
    int[] xs = new int[points.size()];
    for (int i = 0; i < xs.length; i++)
      xs[i] = points.get(i).getX();
    return xs;
  }

  /**
   * Returns the Y coordinates of the stroke in an array. This is here because
   * it's useful for many "draw poly-line" functions, including the one in
   * Swing.
   *
   * @return The array of y coordinates from the user's stroke.
   */
  public int[] buildStrokeYCoords()
  {
    int[] ys = new int[points.size()];
    for (int i = 0; i < ys.length; i++)
      ys[i] = points.get(i).getY();
    return ys;
  }

  /**
   * Returns the number of points in the user's current stroke.
   *
   * @return The number of points in the user's current stroke.
   */
  public int getNumPoints()
  {
    return points.size();
  }

  /**
   * Returns the last (drawn) point in the user's stroke.
   *
   * @return The last (drawn) point in the user's stroke.
   */
  public Point2DT getLastPoint()
  {
    if (points.size() > 0)
      return points.get(points.size() - 1);
    else
      return null;
  }

  /**
   * Turn the stroke point data into a stroke.
   */
  private void makeStroke()
  {
    stroke = new Stroke(points.toArray(new Point2DT[] {}));
    System.out.println("There were " + points.size() + " points.");
    points.clear();
  }

  /**
   * Removes all the shapes that made up the passed item from the {@link
   * #shapes} array.
   */
  private void removeItemsShapes(Item item)
  {
    for (Shape s : item.getShapes())
      if (shapes.contains(s))
        shapes.remove(s);
  }

  /**
   * Performs shape recognition on the point data.
   *
   * @return {@link true} if something was recognizeed.
   */
  public boolean recognizeStroke()
  {
    if (stroke != null)
    {
      Shape shape = srec.classify(stroke);
      if (shape != null)
      {
        shapes.add(shape);
        System.out.println("Probably a " + shape.getClass().getSimpleName());
        stroke = null;

        Shape[] tmp = new Shape[shapes.size()];
        tmp = shapes.toArray(tmp);
        Item item = irec.classify(tmp);

        if (item != null)
        {
          removeItemsShapes(item);
          items.add(item);
          System.out.println("Probably a " + item.getClass().getSimpleName());

          grec.classify(items);
        }
        return true;
      }
    }
    return false;
  }

  /* (non-Javadoc)
   * @see java.util.Vector#iterator()
   */
  public Iterator<Shape> shapeIterator()
  {
    return shapes.iterator();
  }

  /* (non-Javadoc)
   * @see java.util.Vector#iterator()
   */
  public Iterator<Item> itemIterator()
  {
    return items.iterator();
  }

  /** The simulation interface. */
  private JBox2DDispatcher sim = null;

  /**
   * Initializes the simulation.
   */
  public void initSim()
  {
    if (sim != null) return;

    System.out.println("initSim()");

    sim = new JBox2DDispatcher();

    Item[] items = new Item[this.items.size()];
    sim.add(this.items.toArray(items));
  }

  /**
   * Does one step in the simulation.
   * @return Whether the screen needs to be repainted.
   */
  public boolean doSimStep()
  {
    return sim != null && sim.step();
  }

  /**
   * Determines if the simulation is active.
   * @return <code>true</code> if it is.
   */
  public boolean simShouldRun()
  {
    return sim != null;
  }

  /**
   * Destroys the simulation.
   */
  public void destroySim()
  {
    if (sim == null) return;

    System.out.println("destroySim()");

    for (Item i : items)
      i.resetPosition();
    sim = null;
  }
}
