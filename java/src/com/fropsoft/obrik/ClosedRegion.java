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

import com.fropsoft.geometry.Line;
import com.fropsoft.geometry.Point2D;

/**
 * A closed region is an object that can be anchored or not.
 *
 * @author jamoozy
 */
public class ClosedRegion extends AbstractItem
{
  /**
   * The points that outline this region.  Attained by averaging the closest
   * parts of the line segments.
   */
  private final Point2D[] points;

  /**
   * Creates a new closed region that was created by the passed lines.
   *
   * @param lines
   *          The lines that created this region.
   */
  public ClosedRegion(Line... lines)
  {
    super(lines);

    // Create the points that ouline this line.
    points = new Point2D[lines.length];
    points[0] =
        lines[0].getStartPoint().midpoint(lines[lines.length-1].getEndPoint());
    for (int i = 1; i < lines.length; i++)
      points[i] = lines[i-1].getEndPoint().midpoint(lines[i].getStartPoint());
  }

  /**
   * Returns the points that define this region.
   *
   * @return The points that define this region.
   */
  public Point2D[] getPoints()
  {
    return points;
  }
}
