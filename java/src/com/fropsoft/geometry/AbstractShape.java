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
 * A shape that implements the strokes part of the shape.
 * 
 * @author jamoozy
 */
public class AbstractShape implements Shape
{
  private Vector<Stroke> strokes;

  public AbstractShape()
  {
    strokes = new Vector<Stroke>();
  }

  public Stroke[] getStrokes()
  {
    Stroke[] out = new Stroke[strokes.size()];
    for (int i = 0; i < out.length; i++)
      out[i] = strokes.get(i).clone();
    return out;
  }
}
