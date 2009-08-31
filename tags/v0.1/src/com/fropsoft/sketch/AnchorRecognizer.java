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
import com.fropsoft.geometry.Point2D;
import com.fropsoft.geometry.Shape;
import com.fropsoft.obrik.Anchor;

/**
 * This recognizes anchors (that look like X's).
 *
 * @author jamoozy
 */
public class AnchorRecognizer extends AbstractItemRecognizer
{
  /**
   * Gauges how likely it is that l1 and l2 form an anchor (X).  This is a
   * helper function for the {@link ItemRecognizer#gauge(Shape...)} method.
   * The return value is in [0,1].
   *
   * @param l1
   *          The first line.
   * @param l2
   *          The second line.
   * @return The probability that these two lines comprise an X
   */
  private static double gauge(Line l1, Line l2)
  {
    Point2D intersection;
    if ((intersection = Line.segmentIntersection(l1, l2)) == null)
      return 0;

    // A measure of how close these are to 90 degrees.
    double angle = 2 * l1.acuteAngleBetween(l2).getValue() / Math.PI;

    // A measure of how close these are to the same length.
    double similarity = Math.abs(l1.length() - l2.length()) /
                        Math.max(l1.length(), l2.length());

    // A measure of how far in the middle the cross is in each line.
    double l1spacing = 1 - Math.abs(l1.getStartPoint().distanceTo(intersection)
        - l1.getEndPoint().distanceTo(intersection)) / l1.length();
    double l2spacing = 1 - Math.abs(l2.getStartPoint().distanceTo(intersection)
        - l2.getEndPoint().distanceTo(intersection)) / l2.length();

    // o1 and o2 are in [0,1], angle is in [0,pi/2], so normalize by pi/2
//    return (2 * o1 * o2 * angle) / Math.PI;
    return angle * similarity * l1spacing * l2spacing;
  }

  /* (non-Javadoc)
   * @see
   * com.fropsoft.sketch.ItemRecognizer#gauge(com.fropsoft.geometry.Shape[])
   */
  public double gauge(Shape... shapes)
  {
    Line[] lines = Line.findLines(shapes);

    double highGauge = 0;
    int iMax = -1, jMax = -1;   // Indexes of the best pair.

    // Find a crossing of lines.
    for (int i = 0; i < lines.length; i++)
    {
      for (int j = i + 1; j < lines.length; j++)
      {
        Line a = lines[i];
        Line b = lines[j];

        double rate = AnchorRecognizer.gauge(a, b);
        if (rate > highGauge)
        {
          highGauge = rate;
          iMax = i;
          jMax = j;
        }
      }
    }

    if (iMax >= 0 || jMax >= 0)
      this.setItem(new Anchor(lines[iMax], lines[jMax]));

    return highGauge;
  }
}
