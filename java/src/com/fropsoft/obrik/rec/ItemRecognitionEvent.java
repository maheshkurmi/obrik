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

import com.fropsoft.obrik.item.Item;

import java.io.PrintWriter;

/**
 * @author jamoozy
 */
public abstract class ItemRecognitionEvent extends AbstractUserEvent
{
  /** The item this represents. */
  protected Item item;

  /**
   * Creates a new item recognition event.
   * @param i
   *          The item recognized.
   * @param t
   *          The time it was recognized.
   */
  public ItemRecognitionEvent(Item i, long t)
  {
    super(t);
    item = i;
  }

  /**
   * Writes the item to file in the standard way, given a name and data.  The
   * data is the body of the <code>&lt;item&gt;</code> tag.
   * @param out
   *          The place to write to.
   * @param data
   *          The item-specific body of the item tag.
   */
  public void record(PrintWriter out, String data)
  {
    out.printf("<item type=\"%s\">\n%s</item>\n",
                item.getClass().getName(), data);
  }
}
