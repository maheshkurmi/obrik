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

import com.fropsoft.sketch.AnchorRecognizer;
import com.fropsoft.sketch.BoxRecognizer;

import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;

import com.fropsoft.geometry.Point2DT;
import com.fropsoft.geometry.Shape;
import com.fropsoft.geometry.Stroke;
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
  /**
   * Points collected from the user via one of the GUIs.
   */
  private final Vector<Point2DT> points;

  /**
   * Stroke created when the final user point comes from the GUI.
   */
  private Stroke stroke;

  /**
   * The shapes waiting to be recognized as something.
   */
  private final Vector<Shape> shapes;

  /**
   * The shape recognition module.
   */
  private final ShapeRecognizerMain srec;

  /**
   * The items waiting to be simulated.
   */
  private final Vector<Item> items;

  /**
   * The item recognizer.
   */
  private final ItemRecognizerMain irec;

  /**
   * Create a new Obrik game state.
   */
  public State()
  {
    points = new Vector<Point2DT>();
    stroke = null;

    shapes = new Vector<Shape>();
    srec = new ShapeRecognizerMain();
    srec.add(new DotRecognizer());
    srec.add(new LineRecognizer());

    items = new Vector<Item>();
    irec = new ItemRecognizerMain();
    irec.add(new BoxRecognizer());
    irec.add(new AnchorRecognizer());

    // TODO create and init a global recognizer.
  }

  public void mouseDrag(MouseEvent e)
  {
    points.add(new Point2DT(e.getX(), e.getY(), System.currentTimeMillis()));
  }

  public void mouseClicked(MouseEvent e)
  {
    // Up & down, so do no action.
    // XXX maybe add a dot shape or click gesture? interact?
  }

  public void mouseEntered(MouseEvent e)
  {
    if (e.getButton() != MouseEvent.NOBUTTON)
    {
      if (points.size() > 0)
        throw new RuntimeException("points not empty!");
      points.add(new Point2DT(e.getX(), e.getY(), System.currentTimeMillis()));
    }
  }

  public void mousePressed(MouseEvent e)
  {
    if (points.size() > 0)
      throw new RuntimeException("points not empty!");
    points.add(new Point2DT(e.getX(), e.getY(), System.currentTimeMillis()));
  }

  public void mouseExited(MouseEvent e)
  {
    if (e.getButton() != MouseEvent.NOBUTTON)
    {
      points.add(new Point2DT(e.getX(), e.getY(), System.currentTimeMillis()));
      makeStroke();
    }
  }

  public void mouseReleased(MouseEvent e)
  {
    points.add(new Point2DT(e.getX(), e.getY(), System.currentTimeMillis()));
    makeStroke();
  }

  public void mouseMoved(MouseEvent e)
  {
    // No movement action (when mouse unpressed).
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
    points.clear();
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
        System.out.println("Probably a " + shape.getClass());
        stroke = null;

        if (shapes.size() > 0)
        {
          Item item = irec.classify(shapes.toArray(new Shape[] {}));
          if (item != null)
          {
            items.add(item);
            System.out.println("Probably a " + item.getClass());
          }
        }
        return true;
      }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Vector#iterator()
   */
  public Iterator<Shape> shapeIterator()
  {
    return shapes.iterator();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Vector#iterator()
   */
  public Iterator<Item> itemIterator()
  {
    return items.iterator();
  }
}
