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

/**
 * Represents a point on screen with an x and a y coordinte. The coordinates are
 * integers, because they represent a screen pixel coordinate.
 * 
 * @author jamoozy
 */
public class Point2D
{
  /**
   * The X coordinate of this point.
   */
  protected int x;

  /**
   * The Y coordinate of this point.
   */
  protected int y;

  /**
   * Creates a new point at the point (x,y).,
   * 
   * @param x The x coordinate of the new point.
   * @param y The y coordinate of the new point.
   */
  public Point2D(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the y coordinate of this point.
   * 
   * @return The y coordinate.
   */
  public int getX()
  {
    return x;
  }

  /**
   * Returns the x coordinate of this point.
   * 
   * @return The x coordinate.
   */
  public int getY()
  {
    return y;
  }

  /**
   * Computes the L<sub>2</sub> norm to that.
   * 
   * @param that
   *          The other point.
   * @return The L<sub>2</sub> norm to that.
   */
  public double distanceTo(Point2D that)
  {
    double xDiff = this.x - that.x;
    double yDiff = this.y - that.y;
    return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
  }

  /**
   * Computes the angle in radians to that, where 0 is when that is directly to
   * the right of this, and pi/2 is where that is directly above this.
   * 
   * @param that
   *          The other point.
   * @return The angle, in radians.
   */
  public Angle angleTo(Point2D that)
  {
    // TODO Check documentation to see what this returns.
    return new Angle(Math.atan2(that.x - this.x, that.y - this.y));
  }

  /**
   * For use as the slope in the "y = mx + b" line eequation.
   * 
   * @param that
   *          The other line.
   * @return The slope between the two lines.
   */
  public double slopeTo(Point2D that)
  {
    return (that.y - this.y) / (that.x - this.x);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#clone()
   */
  @Override
  public Point2D clone()
  {
    return new Point2D(x, y);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return String.format("(%d,%d)", x, y);
  }

  /**
   * Finds the midpoint between to points.
   * 
   * @param that
   *          The other point.
   * 
   * @return The midpoint between two points.
   */
  public Point2D midpoint(Point2D that)
  {
    return new Point2D((this.x + that.x) / 2, (this.y + that.y) / 2);
  }
}
