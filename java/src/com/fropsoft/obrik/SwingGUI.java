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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.fropsoft.geometry.Point2DT;

/**
 * Sets up a Swing interface and creates and hooks into an Obrik {@link GameState}.
 * @author jamoozy
 */
public class SwingGUI extends JPanel implements MouseListener,
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

    frame.setContentPane(new SwingGUI());

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
  private SwingGUI()
  {
    game = new GameState();

//    JLabel area = null;
//    add(area = new JLabel()
//    {
//      Dimension minSize = new Dimension(100, 50);
//  
//      {
//        setBackground(Color.white);
//        setOpaque(true);
//        setBorder(BorderFactory.createLineBorder(Color.black));
//      }
//  
//      public Dimension getMinimumSize()
//      {
//        return minSize;
//      }
//  
//      public Dimension getPreferredSize()
//      {
//        return minSize;
//      }
//    });

//    area.addMouseListener(this);
    addMouseListener(this);
//    area.addMouseMotionListener(this);
    addMouseMotionListener(this);

    setBackground(Color.white);
    setOpaque(true);
    setBorder(BorderFactory.createLineBorder(Color.white));
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  public void mouseClicked(MouseEvent e)
  {
    game.mouseClicked(e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
   */
  public void mouseEntered(MouseEvent e)
  {
    game.mouseEntered(e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
   */
  public void mouseExited(MouseEvent e)
  {
    game.mouseExited(e);
    repaint(e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
   */
  public void mousePressed(MouseEvent e)
  {
    repaint();
    game.mousePressed(e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
   */
  public void mouseReleased(MouseEvent e)
  {
    repaint(e);
    game.mouseReleased(e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
   */
  public void mouseDragged(MouseEvent e)
  {
    repaint(e);
    game.mouseDrag(e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
   */
  public void mouseMoved(MouseEvent e)
  {
    game.mouseMoved(e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.JComponent#getPreferredSize()
   */
  public Dimension getPreferredSize()
  {
    return new Dimension(400, 400);
  }

  /**
   * Request a repaint between the passed mouse event an the last point.
   * 
   * @param e The event that caused the repaint request.
   * 
   * @see #repaint()
   */
  public void repaint(MouseEvent e)
  {
    Point2DT p = game.lastPoint();
    if (p != null)
    {
      repaint(Math.min(e.getX(), p.getX()) - 5,
          Math.min(e.getY(), p.getY()) - 5,
          Math.abs(e.getX() - p.getX()) + 10,
          Math.abs(e.getY() - p.getY()) + 10);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    // Draw the user's stroke:
    int[] xs = game.getStrokeXCoords();
    int[] ys = game.getStrokeYCoords();
    g.drawPolyline(xs, ys, game.getNumPoints());
    
    // TODO Draw all the things in the #game object.
  }
}