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

package com.fropsoft.sketch.item;

import com.fropsoft.geometry.Shape;
import com.fropsoft.obrik.Item;

/**
 * 
 * @author jamoozy
 */
public abstract class AbstractItemRecognizer implements ItemRecognizer
{
  /**
   * The item that was found on the last call to {@link #gauge(Shape...)}.
   */
  private Item item;

  /**
   * Sets the item.
   * 
   * @param item The item.
   */
  protected void setItem(Item item)
  {
    this.item = item;
  }

  /**
   * Returns the item.
   * 
   * @return The item.
   */
  protected Item getItem()
  {
    return item;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.fropsoft.sketch.ItemRecognizer#getItem(com.fropsoft.geometry.Shape[])
   */
  public Item getItem(Shape... shapes)
  {
    if (item != null)
      return item;

    if (gauge(shapes) <= 0)
      throw new NoItemFoundException(" ");

    return item;
  }
}
