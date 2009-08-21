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

package com.fropsoft.sketch;

import com.fropsoft.geometry.Shape;
import com.fropsoft.geometry.Stroke;

/**
 * Interface for a class that recognizes a shape from a user stroke. All classes
 * that implement this must have a corresponding class that implements the
 * {@link Shape} interface. That corresponding class is expected to be returned
 * by the {@link #makeShape(Stroke)} method.
 * 
 * @author jamoozy
 * 
 * @see {@link Shape}, {@link ShapeRecognizerMain}
 */
public interface ShapeRecognizer
{
  /**
   * Classifies the passed stroke set as this shape.
   * 
   * @param stroke
   *          The stroke to classify.
   * 
   * @return The probability the stroke is this shape.
   */
  public double gague(Stroke stroke);

  /**
   * After a call to {@link #gague(Stroke)}, this function will return the Shape
   * representation of that stroke.
   * 
   * @param stroke
   *          The stroke that was recognized as this shape.
   * 
   * @return A shape representation of the stroke object.
   */
  public Shape makeShape(Stroke stroke);
}
