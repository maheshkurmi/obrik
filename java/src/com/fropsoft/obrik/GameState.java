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

import java.awt.event.MouseEvent;
import java.util.Vector;

import com.fropsoft.geometry.Point2DT;

/**
 * Keeps track of the state of the Obrik "game".
 * 
 * @author jamoozy
 */
public class GameState
{
  // TODO fields with objects that will be turned into JBox things and GUI
  // things.

  public Vector<Point2DT> points;

  /**
   * Create a new Obrik game state.
   */
  public GameState()
  {
    points = new Vector<Point2DT>();
  }

  public void mouseDrag(MouseEvent e)
  {
    points.add(new Point2DT(e.getX(), e.getY(), System.currentTimeMillis()));
  }

  public void mouseClicked(MouseEvent e)
  {
    // Up & down, so do no action. (for now)
  }

  public void mouseEntered(MouseEvent e)
  {
    // Not interesting to us.
  }

  public void mousePressed(MouseEvent e)
  {
    points.clear();
    points.add(new Point2DT(e.getX(), e.getY(), System.currentTimeMillis()));
  }

  public void mouseExited(MouseEvent e)
  {
    if (e.getButton() != MouseEvent.NOBUTTON)
      points.add(new Point2DT(e.getX(), e.getY(), System.currentTimeMillis()));
  }

  public void mouseReleased(MouseEvent e)
  {
    points.add(new Point2DT(e.getX(), e.getY(), System.currentTimeMillis()));
  }

  public void mouseMoved(MouseEvent e)
  {
    // No movement action (when mouse unpressed).
  }

  public int[] getStrokeXCoords()
  {
    int[] xs = new int[points.size()];
    for (int i = 0; i < xs.length; i++)
      xs[i] = points.get(i).getX();
    return xs;
  }

  public int[] getStrokeYCoords()
  {
    int[] ys = new int[points.size()];
    for (int i = 0; i < ys.length; i++)
      ys[i] = points.get(i).getY();
    return ys;
  }

  public int getNumPoints()
  {
    return points.size();
  }
}
