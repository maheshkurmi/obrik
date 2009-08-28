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

import java.util.Vector;

import com.fropsoft.geometry.Line;
import com.fropsoft.geometry.Point2D;
import com.fropsoft.geometry.Shape;
import com.fropsoft.obrik.ClosedRegion;

/**
 * Closed region recognizer based on the discussion Ali and I had in his room on
 * 2009/08/24 at midnight.
 *
 * @author jamoozy
 */
public class ClosedRegionRecognizer extends AbstractItemRecognizer
{
  /**
   * Line end.  Heads or Tails?
   */
  private enum End { HEAD, TAIL }

  /**
   * Represents one side of a line (head or tail).
   */
  private class LineEnd
  {
    /**
     * The line that this is the LineEnd for.
     */
    public Line line;

    /**
     * Which end of the line this represents.
     */
    public End end;

    /**
     * Creates a new line end.
     *
     * @param line The line this is an end for.
     * @param end The end of the line this represents.
     */
    public LineEnd(Line line, End end)
    {
      this.line = line;
      this.end = end;
    }

    /**
     * Returns the correct endpoint of the line.  The point is taking from the
     * point data of the stroke that created the line.  If this represents the
     * "head" of the line, this will return the last point.  If this represents
     * the "tail" of the line, this will return the first point.
     *
     * @return The correct endpoint of the line.
     */
    public Point2D getPoint()
    {
      if (end == End.HEAD)
        return line.getEndPoint();
      else
        return line.getStartPoint();
    }

    /**
     * Returns a new object representing the opposite end of the same line.
     * Thus, this will be pretty much the same object, except the end property
     * will be "flipped" (HEAD -> TAIL or TAIL -> HEAD).
     *
     * @return The opposite end of this line.
     */
    public LineEnd otherEnd()
    {
      return new LineEnd(line, end == End.TAIL ? End.HEAD : End.TAIL);
    }

    /**
     * Determines if this object represents the other end of the same line
     * represented by that.  In other words, it returns whether "that" is
     * equivalent to what {@link #otherEnd()} would return.
     *
     * @param that
     *          The other line to compare with.
     * @return <code>true</code> if that represents the other end of the same
     *         line.
     */
    public boolean isOtherEnd(LineEnd that)
    {
      return this.line == that.line && this.end != that.end;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object.equals(Object)
     */
    @Override
    public boolean equals(Object that)
    {
      return that.getClass() == LineEnd.class && equals((LineEnd)that);
    }

    /**
     * Determines if this line end is equivalent to that line end.
     *
     * @param that
     *          The other line to compare with.
     * @return Same as {@link #equals(Object)}, really.
     */
    public boolean equals(LineEnd that)
    {
      return this.line == that.line && this.end == that.end;
    }
  }

  /**
   * A pool of lines that are connected in some way.
   */
  private class LinePool
  {
    public double probSum;
    public Vector<Line> lines;
    public LineEnd root;
    public LineEnd prev;
    public LineEnd last;

    public LinePool(LineEnd le, int startCapacity)
    {
      probSum = 0;
      lines = new Vector<Line>(startCapacity);
      lines.add(le.line);
      root = prev = last = le;
    }

    public boolean isValidNext(LineEnd le)
    {
      return le.line != last.line && !prev.equals(le);
    }

    public void add(LineEnd le)
    {
      lines.add(le.line);
      prev = last;
      last = le.otherEnd();
    }
  }

  /**
   * Gauges the connection "probability" between the two line ends.
   */
  public static double gaugeConnection(LineEnd le1, LineEnd le2)
  {
    double overlap1 = le1.line.getBounds().overlap(le2.line.getBounds());
    double overlap2 = le2.line.getBounds().overlap(le1.line.getBounds());

    // Use 1-overlap for both bboxes and (length-min)/length for both lines.
    double min = le1.getPoint().distanceTo(le2.getPoint());
    double gauge = (1 - overlap1) * (1 - overlap2) *
    (le1.line.length() - min) * (le2.line.length() - min)
    / (le1.line.length() * le2.line.length());

    // If this is negative, that means that one of the lines is shorter than
    // the gap between the closest endpoints.  "-1" is not a valid probability
    // (it's not in [0,1]), so return 0 instead.
    return gauge < 0 ? 0 : gauge;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.fropsoft.sketch.ItemRecognizer#gauge(com.fropsoft.geometry.Shape[])
   */
  public double gauge(Shape... shapes)
  {
    Line[] lines = Line.findLines(shapes);
    LineEnd[] les = new LineEnd[2*lines.length];
    for (int i = 0; i < lines.length; i++)
    {
      les[ 2*i ] = new LineEnd(lines[i], End.HEAD);
      les[2*i+1] = new LineEnd(lines[i], End.TAIL);
    }
    lines = null;

    // Exhaustive (naive) search.
    for (int s = 0; s < les.length; s++)  // s = starting line end
    {
      LinePool pool = new LinePool(les[s], les.length);
      boolean done = false;
      while (!done)
      {
        done = true;

        // Find the best connection to go to.
        double maxProb = 0;
        int bestN = -1;
        for (int n = 0; n < les.length; n++)  // n = next line end
        {
          if (!pool.isValidNext(les[n]))
            continue;

          double nextProb = gaugeConnection(les[n], pool.last);

          if (nextProb > maxProb)
          {
            maxProb = nextProb;
            bestN = n;
          }
        }

        if (maxProb > 0)
        {
          // If there are 2 or more, we avoid the same line being start, end, and
          // body.  This condition forces a pool.
          if (pool.lines.size() > 2)
          {
            done = false;
            pool.probSum += maxProb;
            pool.add(les[bestN]);

            // Loop done.
            if (les[bestN].isOtherEnd(pool.root))
            {
              lines = new Line[pool.lines.size()];
              lines = pool.lines.toArray(lines);
              setItem(new ClosedRegion(lines));
              return pool.probSum / pool.lines.size();
            }
          }
          else if (!les[bestN].isOtherEnd(pool.root))
          {
            done = false;
            pool.probSum += maxProb;
            pool.add(les[bestN]);
          }
        }
      }
    }

    return 0;
  }
}
