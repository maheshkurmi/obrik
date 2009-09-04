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

package com.fropsoft.sketch.shape;

import java.util.Iterator;

import com.fropsoft.geometry.Angle;
import com.fropsoft.geometry.Line;
import com.fropsoft.geometry.Point2D;
import com.fropsoft.geometry.Point2DTV;
import com.fropsoft.geometry.Shape;
import com.fropsoft.geometry.Stroke;

/**
 * Recognizes a Line.
 * 
 * @author jamoozy
 */
public class LineRecognizer implements ShapeRecognizer
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
      return 0.0;

    Iterator<Point2DTV> iter = stroke.iterator();

    Point2D last = iter.next();
    Point2D curr = iter.next();

    Angle lastAngle, minAngle, maxAngle, angleDiff;
    Angle currAngle = minAngle = maxAngle = last.angleTo(curr);

    int penalties = 0;

    while (iter.hasNext())
    {
      last = curr;
      lastAngle = currAngle;

      curr = iter.next();
      currAngle = last.angleTo(curr);

      angleDiff = currAngle.angleBetween(lastAngle);
      if (angleDiff.getValue() > Math.PI / 3)
        penalties++;
    }

    return 1 - (minAngle.angleBetween(maxAngle).getValue() / Math.PI);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.fropsoft.sketch.ShapeRecognizer#makeShape(com.fropsoft.geometry.Stroke)
   */
  public Shape makeShape(Stroke stroke)
  {
    return new Line(stroke);
  }
}
