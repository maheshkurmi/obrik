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

package com.fropsoft.sketch;

import java.awt.Shape;
import java.util.Vector;

import com.fopsoft.geometry.Stroke;

public class RecognizerMain
{
  /**
   * The list of recognizers that this recognizer tries to recognize.
   */
  private final Vector<ShapeRecognizer> recognizers;

  /**
   * Creates a new, empty recognizer.
   */
  public RecognizerMain()
  {
    recognizers = new Vector<ShapeRecognizer>();
  }

  /**
   * Adds the passed shape recognizer to the list of recognizers this will query
   * on the next call to {@link #classify(Stroke)}.
   * 
   * @param r The shape recognizer to add.
   */
  public void add(ShapeRecognizer r)
  {
    recognizers.add(r);
  }

  /**
   * Queries all registered recognizers and returns the shape corresponding to
   * the most probable match.
   * 
   * @param stroke The stroke to evaluate.
   * @return The shape corresponding to the most probably match.
   */
  public Shape classify(Stroke stroke)
  {
    double prob = 0;
    int high = -1;
    for (int i = 0; i < recognizers.size(); i++)
    {
      double next = recognizers.get(i).gague(stroke);
      if (next > prob)
      {
        prob = next;
        high = i;
      }
    }
    System.out.println(recognizers.get(high).getClass().getSimpleName());
    return recognizers.get(high).getShape();
  }
}
