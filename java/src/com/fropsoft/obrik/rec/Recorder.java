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

package com.fropsoft.obrik.rec;

import com.fropsoft.geometry.Dot;
import com.fropsoft.geometry.Line;
import com.fropsoft.geometry.Shape;

import com.fropsoft.obrik.item.Anchor;
import com.fropsoft.obrik.item.ClosedRegion;
import com.fropsoft.obrik.item.Item;

import java.util.Vector;

/**
 * @author jamoozy
 */
public class Recorder
{
  /** The points of the current stroke. */
  private Vector<UserEvent> events;

  /**
   * Creates a new recorder.
   */
  public Recorder()
  {
    events = new Vector<UserEvent>(10000);
  }

  /**
   * Starts a stroke.
   *
   * @param x
   *          The x coordinate of the newly-started stroke.
   * @param y
   *          The y coordinate of the newly-started stroke.
   */
  public void startStroke(int x, int y)
  {
    events.add(new StrokeStartEvent(x, y, System.currentTimeMillis()));
  }

  /**
   * Continues a stroke by adding this point.
   *
   * @param x
   *          The next x coordinate of the current stroke.
   * @param y
   *          The next y coordinate of the current stroke.
   */
  public void addPoint(int x, int y)
  {
    events.add(new StrokeContinueEvent(x, y, System.currentTimeMillis()));
  }

  /**
   * Finishes a stroke at this point.
   *
   * @param x
   *          The final x coordinate of the current stroke.
   * @param y
   *          The final y coordinate of the current stroke.
   */
  public void endPoint(int x, int y)
  {
    events.add(new StrokeEndEvent(x, y, System.currentTimeMillis()));
  }

  /**
   * Adds a run button event.
   */
  public void addRun()
  {
    events.add(new RunButtonEvent(System.currentTimeMillis()));
  }

  /**
   * Adds a clear button event.
   */
  public void addClear()
  {
    events.add(new ClearButtonEvent(System.currentTimeMillis()));
  }

  /**
   * Adds a reset button event.
   */
  public void addReset()
  {
    events.add(new ResetButtonEvent(System.currentTimeMillis()));
  }

  /**
   * Adds a shape recognition event.
   * @param s
   *          The shape that was recognized.
   */
  public void addShape(Shape s)
  {
    if (s instanceof Dot)
    {
      System.out.println("Dots aren't recorded.");
    }
    else if (s instanceof Line)
    {
      events.add(new LineEvent((Line)s, System.currentTimeMillis()));
    }
    else
    {
      throw new RuntimeException("Don't know of " + s.getClass().getName());
    }
  }

  /**
   * Adds an item recognition event.
   * @param i
   *          The item that was recognized.
   */
  public void addItem(Item i)
  {
    if (i instanceof ClosedRegion)
    {
      events.add(new ClosedRegionEvent((ClosedRegion)i,
                    System.currentTimeMillis()));
    }
    else if (i instanceof Anchor)
    {
      events.add(new AnchorEvent((Anchor)i, System.currentTimeMillis()));
    }
    else
    {
      throw new RuntimeException("Don't know of " + i.getClass().getName());
    }
  }
}