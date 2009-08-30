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
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Sets up a Swing interface and creates and hooks into an Obrik {@link State}.
 *
 * @author jamoozy
 */
public class SwingGUI extends JPanel
{
  /** To shut eclipse up. */
  private static final long serialVersionUID = 3L;

  /**
   * Run Obrik in a Swing GUI.
   *
   * @param args Command line args (none yet supported).
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

    // Display the window.
    frame.pack();
    frame.setVisible(true);

    // Don't do any more GUI work here as advised by a Sun thread safety page
    // at:
    // <http://java.sun.com/products/jfc/tsc/articles/threads/threads1.html>
  }

  /**
   * The internal state of Obrik.
   */
  private final State state;

  /**
   * Create a new, empty Obrik game under a Swing GUI.
   */
  private SwingGUI()
  {
    state = new State();

    JButton run = new JButton("Run");
    run.setAction(new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        SwingGUI.this.startSim();
      }
    });
    run.setText("Run");

    JButton reset = new JButton("Reset");
    reset.setAction(new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        SwingGUI.this.stopSim();
      }
    });
    reset.setText("Reset");

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(new SwingCanvas(state));
//    add(Box.createRigidArea(new Dimension(150,10)));
    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
    run.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    reset.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    p.add(run);
    p.add(Box.createRigidArea(new Dimension(150,10)));
    p.add(reset);
    add(p);
  }

  /**
   * Start the simulation.
   */
  public void startSim()
  {
    // Initialize the simulation.
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        state.initSim();
      }
    });

    // Perform a step in the simulation.
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        doSimStep();
      }
    });
  }

  public void doSimStep()
  {
    if (state.simShouldRun())
    {
      // actually do something
      if (state.doSimStep())
      {
        repaint();
      }

      // Queue up another sim step.
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          doSimStep();
        }
      });
    }
  }

  /**
   * Start the simulation.
   */
  public void stopSim()
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        state.destroySim();
      }
    });
  }
}
