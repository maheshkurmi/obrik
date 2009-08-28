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

/**
 * Meant to create and dispatch a JBox2D simulation. This acts as the bridge
 * betweden Obrik's internal representation of items and the way it needs to be
 * to work in JBox2D.
 * 
 * @author jamoozy
 */
public class JBox2DDispatcher
{
  /**
   * Creates a new dispatcher.
   */
  public JBox2DDispatcher()
  {
    // TODO
  }

  /**
   * Loads an item into the dispatcher.
   * 
   * @param item The item to load.
   */
  public void add(Item item)
  {
    // TODO
  }

  /**
   * Loads a series of itemes into the dispatcher.
   * 
   * @param items The series of items to load.
   */
  public void add(Item... items)
  {
    // TODO
  }

  /**
   * Runs the JBox2D simulation.
   */
  public void run()
  {
    // TODO
  }
}
