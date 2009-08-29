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
 * Encompases some useful angle manipulation functions (in 2D). All operations
 * are done in radians. (One might call this a glorified double.)
 *
 * @author jamoozy
 */
public class Angle
{
  /**
   * The angle, in radians, of this angle.
   */
  private final double angle;

  /**
   * Creates a new angle with the given angle.
   *
   * @param angle
   *          The angle of the new angle ^_^
   */
  public Angle(double angle)
  {
    this.angle = angle;
  }

  /**
   * Finds the angle between two angles.  Will be in [0,179].
   *
   * @param that
   *          The other angle.
   * @return The angle between the angles.
   */
  public Angle angleBetween(Angle that)
  {
    double diff = Math.abs(this.angle - that.angle);
    if (diff > Math.PI)
      diff = 2 * Math.PI - diff;
    return new Angle(diff);
  }

  /**
   * Returns the double value of this angle.
   *
   * @return The double value of this angle.
   */
  public double getValue()
  {
    return angle;
  }
}
