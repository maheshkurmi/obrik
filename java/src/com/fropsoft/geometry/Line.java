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

package com.fropsoft.geometry;

import java.util.Vector;

/**
 * Y'know ... a line.
 * 
 * @author jamoozy
 */
public class Line extends AbstractShape
{
  /**
   * Find all the lines in an array of shapes.
   * 
   * @param shapes
   *          The shapes to search for lines in.
   * @return An array of the found lines.
   */
  public static Line[] findLines(Shape... shapes)
  {
    Vector<Line> vs = new Vector<Line>();
    for (Shape s : shapes)
      if (s.getClass() == Line.class)
        vs.add((Line) s);
    return vs.toArray(new Line[] {});
  }

  /**
   * Creates a new Line.
   * 
   * @param strokes
   */
  public Line(Stroke... strokes)
  {
    super(strokes);
  }

  /**
   * Returns the starting point of this line.
   * 
   * @return The starting piont of this line.
   */
  public Point2D getStartPoint()
  {
    return strokes.get(0).get(0);
  }

  /**
   * Returns the ending point of this line.
   * 
   * @return The ending piont of this line.
   */
  public Point2D getEndPoint()
  {
    Stroke s = strokes.get(strokes.size() - 1);
    return s.get(s.numPoints() - 1);
  }

  /**
   * Determines if that line and this line intersect, returning the intersection
   * or <code>null</code>.
   * 
   * @param that
   *          The other line.
   * @return The intersection (if it exists) or <code>null</code>.
   */
  public Point2D intersection(Line that)
  {
    double thisSlope = this.getStartPoint().slopeTo(this.getEndPoint());
    double thatSlope = that.getStartPoint().slopeTo(that.getEndPoint());

    // Only parallel lines don't intersect, so check that.
    if (thisSlope == thatSlope)
      return null;

    // Find the "b" in "y = mx + b"
    double thisOffset = this.getEndPoint().getY() - thisSlope
                      * this.getEndPoint().getX();
    double thatOffset = that.getEndPoint().getY() - thatSlope
                      * that.getEndPoint().getX();

    // XXX I should probably look at the efficiency of this method.
    return new Point2D((int) Math.round((thisOffset - thatOffset)
                                      / (thatSlope - thisSlope)),
                       (int) Math.round((thatSlope * thisOffset
                                  - thisSlope * thatOffset)
                                      / (thatSlope - thisSlope)));
  }

  /**
   * Determines if this line is top-left to bottom-right. Direction does not
   * mater, so this is equivalent to bottom-right to top-left.
   * 
   * @return
   */
  public boolean isTLBR()
  {
    return (getStartPoint().getX() < getEndPoint().getX() &&
            getStartPoint().getY() < getEndPoint().getY()) ||
           (getStartPoint().getX() > getEndPoint().getX() &&
            getStartPoint().getY() > getEndPoint().getY());
  }

  /**
   * Determines if this line is top-right to bottom-left. Direction does not
   * mater, so this is equivalent to bottom-left to top-right.
   * 
   * @return
   */
  public boolean isTRBL()
  {
    return (getStartPoint().getX() < getEndPoint().getX() &&
            getStartPoint().getY() > getEndPoint().getY()) ||
           (getStartPoint().getX() > getEndPoint().getX() &&
            getStartPoint().getY() < getEndPoint().getY());
  }
}
