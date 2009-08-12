/* 
 * This program is free software; you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, see <http://www.gnu.org/licenses/> or write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */

package com.fopsoft.geometry;

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

  public double distanceTo(Point2D that)
  {
    double xDiff = this.x - that.x;
    double yDiff = this.y - that.y;
    return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
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
}
