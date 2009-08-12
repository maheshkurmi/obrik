/*
 * This file is part of NAME.  Copyright (C) 2009 Andrew Correa
 * <jamoozy@gmail.com>, Ali Mohammad <alawibaba@gmail.com>
 *
 * NAME is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/> or write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */

package com.fropsoft.sketch;

import java.awt.Shape;

import com.fopsoft.geometry.Stroke;

/**
 * All classes that represent a single shape that can be recognized must
 * implement this class.
 * 
 * @author jamoozy
 */
public interface ShapeRecognizer
{
  /**
   * Classifies the passed stroke set as this type.
   * 
   * @return The probability the stroke is this shape.
   */
  public double gague(Stroke stroke);

  /**
   * After a call to {@link #gague(Stroke)}, this function will return the Shape
   * representation of that {@link Stroke} object.
   * 
   * @return A {@link Shape} representation of a {@link Stroke} object.
   */
  public Shape getShape();
}