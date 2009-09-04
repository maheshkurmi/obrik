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
 * This gives a basic implementation for all the methods in the {@link Item}
 * interface.
 *
 * @author jamoozy
 */
public abstract class AbstractItem implements Item
{
  /** The shapes that make up this Item. */
  protected Shape[] shapes;

  /** The bounding box of this item. */
  protected Bounds bbox;

  /** The position of this item. */
  protected Point2D position;

  /**
   * Adds the shapes to the protected {@link #shapes} array.
   *
   * @param shapes
   *          The shapes to add.
   */
  protected AbstractItem(Shape... shapes)
  {
    this.shapes = new Shape[shapes.length];
    for (int i = 0; i < shapes.length; i++)
      this.shapes[i] = shapes[i];
    bbox = Bounds.createFromShapes(shapes);
    position = bbox.getCenter();
  }

  /* (non-Javadoc)
   * @see com.fropsoft.obrik.Item#getShapes()
   */
  public Shape[] getShapes()
  {
    return shapes;
  }

  /* (non-Javadoc)
   * @see com.fropsoft.obrik.Item#getPosition()
   */
  public Point2D getPosition()
  {
    return position;
  }

  /* (non-Javadoc)
   * @see com.fropsoft.obrik.Item#getBBox()
   */
  public Bounds getBBox()
  {
    return bbox;
  }

  /* (non-Javadoc)
   * @see com.fropsoft.obrik.Item#updatePosition()
   */
  public void updatePosition(Point2D point)
  {
    position = point;
  }

  /* (non-Javadoc)
   * @see com.fropsoft.obrik.Item#resetPosition()
   */
  public void resetPosition()
  {
    position = bbox.getCenter();
  }
}
