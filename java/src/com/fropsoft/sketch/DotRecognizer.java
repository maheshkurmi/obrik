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

import com.fropsoft.geometry.Dot;
import com.fropsoft.geometry.Shape;
import com.fropsoft.geometry.Stroke;

public class DotRecognizer implements ShapeRecognizer
{
  /*
   * (non-Javadoc)
   * 
   * @see
   * com.fropsoft.sketch.ShapeRecognizer#gague(com.fropsoft.geometry.Stroke)
   */
  public double gauge(Stroke stroke)
  {
    if (stroke.numPoints() < 4)
      return 1.0;
    
    return 1.0 / stroke.numPoints();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.fropsoft.sketch.ShapeRecognizer#getShape()
   */
  public Shape makeShape(Stroke stroke)
  {
    return new Dot(stroke);
  }
}
