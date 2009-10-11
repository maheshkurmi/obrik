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

package com.fropsoft.obrik.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.fropsoft.obrik.State;
import com.fropsoft.obrik.rec.Recorder;

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

  /** A log of this run. */
  private Recorder recorder;

  /* (non-Javadoc)
   * @see java.applet.Applet#init()
   */
  @Override
  public void init()
  {
    super.init();
    resize(400, 430);
    recorder = new Recorder("WebGUI", 0, 1);

    state = new State(recorder);

    // Create the canvas
    canvas = new SwingCanvas(state);
    canvas.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mousePressed(MouseEvent e)
      {
        WebGUI.this.recorder.startStroke(e.getX(), e.getY());
      }

      @Override
      public void mouseReleased(MouseEvent e)
      {
        WebGUI.this.recorder.endPoint(e.getX(), e.getY());
      }
    });
    canvas.addMouseMotionListener(new MouseAdapter()
    {
      @Override
      public void mouseDragged(MouseEvent e)
      {
        WebGUI.this.recorder.addPoint(e.getX(), e.getY());
      }
    });

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
    recorder.storeToFile();
    canvas.destroy();
  }
  
  private void clear()
  {
    recorder.addClear();
    if (state.simShouldRun())
      stopSim();
    state.clearAll();
    repaint();
  }

  private void startSim()
  {
    recorder.addRun();
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

  /**
   * Takes one step through the simulation.
   */
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
    recorder.addReset();
    if (state != null)
      state.destroySim();
  }
}
