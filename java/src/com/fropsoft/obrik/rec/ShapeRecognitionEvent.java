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

package com.fropsoft.obrik.rec;

import com.fropsoft.geometry.Shape;

import java.io.PrintWriter;

/**
 * @author jamoozy
 */
public abstract class ShapeRecognitionEvent extends AbstractUserEvent
{
  /** The shape that was recognized. */
  protected Shape shape;

  /**
   * Creates a new shape recognition object.
   */
  public ShapeRecognitionEvent(Shape s, long t)
  {
    super(t);
    shape = s;
  }

  /**
   * Writes the shape to file in the standard way, given a name and data.  The
   * data is the body of the <code>&lt;shape&gt;</code> tag.
   * @param out
   *          The place to write to.
   * @param data
   *          The item-specific body of the shape tag.
   */
  public void record(PrintWriter out, String data)
  {
    out.printf("<shape type=\"%s\">\n%s\n</shape>\n",
                shape.getClass().getName(), data);
  }
}
