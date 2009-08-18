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

import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;

import com.fropsoft.geometry.Point2DT;
import com.fropsoft.geometry.Shape;
import com.fropsoft.geometry.Stroke;
import com.fropsoft.sketch.DotRecognizer;
import com.fropsoft.sketch.LineRecognizer;
import com.fropsoft.sketch.RecognizerMain;

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
  private final RecognizerMain recognizer;

  /**
   * Create a new Obrik game state.
   */
  public State()
  {
    points = new Vector<Point2DT>();
    stroke = null;
    shapes = new Vector<Shape>();
    recognizer = new RecognizerMain();
    recognizer.add(new DotRecognizer());
    recognizer.add(new LineRecognizer());
  }

  public void mouseDrag(MouseEvent e)
  {
    points.add(new Point2DT(e.getX(), e.getY(), System.currentTimeMillis()));
  }

  public void mouseClicked(MouseEvent e)
  {
    // XXX Up & down, so do no action. (for now) --- maybe make a Dot?
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

  public int[] getStrokeXCoords()
  {
    int[] xs = new int[points.size()];
    for (int i = 0; i < xs.length; i++)
      xs[i] = points.get(i).getX();
    return xs;
  }

  public int[] getStrokeYCoords()
  {
    int[] ys = new int[points.size()];
    for (int i = 0; i < ys.length; i++)
      ys[i] = points.get(i).getY();
    return ys;
  }

  public int getNumPoints()
  {
    return points.size();
  }

  public Point2DT lastPoint()
  {
    if (points.size() > 0)
      return points.get(points.size() - 1).clone();
    else
      return null;
  }

  private void makeStroke()
  {
    stroke = new Stroke(points.toArray(new Point2DT[] {}));
    points.clear();
  }

  /**
   * Performs shape recognition on the point data.
   */
  public void recognizeStroke()
  {
    if (stroke != null)
    {
      shapes.add(recognizer.classify(stroke));
      System.out.println("Probably a "
          + shapes.get(shapes.size() - 1).getClass().getSimpleName());
      stroke = null;
    }
  }

  public Iterator<Shape> shapeIterator()
  {
    return shapes.iterator();
  }
}
