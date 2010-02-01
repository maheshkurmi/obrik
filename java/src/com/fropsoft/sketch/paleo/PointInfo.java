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

package com.fropsoft.sketch.paleo;

import com.fropsoft.geometry.Point2DTV;

/**
 * @author jamoozy
 */
public class PointInfo extends Point2DTV
{
  /** The direction the pen was going in here. */
  private double direction;

  /** The curvature at this point. */
  private double curvature;

  /** The velocity of the pen at this point (pixels/s). */
  private double velocity;

  /** The XXX ??? */
  private double dirRatio;

  /** The XXX ??? */
  private double arcSum;

  /** The XXX ??? */
  private double dirSum;

  /** The XXX ??? */
  private double cordDist;

  /** Create a new point info based on a Point2DTV. */
  PointInfo(Point2DTV point)
  {
    super(point.getX(), point.getY(), point.getT(), point.getV());
  }

  /** The direction the pen was going in here. */
  public double getDirection() { return direction; }

  /** The curvature at this point. */
  public double getCurvature() { return curvature; }

  /** The velocity of the pen at this point (pixels/s). */
  public double getVelocity() { return velocity; }

  /** The XXX ??? */
  public double getDirRatio() { return dirRatio; }

  /** The XXX ??? */
  public double getArcSum() { return arcSum; }

  /** The XXX ??? */
  public double getDirSum() { return dirSum; }

  /** The XXX ??? */
  public double getCordDist() { return cordDist; }
}
