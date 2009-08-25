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
    // TODO generic-ize this function and put it ... somewhere.
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
   * Returns the X coordinate of the starting location.
   *
   * @return the X coordinate of the starting location.
   */
  public int getStartX()
  {
    return getStartPoint().getX();
  }

  /**
   * Returns the Y coordinate of the starting location.
   *
   * @return the Y coordinate of the starting location.
   */
  public int getStartY()
  {
    return getStartPoint().getY();
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
   * Returns the X coordinate of the ending location.
   *
   * @return the X coordinate of the ending location.
   */
  public int getEndX()
  {
    return getEndPoint().getX();
  }

  /**
   * Returns the Y coordinate of the ending location.
   *
   * @return the Y coordinate of the ending location.
   */
  public int getEndY()
  {
    return getEndPoint().getY();
  }

  /**
   * Computes if lines a and b intersect, returning the intersection or
   * <code>null</code>.  This only reterns <code>null</code> when a and b are
   * perfectly parallel.  If you want to get the intersections of the actual
   * line segments, use {@link #segmentIntersection(Line)}.  This method will
   * extend both lines into infinity in either direction to get the
   * intersection.
   * 
   * @param that
   *          The other line.
   * @return The intersection (if it exists) or <code>null</code>.
   */
  public static Point2D intersection(Line a, Line b)
  {
    double aSlope = a.getStartPoint().slopeTo(a.getEndPoint());
    double bSlope = b.getStartPoint().slopeTo(b.getEndPoint());

    // Only parallel lines don't intersect, so check b.
    if (aSlope == bSlope)
      return null;

    // Find the "b" in "y = mx + b"
    double aOffset = a.getEndY() - aSlope * a.getEndX();
    double bOffset = b.getEndY() - bSlope * b.getEndX();

    // XXX I should probably look at the efficiency of a method.
    return new Point2D(
        (int) Math.round((aOffset - bOffset) / (bSlope - aSlope)),
        (int) Math.round((bSlope * aOffset - aSlope * bOffset)
            / (bSlope - aSlope)));
  }

  /**
   * Computes the intersection of the lines, treating them as line segments.
   * This differs from {@link #intersection(Line)} in that it treats the lines
   * as segments instead of infinitely long lines.  Thus, this method will
   * return <code>null</code> more often than {@link #intersection(Line)}
   *
   * @param a
   *          The line this may intersect.
   * @param b
   *          The line this may intersect.
   * @return The intersection point or <code>null</code>.
   */
  public static Point2D segmentIntersection(Line a, Line b)
  {
    Point2D inter = intersection(a, b);

    if (inter == null || !a.isOn(inter) || !b.isOn(inter))
      return null;

    return inter;
  }

  public boolean isOn(Point2D point)
  {
    double a = getEndX() - getStartX();
    double b = getEndY() - getStartY();

    // Avoid any divide-by-zero errors.
    if (a == 0) return Math.abs(point.getY() - getStartY()) <= 5;
    if (b == 0) return Math.abs(point.getX() - getStartX()) <= 5;

    // How far in each direction to get to the X?
    double u = (point.getX() - getStartX()) / a;
    double v = (point.getY() - getStartY()) / b;

    return (Math.abs(u - v) < 0.05 && 0 <= u && u <= 1 && 0 <= v && v <= 1);
  }

  /**
   * Compute the absolute angle of this line.  The direction the line was
   * drawn matters.  This returns 0 when the line was drawn from the left
   * directly to the right, <code>{@link Math#PI}/2</code> is straight up.
   *
   * @return The absolute angle of this line.
   */
  public Angle getAbsoluteAngle()
  {
    return getStartPoint().angleTo(getEndPoint());
  }

  /**
   * Computes the length of this line.
   *
   * @return The length of this line.
   */
  public double length()
  {
    return getStartPoint().distanceTo(getEndPoint());
  }

  /**
   * Computes the most acute angle between this line and that line.
   *
   * @param that
   *          The other line.
   * @return The most acute angle between the two lines.
   */
  public Angle acuteAngleBetween(Line that)
  {
    double angleBetween =
      this.getAbsoluteAngle().angleBetween(that.getAbsoluteAngle()).getValue();
    if (angleBetween > Math.PI / 2)
      angleBetween = Math.PI - angleBetween;
    return new Angle(angleBetween);
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
