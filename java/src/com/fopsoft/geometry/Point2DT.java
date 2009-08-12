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

package com.fopsoft.geometry;

/**
 * Represents a timestamped point. This is very similar to the {@link Point2D}
 * class, with the exception that this also has a timestamp, which can be
 * accessed via the {@link #getT()} method.
 * 
 * @author jamoozy
 */
public class Point2DT extends Point2D
{
  /**
   * The timestamp of this point.
   */
  protected long t;

  /**
   * Creates a new timestamped point.
   * 
   * @param x The x coordinate of the new point.
   * @param y The y coordinate of the new point.
   * @param t The timestamp of the new point.
   */
  public Point2DT(int x, int y, long t)
  {
    super(x, y);
    this.t = t;
  }

  /**
   * Returns the timestamp of this point.
   * 
   * @return The timestamp
   */
  public long getT()
  {
    return t;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#clone()
   */
  @Override
  public Point2DT clone()
  {
    return new Point2DT(x, y, t);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return String.format("(%d,%d) @ %d", x, y, t);
  }
}
