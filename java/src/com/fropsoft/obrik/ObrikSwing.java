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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class ObrikSwing
{
  public static void createAndShowGUI()
  {
    JFrame frame = new JFrame("Obrik");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Add the ubiquitous "Hello World" label.
    JLabel label = new JLabel("One day, Obrik will be here. ^_^");
    frame.getContentPane().add(label);

    // TODO Add components to the frame here...

    //Display the window.
    frame.pack();
    frame.setVisible(true);

    // Don't do any more GUI work here as advised by a Sun thread safety page at:
    // <http://java.sun.com/products/jfc/tsc/articles/threads/threads1.html>
  }
  
  public static void main(String[] args)
  {
    // Schedule a job for the event-dispatching thread:
    //   ... creating and showing Obrik's GUI.
    //
    // Why does Sun want it done this way?
    SwingUtilities.invokeLater(new Runnable()
    {
        public void run()
        {
            createAndShowGUI();
        }
    });
  }
}
