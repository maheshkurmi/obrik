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

/**
 * Thrown when a call to
 * {@link ItemRecognizer#getItem(com.fropsoft.geometry.Shape...)} cannot find an
 * item in the shape list.
 * 
 * @author jamoozy
 */
public class NoItemFoundException extends RuntimeException
{
  /** To shut eclipse up. */
  private static final long serialVersionUID = 2L;

  /**
   * Creates a new no item found exception.
   * @param msg A brief description.
   */
  public NoItemFoundException(String msg)
  {
    super(msg);
  }
}
