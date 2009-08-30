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

import java.util.Iterator;

/**
 * This can be thought of as a bounding box.
 *
 * @author jamoozy
 */
public class Bounds
{
  /**
   * Creates the bounding box that contains all the points in all the strokes.
   * @param strokes The strokes to create a bounding box for.
   * @return The bounding box.
   */
  public static Bounds createFromStrokes(Stroke... strokes)
  {
    Bounds bounds = new Bounds();
    for (Stroke s : strokes)
      for (Iterator<Point2DTV> iter = s.iterator(); iter.hasNext();)
        bounds.expandToInclude(iter.next());
    return bounds;
  }

  /**
   * Creates a new bounds from a list of shapes.  The created bounds will
   * encompass all the passed shapes.
   * 
   * @param shapes
   *          The shapes to create the bounds from.
   * @return The bounds.
   */
  public static Bounds createFromShapes(Shape... shapes)
  {
    Bounds bounds = new Bounds();
    for (Shape shape : shapes)
      bounds.expandToInclude(shape.getBounds());
    return bounds;
  }

  /**
   * Minimum (x,y).
   */
  private Point2D min;

  /**
   * Maximum (x,y).
   */
  private Point2D max;

  /**
   * Creates a new, invalid Bounds object, where the coordinates of min are
   * {@link Integer#max} and the coordinates or max are {@link Integer#min}
   */
  private Bounds()
  {
    min = new Point2D(Integer.MAX_VALUE, Integer.MAX_VALUE);
    max = new Point2D(Integer.MIN_VALUE, Integer.MIN_VALUE);
  }

  /**
   * Expands the {@link Bounds} object to include this point.
   *
   * @param point
   *          The point to ensure
   */
  private void expandToInclude(Point2D point)
  {
    max = new Point2D(Math.max(max.getX(), point.getX()),
                      Math.max(max.getY(), point.getY()));
    min = new Point2D(Math.min(min.getX(), point.getX()),
                      Math.min(min.getY(), point.getY()));
  }

  /**
   * Expands the {@link Bounds} object to include this bounding box.
   *
   * @param bounds
   *          The bounds to ensure
   */
  private void expandToInclude(Bounds bounds)
  {
    max = new Point2D(Math.max(max.getX(), bounds.max.getX()),
                      Math.max(max.getY(), bounds.max.getY()));
    min = new Point2D(Math.min(min.getX(), bounds.min.getX()),
                      Math.min(min.getY(), bounds.min.getY()));
  }

  /**
   * Creates a new Bounds object.
   *
   * @param minX
   *          Minimum x value.
   * @param minY
   *          Minimum y value.
   * @param maxX
   *          Maximum x value.
   * @param maxY
   *          Maximum y value.
   */
  public Bounds(int minX, int minY, int maxX, int maxY)
  {
    min = new Point2D(minX, minY);
    max = new Point2D(maxX, maxY);
  }

  /**
   * Returns the center point of this {@link Bounds} object.
   *
   * @return The cneter point of this {@link Bounds} object.
   */
  public Point2D getCenter()
  {
    return new Point2D(getCenterX(), getCenterY());
  }

  /**
   * Returns the y coordinate of the center of this {@link Bounds} object.
   *
   * @return The y coordinate of the cneter of this {@link Bounds} object.
   */
  public int getCenterX()
  {
    return (min.getX() + max.getX()) / 2;
  }

  /**
   * Returns the x coordinate of the center of this {@link Bounds} object.
   *
   * @return The x coordinate of the cneter of this {@link Bounds} object.
   */
  public int getCenterY()
  {
    return (min.getY() + max.getY()) / 2;
  }

  /**
   * Returns the minimum x value.
   *
   * @return The minimum x value.
   */
  public int getMinX()
  {
    return min.getX();
  }

  /**
   * Returns the minimum y value.
   *
   * @return The minimum y value.
   */
  public int getMinY()
  {
    return min.getY();
  }

  /**
   * Returns the maximum x value.
   *
   * @return The maximum x value.
   */
  public int getMaxX()
  {
    return max.getX();
  }

  /**
   * Returns the maximum y value.
   *
   * @return The maximum y value.
   */
  public int getMaxY()
  {
    return max.getY();
  }

  /**
   * Returns the area of this bounds object in pixels<sup>2</sup>.
   *
   * @return The area of this bounds object in pixels<sup>2</sup>.
   */
  public int area()
  {
    if (max.getX() < min.getX() || max.getY() < min.getY())
      return 0;

    return (max.getX() - min.getX()) * (max.getY() - min.getY());
  }

  /**
   * Determine the percentage of this area overlapped by that bounds.
   *
   * @param that
   *          The bounds to compare with.
   * @return The percentage of overlap.
   */
  public double overlap(Bounds that)
  {
    Bounds overlap = new Bounds(Math.max(this.min.getX(), that.min.getX()),
                                Math.max(this.min.getY(), that.min.getY()),
                                Math.min(this.max.getX(), that.max.getX()),
                                Math.min(this.max.getY(), that.max.getY()));
    return ((double) overlap.area()) / this.area();
  }
}
