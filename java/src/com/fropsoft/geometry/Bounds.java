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
 * This can be thought of as a bounding box.
 * 
 * @author jamoozy
 */
public class Bounds
{
  /**
   * Minimum (x,y).
   */
  private Point2D min;

  /**
   * Maximum (x,y).
   */
  private Point2D max;

  /**
   * Creates a new Bounds object.
   * 
   * @param minX Minimum x value.
   * @param minY Minimum y value.
   * @param maxX Maximum x value.
   * @param maxY Maximum y value.
   */
  public Bounds(int minX, int minY, int maxX, int maxY)
  {
    min = new Point2D(minX, minY);
    max = new Point2D(maxX, maxY);
  }

  /**
   * Returns the minimum x value.
   * 
   * @return The minimum x value.
   */
  public int getMinX()
  {
    return min.getX();
  }

  /**
   * Returns the minimum y value.
   * 
   * @return The minimum y value.
   */
  public int getMinY()
  {
    return min.getY();
  }

  /**
   * Returns the maximum x value.
   * 
   * @return The maximum x value.
   */
  public int getMaxX()
  {
    return max.getX();
  }

  /**
   * Returns the maximum y value.
   * 
   * @return The maximum y value.
   */
  public int getMaxY()
  {
    return max.getY();
  }
}
