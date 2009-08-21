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
import java.util.Vector;

/**
 * This represents a pen stroke, that is it represents the timestamped (x,y)
 * coordinate data of a digital pen from the time it gets placed on the canvas
 * to the time it gets released. This uses {@link Point2DTV} objects to store
 * the pen stroke's data.
 * 
 * @author jamoozy
 */
public class Stroke
{
  /**
   * The points that make up this stroke.
   */
  private final Vector<Point2DTV> points;

  /**
   * Creats an empty stroke.
   */
  public Stroke()
  {
    points = new Vector<Point2DTV>();
  }

  /**
   * Creates a new stroke consisting of the passed strokes.
   * 
   * @param points The poitns to add to this stroke.
   */
  public Stroke(Point2DT... points)
  {
    this.points = new Vector<Point2DTV>();
    this.points.add(new Point2DTV(points[0]));
    for (int i = 1; i < points.length - 1; i++)
    {
      double s = (points[i + 1].getT() - points[i - 1].getT()) / 2e6;
      double l = points[i + 1].distanceTo(points[i - 1]);
      this.points.add(new Point2DTV(points[i], l / s));
    }
    this.points.add(new Point2DTV(points[points.length - 1]));
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#clone()
   */
  @Override
  public Stroke clone()
  {
    Stroke that = new Stroke();
    for (int i = 0; i < points.size(); i++)
      that.points.add(this.points.get(i).clone());
    return that;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Vector#iterator()
   */
  public Iterator<Point2DTV> iterator()
  {
    return points.iterator();
  }

  /**
   * Returns the number of points in this stroke.
   * 
   * @return The number of points in this stroke.
   */
  public int numPoints()
  {
    return points.size();
  }

  /**
   * Returns the i'th point from the stroke.
   * 
   * @param i
   *          The index of the point to get.
   * @return The i'th piont from the stroke.
   */
  public Point2D get(int i)
  {
    return points.get(i);
  }
}
