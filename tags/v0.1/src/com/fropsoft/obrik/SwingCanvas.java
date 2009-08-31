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
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.fropsoft.geometry.Dot;
import com.fropsoft.geometry.Line;
import com.fropsoft.geometry.Point2D;
import com.fropsoft.geometry.Point2DT;
import com.fropsoft.geometry.Shape;

/**
 * A GUI for a web browser (applet).
 * @author jamoozy
 */
public class SwingCanvas extends JPanel
        implements MouseListener, MouseMotionListener
{
  /** The state that will be populated. */
  private State state;

  /**
   * 
   * @param state
   */
  public SwingCanvas(State state)
  {
    this.state = state;

    addMouseListener(this);
    addMouseMotionListener(this);

    setBackground(Color.white);
    setOpaque(true);
    setBorder(BorderFactory.createLineBorder(Color.white));
  }

  /**
   * 
   */
  public void destroy()
  {
    state = null;
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  public void mouseClicked(MouseEvent e)
  {
    state.mouseClicked(e.getX(), e.getY());
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
   */
  public void mouseEntered(MouseEvent e)
  {
    state.mouseEntered(e.getX(), e.getY(),
            e.getButton() != MouseEvent.NOBUTTON);
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
   */
  public void mouseExited(MouseEvent e)
  {
    repaint(e);
    state.mouseExited(e.getX(), e.getY(), e.getButton() != MouseEvent.NOBUTTON);
    invokeShapeRecognitionLater();
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
   */
  public void mousePressed(MouseEvent e)
  {
    repaint();
    state.mousePressed(e.getX(), e.getY());
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
   */
  public void mouseReleased(MouseEvent e)
  {
    repaint(e);
    state.mouseReleased(e.getX(), e.getY());
    invokeShapeRecognitionLater();
  }

  /**
   * Queue up a shape recognition.
   */
  private void invokeShapeRecognitionLater()
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        if (state.recognizeStroke())
          repaint();
      }
    });
  }

  /* (non-Javadoc)
   * @see
   * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
   */
  public void mouseDragged(MouseEvent e)
  {
    repaint(e);
    state.mouseDrag(e.getX(), e.getY());
  }

  /* (non-Javadoc)
   * @see
   * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
   */
  public void mouseMoved(MouseEvent e)
  {
    state.mouseMoved(e.getX(), e.getY());
  }


  /* (non-Javadoc)
   * @see javax.swing.JComponent#getPreferredSize()
   */
  @Override
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
    Point2DT p = state.getLastPoint();
    if (p != null)
    {
      repaint(Math.min(e.getX(), p.getX()) - 5,
              Math.min(e.getY(), p.getY()) - 5,
              Math.abs(e.getX() - p.getX()) + 10,
              Math.abs(e.getY() - p.getY()) + 10);
    }
  }

  /* (non-Javadoc)
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    // Draw the user's stroke:
    int[] xs = state.buildStrokeXCoords();
    int[] ys = state.buildStrokeYCoords();
    g.drawPolyline(xs, ys, state.getNumPoints());

    // Draw all the shapes in the state.
    for (Iterator<Shape> iter = state.shapeIterator(); iter.hasNext();)
      drawShape(g, iter.next());

    // Draw all the items in the state.
    for (Iterator<Item> iter = state.itemIterator(); iter.hasNext();)
      drawItem(g, iter.next());
  }

  /**
   * Draws an item.
   *
   * @param g
   *          The graphics to draw on.
   * @param i
   *          The item to draw.
   */
  public void drawItem(Graphics g, Item i)
  {
    if (i instanceof ClosedRegion)
    {
      ClosedRegion cr = (ClosedRegion)i;
      Point2D[] points = cr.getPoints();
      int[] x = new int[points.length];
      int[] y = new int[points.length];
      for (int j = 0; j < points.length; j++)
      {
        x[j] = points[j].getX();
        y[j] = points[j].getY();
      }
      g.setColor(cr.isAnchored() ? Color.green : Color.black);
      g.drawPolygon(x, y, points.length);
    }
    else
    {
      System.err.printf("How do I draw a %s?\n", i.getClass().getSimpleName());
    }
  }

  /**
   * Draws a shape.
   *
   * @param g
   *          The graphics to draw on.
   * @param s
   *          The shape to draw.
   */
  public void drawShape(Graphics g, Shape s)
  {
    if (s.getClass() == Line.class)
    {
      Line line = (Line) s;
      g.setColor(Color.blue);
      g.drawLine(line.getStartPoint().getX(), line.getStartPoint().getY(), line
          .getEndPoint().getX(), line.getEndPoint().getY());
    }
    else if (s.getClass() != Dot.class)
    {
      System.err.println("Hey!  There's a " + s.getClass() + " shape!");
    }
  }
}
