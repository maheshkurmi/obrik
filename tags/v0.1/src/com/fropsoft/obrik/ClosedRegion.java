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

package com.fropsoft.obrik;

import com.fropsoft.geometry.Line;
import com.fropsoft.geometry.Point2D;

/**
 * A closed region is an object that can be anchored or not.
 *
 * @author jamoozy
 */
public class ClosedRegion extends AbstractItem
{
  /**
   * The points that outline this region.  Attained by averaging the closest
   * parts of the line segments.
   */
  private final Point2D[] startPoints;

  /** Current points. */
  private Point2D[] points;

  /** Keeps track of whether this has been anchored. */
  private boolean anchored;

  /**
   * Creates a new closed region that was created by the passed lines.
   *
   * @param lines
   *          The lines that created this region.
   */
  public ClosedRegion(Line... lines)
  {
    super(lines);

    // Create the points that ouline this line.
    points = new Point2D[lines.length];
    for (int i = 0; i < lines.length; i++)
    {
      int j = (i + 1) % lines.length;
      double d[] =
      {
        lines[i].getStartPoint().distanceTo(lines[j].getStartPoint()),
        lines[i].getStartPoint().distanceTo(lines[j].getEndPoint()),
        lines[i].getEndPoint().distanceTo(lines[j].getStartPoint()),
        lines[i].getEndPoint().distanceTo(lines[j].getEndPoint())
      };

      double min = Double.MAX_VALUE;
      int min_i = 0;
      for (int k = 0; k < d.length; k++)
        if (d[k] < min)
        {
          min = d[k];
          min_i = k;
        }

      switch (min_i)
      {
        case 0:
          points[i] = lines[i].getStartPoint().midpoint(lines[j].getStartPoint());
          break;
        case 1:
          points[i] = lines[i].getStartPoint().midpoint(lines[j].getEndPoint());
          break;
        case 2:
          points[i] = lines[i].getEndPoint().midpoint(lines[j].getStartPoint());
          break;
        case 3:
          points[i] = lines[i].getEndPoint().midpoint(lines[j].getEndPoint());
          break;
      }
    }

    startPoints = new Point2D[lines.length];
    for (int i = 0; i < points.length; i++)
      startPoints[i] = points[i].clone();
  }

  /**
   * Determines if this is anchored (immobile).  You anchor closed regions by
   * drawing an X on them.
   * @return <code>truce</code> if it's anchored, <code>false</code> otherwise.
   */
  public boolean isAnchored()
  {
    return anchored;
  }

  /**
   * Changes the anchored state of this closed region.
   * @param anchored Whether it's anchored or not.
   */
  public void setAnchored(boolean anchored)
  {
    this.anchored = anchored;
  }

  /**
   * Sets the points of this closed region to the passed points.
   * @param points
   *          The new points.
   */
  public void setPoints(Point2D[] points)
  {
    this.points = points;
  }

  /**
   * Returns the points that define this region.
   *
   * @return The points that define this region.
   */
  public Point2D[] getPoints()
  {
    return points;
  }

  /* (non-Javadoc)
   * @see com.fropsoft.obrik.Item#resetPosition()
   */
  public void resetPosition()
  {
    super.resetPosition();
    for (int i = 0; i < points.length; i++)
      points[i].setPoint(startPoints[i].getX(), startPoints[i].getY());
  }
}