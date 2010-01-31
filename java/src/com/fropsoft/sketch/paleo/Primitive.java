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

/**
 * @author jamoozy
 */
public abstract class Primitive
{
  /** Is this recognition possible? */
  protected boolean possible;

  /** Index of the point that starts this primitive. */
  protected int start;

  /** Index of the point that ends this primitive. */
  protected int end;

  /** Length of this primitive. */
  protected double length;

  /** Gets the possibility. */
  public boolean isPossible() { return possible; }

  /** Get the start index. */
  public int getStart() { return start; }

  /** Get the end index. */
  public int getEnd() { return end; }

  /** Get the length */
  public double getLength() { return length; }
}
