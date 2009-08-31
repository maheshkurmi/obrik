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
import com.fropsoft.obrik.Item;

/**
 * This is the interface for a class that recognizes items given a list of
 * shapes.
 *
 * @author jamoozy
 */
public interface ItemRecognizer
{
  /**
   * Gauges the probability that the list of passed shapes contains the item
   * this recognizer corresponds to. This function is expected to exhaustively
   * search the list for combinations of shapes that make up this
   *
   * @return The probability that some combination of shapes is this item.
   */
  public double gauge(Shape... shapes);

  /**
   * After a call to {@link #gague(Shape...))}, this function will return an
   * item representation of what was found to be in the list. To ensure proper
   * execution, call getItem immediately after the call to
   * {@link ItemRecognizerMain#classify(Shape...)} as {@link #gauge(Shape...)}
   * will store the result locally.
   *
   * @param shapes
   *          A list of recognized shapes.
   *
   * @return An item found in the list of shapes.
   * @throws NoItemFoundException
   *           If the item could not be found in the strokes.
   */
  public Item getItem(Shape... shapes) throws NoItemFoundException;
}
