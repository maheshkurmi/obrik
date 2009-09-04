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

package com.fropsoft.obrik.item;

import com.fropsoft.geometry.Bounds;
import com.fropsoft.geometry.Point2D;
import com.fropsoft.geometry.Shape;

/**
 * An {@link Item} is something that exists on the Obrik canvas that has
 * meaning.
 *
 * @author jamoozy
 */
public interface Item
{
  /**
   * Returns the shapes that made up this {@link Item}.
   * @return The shapes that make up this {@link Item}.
   */
  public Shape[] getShapes();

  /**
   * Returns the position of this item.
   * @return The position of this item.
   */
  public Point2D getPosition();

  /**
   * Returns the bounding box of this item.
   * @return The bounding box of this item.
   */
  public Bounds getBBox();

  /**
   * Updates the Position of this item.
   */
  public void updatePosition(Point2D point);

  /**
   * Resets the position of this object to where it was drawn.
   */
  public void resetPosition();
}
