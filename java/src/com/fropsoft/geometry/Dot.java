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
 * Y'know ... a dot.
 * 
 * @author jamoozy
 */
public class Dot extends AbstractShape
{
  /**
   * Creates a new dot from the passed strokes.
   * 
   * @param strokes
   */
  public Dot(Stroke... strokes)
  {
    super(strokes);
  }

  /**
   * Returns the centerpoint of this dot---it's location.
   * 
   * @return The centerpoint of this dot---it's location.
   */
  public Point2D getCenter()
  {
    return bbox.getCenter();
  }

  /**
   * Returns the X coordinate of this dot.
   * 
   * @return The X coordinate of this dot.
   */
  public int getCenterX()
  {
    return bbox.getCenter().getX();
  }

  /**
   * Returns the Y coordinate of this dot.
   * 
   * @return The Y coordinate of this dot.
   */
  public int getCenterY()
  {
    return bbox.getCenter().getY();
  }
}
