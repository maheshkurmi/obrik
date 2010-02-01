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

import com.fropsoft.geometry.Stroke;

/**
 * @author jamoozy
 */
public class StrokeInfo extends Stroke
{
  /** The number of primitives to check. */;
  public static int NUM_PRIMS = 9; // including complex

  /** The primitives */
  private Primitive[] primitives = new Primitive[NUM_PRIMS];

  /** Candidate corners. */
  private Vector<PointInfo> candCorners = new Vector<PointInfo>();

  /** Merged corners. */
  private Vector<PointInfo> mergCorners = new Vector<PointInfo>();

  /** True corners. */
  private Vector<PointInfo> trueCorners = new Vector<PointInfo>();

  /** First valid index after trimming tails. */
  private int firstValid;

  /** Last valid index after trimming tails. */
  private int lastValid;

  /** Normalized Distance between Direction Extremes. */
  private double ndde;

  /** Direction Change Ratio. */
  private double dcr;

  /** Number of rotations the stroke made. */
  private double rotations;

  /** Whether the stroke was over traced. */
  private boolean overtraced;

  /** Whether the stroke was fully closed (think circle). */
  private boolean closed;

  /** The list of recognized primitives in order of likelihood. */
  private Vector<Primitive> hierarchy = new Vector<Primitive>();

  /** Make a new stroke info based on a stroke. */
  StrokeInfo(Stroke stroke)
  {
    points = new Vector<PointInfo>(stroke.points.size());
    for (Point p : stroke.points)
      points.add(new PointInfo(p));
  }

  /** Get the first valid index after trimming tails. */
  public int getFirstValid() { return firstValid; }

  /** Get the last valid index after trimming tails. */
  public int getLastValid() { return lastValid; }

  /** Get the Normalized Distance between Direction Extremes. */
  public double getNDDE() { return ndde; }

  /** Get the Direction Change Ratio. */
  public double getDCR() { return dcr; }

  /** Get the number of rotations the stroke made. */
  public double getRotations() { return rotations; }

  /** Get whether the stroke was over traced. */
  public boolean getOvertraced() { return overtraced; }

  /** Get whether the stroke was fully closed (think circle). */
  public boolean getClosed() { return closed; }

  /** Gets the i<sup>th</sup> {@link PointInfo} in the stroke. */
  public PointInfo get(int i)
  {
    if (0 <= i && i < points.size())
      return hierarchy[i];
    System.err.println("Could not get point " + i + ": O.O.B.");
    return null;
  }

  /** Gets the (i+1)<sup>th</sup> most likely recognition. */
  public Primitive getRec(int i)
  {
    if (0 <= i && i < hierarchy.length)
      return hierarchy[i];
    System.err.println("Could not get point " + i + ": O.O.B.");
    return null;
  }
}
