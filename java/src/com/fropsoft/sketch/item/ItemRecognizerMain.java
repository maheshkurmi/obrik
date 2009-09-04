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

import java.util.Vector;

import com.fropsoft.geometry.Shape;
import com.fropsoft.obrik.item.Item;

/**
 * This class recognizes items given a list of shapes.
 * 
 * @author jamoozy
 */
public class ItemRecognizerMain
{
  /**
   * The list of recognizers that this recognizer uses to recognize.
   */
  private final Vector<ItemRecognizer> recognizers;

  /**
   * Creates a new, empty recognizer.
   */
  public ItemRecognizerMain()
  {
    recognizers = new Vector<ItemRecognizer>();
  }

  /**
   * Adds the passed item recognizer to the list of recognizers this will query
   * on the next call to {@link #classify(Shape...)}.
   * 
   * @param r The shape recognizer to add.
   */
  public void add(ItemRecognizer r)
  {
    recognizers.add(r);
  }

  /**
   * Queries all registered recognizers and returns the item corresponding to
   * the most probable match in the list.
   * 
   * @param shapes The shapes to evaluate.
   * @return The item corresponding to the most probable match.
   */
  public Item classify(Shape... shapes)
  {
    double prob = 0;
    int high = -1;
    for (int i = 0; i < recognizers.size(); i++)
    {
      ItemRecognizer r = recognizers.get(i);
      double next = r.gauge(shapes);
      System.out.printf("%s: %1.3f\n", r.getClass().getSimpleName(), next);
      if (next > prob)
      {
        prob = next;
        high = i;
      }
    }

    if (prob <= 0)
      return null;

    return recognizers.get(high).getItem(shapes);
  }
}
