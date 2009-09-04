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

package com.fropsoft.sketch.action;

import java.util.Vector;

import com.fropsoft.obrik.item.Anchor;
import com.fropsoft.obrik.item.ClosedRegion;
import com.fropsoft.obrik.item.Item;

/**
 * @author jamoozy
 *
 */
public class AnchorActionRecognizer extends AbstractActionRecognizer
{
  /** E */
  protected enum Action { ACT, GAUGE };

  /**
   * 
   */
  public AnchorActionRecognizer()
  {
    
  }

  /* (non-Javadoc)
   * @see com.fropsoft.sketch.ActionRecognizer#gauge(java.util.Vector)
   */
  public double gauge(Vector<Item> items)
  {
    return gaugeOrAct(items, Action.GAUGE);
  }

  private double gaugeOrAct(Vector<Item> items, Action action)
  {
    // Build some 
    Vector<Anchor> anchors = new Vector<Anchor>(items.size());
    Vector<ClosedRegion> crs = new Vector<ClosedRegion>(items.size());
    for (Item i : items)
    {
      if (i instanceof Anchor)
        anchors.add((Anchor)i);
      else if (i instanceof ClosedRegion)
        crs.add((ClosedRegion)i);
    }
    
    for (Anchor a : anchors)
      for (ClosedRegion cr : crs)
        if (a.getBBox().overlap(cr.getBBox()) > 0)
        {
          if (action == Action.ACT)
          {
            items.remove(a);
            cr.setAnchored(true);
          }
          return 1;
        }

    return 0;
  }

  /* (non-Javadoc)
   * @see com.fropsoft.sketch.ActionRecognizer#gauge(java.util.Vector)
   */
  @Override
  public void act(Vector<Item> items)
  {
    if (gaugeOrAct(items, Action.ACT) == 0)
      throw new RuntimeException("Invalid item list!");
  }
}
