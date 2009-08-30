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

package com.fropsoft.geometry;

/**
 * This is a 2D point with a timestamp and a velocity. This is equivalent to a
 * {@link Point2DT}, except that it also has a velocity that can be accessed
 * with the {@link #getV()} method. This velocity is in pixels-per-second.
 *
 * @author jamoozy
 *
 */
public class Point2DTV extends Point2DT
{
  /**
   * The speed of the pen at this point in p/s.
   */
  protected double v;

  /**
   * Creates a new {@link Point2DTV} with the data from a {@link Point2DT}
   * object and a velocity of 0.
   *
   * @param p2dt
   */
  public Point2DTV(Point2DT p2dt)
  {
    this(p2dt.x, p2dt.y, p2dt.t, 0);
  }

  /**
   * Creates a new {@link Point2DTV} object with the data from a
   * {@link Point2DT} object and a velocity of v.
   *
   * @param p2dt
   * @param v
   */
  public Point2DTV(Point2DT p2dt, double v)
  {
    this(p2dt.x, p2dt.y, p2dt.t, v);
  }

  /**
   * Creates a new instance at (x,y) at time t voind v pixels-per-second.
   *
   * @param x The x coordinate
   * @param y The y coordinate
   * @param t The timestamp
   * @param v The speed in pixels-per-second.
   */
  public Point2DTV(int x, int y, long t, double v)
  {
    super(x, y, t);
    this.v = v;
  }

  /**
   * Returns the speed of the pen when it made this point in pixels-per-second.
   *
   * @return The speed of the pen when it made this point in pixels-per-second.
   */
  public double getV()
  {
    return v;
  }

  /*
   * (non-Javadoc)
   * @see com.fopsoft.geometry.Point2DT#clone()
   */
  @Override
  public Point2DTV clone()
  {
    return new Point2DTV(x, y, t, v);
  }

  /*
   * (non-Javadoc)
   * @see com.fopsoft.geometry.Point2DT#toString()
   */
  @Override
  public String toString()
  {
    return String.format("(%d,%d) @ %d going %.2f p/s", x, y, t, v);
  }
}
