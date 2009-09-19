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

import java.awt.event.MouseEvent;

import com.fropsoft.obrik.State;

import com.fropsoft.obrik.ui.SwingCanvas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 * @author jamoozy
 *
 */
public class TestSuite extends JPanel
{
  /** To shut eclipse up. */
  private static final long serialVersionUID = 7L;

  /** Run the test stuite. */
  public static void main(String[] args)
  {
    final TestSuite ts = new TestSuite();

    if (args.length > 0)
    {
      if (args[0].equals(""))
      {
        ts.showStrokeInterp();
      }
    }

    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        showGUI(ts);
      }
    });
  }

  /** Run the test suite with the given  */
  public static void showGUI(TestSuite ts)
  {
    JFrame root = new JFrame("Obrik Test Suite!");
    root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    root.setContentPane(ts);
    root.pack();
    root.setVisible(true);
  }

  private final State state;

  private final SwingCanvas canvas;

  /** */
  public TestSuite()
  {
    state = new State();
    canvas = new SwingCanvas(state);
    canvas.addMouseListener(new MouseInputAdapter()
    {
      public void mouseReleased(MouseEvent e)
      {
        state.clearAll();
      }
    });
    add(canvas);
  }

  /**  */
  public void showStrokeInterp()
  {
    state.clearAll();
  }
}
