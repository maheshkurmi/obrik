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
 * A class that implements the strokes and bounds part of the shape interface.
 *
 * @author jamoozy
 */
public class AbstractShape implements Shape
{
  /**
   * Find all the shapes of a specific type in an array of shapes.  So if you
   * want all the lines in a list of shapes, you call:
   * <code>AbstractShape.findTypes((Line)null, shapes)</code> 
   *
   * @param shapes
   *          The shapes to search for something in.
   * @return An array of the found shapes.
   */
  public static <T extends Shape> T[] findTypes(T shape, Shape... shapes)
  {
    int num = 0;
    Shape[] ts = new Shape[shapes.length];
    for (Shape s : shapes)
      if (s instanceof Line)
        ts[num++] = s;
    Shape[] out = new Shape[num];
    System.arraycopy(ts, 0, out, 0, num);
    return (T[])ts;
  }

  /**
   * The strokes that make up this shape.
   */
  protected Vector<Stroke> strokes;

  /**
   * The bounding box of this {@link Shape}.
   */
  protected Bounds bbox;

  /**
   * Creates a new AbstractShape
   */
  protected AbstractShape(Stroke... strokes)
  {
    this.strokes = new Vector<Stroke>();
    for (Stroke s : strokes)
      this.strokes.add(s);
    bbox = Bounds.createFromStrokes(strokes);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.fropsoft.geometry.Shape#getStrokes()
   */
  public Stroke[] getStrokes()
  {
    Stroke[] out = new Stroke[strokes.size()];
    for (int i = 0; i < out.length; i++)
      out[i] = strokes.get(i).clone();
    return out;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.fropsoft.geometry.Shape#getBounds()
   */
  public Bounds getBounds()
  {
    return bbox;
  }
}
