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

import com.fropsoft.geometry.Point2D;

/**
 * @author jamoozy
 */
public abstract class StrokeEvent extends AbstractUserEvent
{
  /** The point where this occured. */
  protected Point2D point;

  /**
   * Creates a new stroke event at x,y.
   * @param x
   *          The x coordinate of this event.
   * @param y
   *          The y coordinate of this event.
   * @param t
   *          The time this occured.
   */
  protected StrokeEvent(int x, int y, long t)
  {
    super(t);

    point = new Point2D(x,y);
  }

  /**
   * Returns the point where this occured.
   * @return The point where this occured.
   */
  public Point2D getPoint()
  {
    return point;
  }
}
