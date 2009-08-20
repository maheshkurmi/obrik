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

import com.fropsoft.geometry.Line;
import com.fropsoft.geometry.Shape;
import com.fropsoft.obrik.Box;

/**
 * Recognizes a box.
 * 
 * @author jamoozy
 * 
 * @see {@link Box}
 */
public class BoxRecognizer extends AbstractItemRecognizer
{
  /**
   * Creaes a new box recognizer.
   */
  public BoxRecognizer()
  {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.fropsoft.sketch.ItemRecognizer#gauge(com.fropsoft.geometry.Shape[])
   */
  public double gauge(Shape... shapes)
  {
    Line[] lines = Line.findLines(shapes);

    // See if they can be arranged head-to-toe.
    for (int i = 0; i < lines.length; i++)
    {
      for (int j = 0; j < lines.length; j++)
      {
        // TODO uhhhh ....
      }
    }

    return 0;
  }
}
