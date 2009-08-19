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
import com.fropsoft.geometry.Shape;

/**
 * @author jamoozy
 */
public class Box extends AbstractItem
{
  /**
   * The points that define the outline of this box.
   */
  private final Point2D[] outline;

  /**
   * Creates a new box made up of the passed shapes. Assumes that the shapes are
   * lines that define the edges of the box. Assumes the lines are given in
   * order around the box (either direction).
   * 
   * @param shapes
   *          The shapes that make up this box.
   * @throws InvalidShapeException
   *           If there are any shapes other than lines.
   */
  public Box(Shape... shapes) throws InvalidShapeException
  {
    super(shapes);

    // Check that everything is a line.
    // TODO encorporate polylines (change javadoc comments when done).
    for (Shape s : shapes)
      if (s.getClass() != Line.class)
        throw new InvalidShapeException("Expected Line, got "
            + s.getClass().getSimpleName());

    outline = new Point2D[shapes.length];

    // Loop through and create the box.
    for (int i = 0; i < shapes.length; i++)
    {
      Line l1 = (Line)shapes[i];
      Line l2 = (Line) shapes[(i + 1) % shapes.length];

      outline[i] = l2.getStartPoint().midpoint(l1.getEndPoint());
      // XXX maybe better to look at intersection of lines?
      // or do both and see which is "better"?
      // ("better" meaning closer to a line, or something).
    }
  }

  /**
   * Returns the outline of this box.
   * 
   * @return The outline of this box.
   */
  public Point2D[] getOutline()
  {
    return outline;
  }

  /* (non-Javadoc)
   * @see com.fropsoft.obrik.Item#toJBox2DObject()
   */
  public Object toJBox2DObject()
  {
    // TODO Auto-generated method stub
    return null;
  }

}
