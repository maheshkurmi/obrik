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
 * Y'know ... a line.
 * 
 * @author jamoozy
 */
public class Line extends AbstractShape
{
  /**
   * Creates a new Line.
   * 
   * @param strokes
   */
  public Line(Stroke... strokes)
  {
    super(strokes);
  }

  /**
   * Returns the starting point of this line.
   * 
   * @return The starting piont of this line.
   */
  public Point2D getStartPoint()
  {
    return strokes.get(0).get(0);
  }

  /**
   * Returns the ending point of this line.
   * 
   * @return The ending piont of this line.
   */
  public Point2D getEndPoint()
  {
    Stroke s = strokes.get(strokes.size() - 1);
    return s.get(s.numPoints() - 1);
  }
}
