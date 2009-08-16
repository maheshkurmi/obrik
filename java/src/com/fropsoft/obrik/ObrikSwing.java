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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Sets up a Swing interface and creates and hooks into an Obrik {@link GameState}.
 * @author jamoozy
 */
public class ObrikSwing extends JPanel implements MouseListener,
    MouseMotionListener
{
  /**
   * Run Obrik in a Swing GUI.
   * 
   * @param args
   *          Command line args (none yet supported).
   */
  public static void main(String[] args)
  {
    // Schedule a job for the event-dispatching thread:
    // ... creating and showing Obrik's GUI.
    //
    // Why does Sun want it done this way?
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        createObrikAndShowGUI();
      }
    });
  }

  /**
   * Creates an Obrik game state and a Swing GUI and runs the GUI on top of the
   * state.
   */
  public static void createObrikAndShowGUI()
  {
    JFrame frame = new JFrame("Obrik");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setContentPane(new ObrikSwing());

    //Display the window.
    frame.pack();
    frame.setVisible(true);

    // Don't do any more GUI work here as advised by a Sun thread safety page at:
    // <http://java.sun.com/products/jfc/tsc/articles/threads/threads1.html>
  }
  



  /**
   * The internal state of Obrik.
   */
  private GameState game;

  /**
   * Create a new, empty Obrik game under a Swing GUI.
   */
  private ObrikSwing()
  {
    game = new GameState();
  }

  public void mouseClicked(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void mouseEntered(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void mouseExited(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void mousePressed(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void mouseReleased(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void mouseDragged(MouseEvent e)
  {
    game.mouseDrag(e);
  }

  public void mouseMoved(MouseEvent e)
  {
    // Ignored because we only want drags.
  }


  public Dimension getPreferredSize()
  {
    return new Dimension(400, 400);
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    // Draw Text
    g.drawString("This is my custom Panel!", 10, 20);
  }
}