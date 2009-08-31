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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 * @author jamoozy
 *
 */
public class WebGUI extends JApplet
{
  /** To shut up Eclipse. */
  private static final long serialVersionUID = 6L;

  /** The state of Obrik. */
  private State state;
  
  /** The part that is drawn on. */
  private SwingCanvas canvas;

  /** Keeps track of whether this applet has focus. */
  private boolean hasFocus = true;

  /* (non-Javadoc)
   * @see java.applet.Applet#init()
   */
  @Override
  public void init()
  {
    super.init();
    resize(400, 430);

    state = new State();
    canvas = new SwingCanvas(state);

    JButton run = new JButton("Run");
    run.setAction(new AbstractAction()
    {
      public void actionPerformed(ActionEvent e)
      {
        WebGUI.this.startSim();
      }
    });
    run.setText("Run");
    
    JButton clear = new JButton("Clear");
    clear.setAction(new AbstractAction()
    {
      public void actionPerformed(ActionEvent e)
      {
        WebGUI.this.clear();
      }
    });
    clear.setText("Clear");

    JButton reset = new JButton("Reset");
    reset.setAction(new AbstractAction()
    {
      public void actionPerformed(ActionEvent e)
      {
        WebGUI.this.stopSim();
      }
    });
    reset.setText("Reset");

    Insets insets = new Insets(0, 0, 0, 0);
    GridBagLayout layout = new GridBagLayout();
    layout.setConstraints(canvas, new GridBagConstraints(0, 0, 3, 1, 1, 1,
            GridBagConstraints.BASELINE, 0, insets, 0, 0));
    layout.setConstraints(run, new GridBagConstraints(0, 1, 1, 1, 1, 1,
        GridBagConstraints.BASELINE, 0, insets, 0, 0));
    layout.setConstraints(clear, new GridBagConstraints(1, 1, 1, 1, 1, 1,
        GridBagConstraints.BASELINE, 0, insets, 0, 0));
    layout.setConstraints(reset, new GridBagConstraints(2, 1, 1, 1, 1, 1,
            GridBagConstraints.BASELINE, 0, insets, 0, 0));
    setLayout(layout);

    add(canvas);
    add(run);
    add(clear);
    add(reset);
  }

  /* (non-Javadoc)
   * @see java.applet.Applet#start()
   */
  @Override
  public void start()
  {
    hasFocus = true;
  }

  /* (non-Javadoc)
   * @see java.applet.Applet#start()
   */
  @Override
  public void stop()
  {
    hasFocus = false;
  }

  /* (non-Javadoc)
   * @see java.applet.Applet#start()
   */
  @Override
  public void destroy()
  {
    state = null;
    canvas.destroy();
  }
  
  private void clear()
  {
    if (state.simShouldRun())
      stopSim();
    state.clearAll();
    repaint();
  }

  private void startSim()
  {
    state.initSim();

    // Perform a step in the simulation.
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        stepSim();
      }
    });
  }

  private void stepSim()
  {
    if (!hasFocus)
    {
      stopSim();
      return;
    }

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
          stepSim();
        }
      });
    }
  }

  private void stopSim()
  {
    if (state != null)
      state.destroySim();
  }
}
