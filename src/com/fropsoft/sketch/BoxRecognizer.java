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
import com.fropsoft.geometry.Shape;
import com.fropsoft.obrik.Box;

/**
 * Recognizes a box.
 *
 * @author jamoozy
 *
 * @see {@link Box}
 */
public class BoxRecognizer extends AbstractItemRecognizer
{
  /**
   * Determines how likely it is that l1 and l2 connect together.  This is a
   * convenience function used by {@link #gauge(Shape...)} to group
   *
   * @param l1
   *          The first line.
   * @param l2
   *          The second line.
   * @return The probability these lines connect and where they connect.
   */
  private static LGNode gaugeConnection(Line l1, Line l2)
  {
    LineEnd.End l1point = LineEnd.End.TAIL;
    LineEnd.End l2point = LineEnd.End.TAIL;
    double next, min = Double.MAX_VALUE;

    if ((next = l1.getStartPoint().distanceTo(l2.getStartPoint())) < min)
      min = next;

    if ((next = l1.getEndPoint().distanceTo(l2.getStartPoint())) < min)
    {
      min = next;
      l1point = LineEnd.End.HEAD;
      l2point = LineEnd.End.TAIL;
    }

    if ((next = l1.getStartPoint().distanceTo(l2.getEndPoint())) < min)
    {
      min = next;
      l1point = LineEnd.End.TAIL;
      l2point = LineEnd.End.HEAD;
    }

    if ((next = l1.getEndPoint().distanceTo(l2.getEndPoint())) < min)
    {
      min = next;
      l1point = LineEnd.End.HEAD;
      l2point = LineEnd.End.HEAD;
    }

    double overlap1 = l1.getBounds().overlap(l2.getBounds());
    double overlap2 = l2.getBounds().overlap(l1.getBounds());

    // Use 1-overlap for both bboxes and (length-min)/length for both lines.
    double gage = (1 - overlap1) * (1 - overlap2) * (l1.length() - min) *
              (l2.length() - min) / (l1.length() * l2.length());

    // If this is negative, that means that one of the lines is shorter than
    // the gap between the closest endpoints.  "-1" is not a valid probability
    // (it's not in [0,1]), so return 0 instead.
    return new LGNode(gage < 0 ? 0 : gage,
            new LineEnd(l1, l1point), new LineEnd(l2, l2point));
  }

  /**
   * A complete, undirected graph representing all lines and how they relate
   * to each other in terms of a {@link LGNode} objects.  Nodes are
   * {@link LGEdge} objects, and edges are {@link LGNode} objects.
   */
  private static class LineGraph
  {
    /**
     * The line nodes.
     */
    public final LGEdge[] lns;

    /**
     * Creates a new line graph for the set of lines.
     *
     * @param lines
     *          The set of lines to create the graph for.
     */
    public LineGraph(int numNodes)
    {
      lns = new LGEdge[numNodes];
//      lns = new LGNode[lines.length];
//      for (int i = 0; i < lines.length; i++)
//      {
//        lns[i] = new LGNode(lines[i], lines.length);
//        lns[i].tailProbs[i] = 0.0;
//        lns[i].headProbs[i] = 0.0;
//      }
    }

//    /**
//     * Sets the connection between node i and j.
//     *
//     * @param i
//     *          The first node index.
//     * @param j
//     *          The second node index.
//     * @param lc
//     *          The relationship between the two nodes.
//     */
//    public void setLC(int i, int j, LGEdge lc)
//    {
//      if (lc.l1 != lns[i].line || lc.l2 != lns[j].line)
//        throw new RuntimeException("Wrong order.");
//
//      lns[i].tailProbs[j] = lc.side1 == LineEnd.End.TAIL ? lc.prob : 0.0;
//      lns[i].headProbs[j] = lc.side1 == LineEnd.End.HEAD ? lc.prob : 0.0;
//      lns[j].tailProbs[i] = lc.side2 == LineEnd.End.TAIL ? lc.prob : 0.0;
//      lns[j].headProbs[i] = lc.side2 == LineEnd.End.HEAD ? lc.prob : 0.0;
//    }
  }

  /**
   * A node in the {@link LineGraph}.
   */
  private static class LGEdge
  {
    /**
     * The line this is a node for.
     */
    public final Line line;

    /**
     * Probabilities of this head connecting somewhere else.
     */
    public final LGNode head;

    /**
     * Probabilities of this tail connecting somewhere else.
     */
    public final LGNode tail;

    /**
     * Creates a new node that is part of a graph with numLines members, for
     * the passed line.
     *
     * @param line
     *          The line this node represents.
     * @param numLines
     *          The number of nodes in the graph.
     */
    public LGEdge(Line line, LGNode head, LGNode tail)
    {
      this.line = line;
      this.head = head;
      this.tail = tail;
    }
  }

  /**
   * Used as a return value for the {@link #gaugeConnection(Line,Line)}
   * method.  This represents the probability that two lines were meant to be
   * connected AND which ends of the lines were meant to be connected.
   */
  private static class LGNode
  {
    /**
     * The list of line ends that converge here.
     */
    public final Vector<LineEnd> les;

    /**
     * Probability that these two lines were meant to be connected.
     */
    public double prob;

    /**
     * Creates a new object with all data.
     *
     * @param lineEnds
     *          The line ends that converge here.
     * @param p
     *          The probability these lines were meant to be paired.
     */
    public LGNode(double prob, LineEnd... lineEnds)
    {
      this.prob = prob;
      les = new Vector<LineEnd>();
      for (LineEnd e : lineEnds)
        les.add(e);
    }
  }

  /**
   * Represents one end of a line, either the head or the tail.  This is used
   * by the {@link LGNode} object to group ends of lines.  Ends must be known,
   * so that it's certain that one line doesn't have two or more lines coming
   * out of the same end.
   */
  private static class LineEnd
  {
    public enum End
    {
      /**
       * Field that signifies the start point.
       */
      TAIL,

      /**
       * Field that signifies the end point.
       */
      HEAD;
    }

    /**
     * Specifies which end of the line.
     *
     * @see {@link #TAIL}, {@link #HEAD}
     */
    public final End end;

    /**
     * The line.
     */
    public final Line line;

    /**
     * Creates a new line for the specified line at the specified end.
     */
    public LineEnd(Line line, End end)
    {
      this.line = line;
      this.end = end;
    }

    /**
     * Determines if that is the same as this.
     *
     * @param that
     *          The object to compare with.
     */
    @Override
    public boolean equals(Object that)
    {
      return that.getClass() == LineEnd.class &&
        ((LineEnd)that).line == line && ((LineEnd)that).end == end;
    }
  }

  /**
   * Creates a new box recognizer.
   */
  public BoxRecognizer()
  {
    super();
  }

  /* (non-Javadoc)
   * @see
   * com.fropsoft.sketch.ItemRecognizer#gauge(com.fropsoft.geometry.Shape[])
   */
  public double gauge(Shape... shapes)
  {
    Line[] lines = Line.findLines(shapes);

    Vector<LGNode> nodes = new Vector<LGNode>();

//    LineGraph graph = new LineGraph(lines.length);

    // Build a graph that represents the relationship between all lines on the
    // canvas.
    for (int i = 0; i < lines.length; i++)
      for (int j = i + 1; j < lines.length; j++)
        nodes.add(BoxRecognizer.gaugeConnection(lines[i], lines[j]));

    // Coallate LGNodes that fall on the same spacial point.
    for (int i = 0; i < nodes.size(); i++)
    {
      for (int j = i + 1; j < nodes.size(); j++)
      {
        // Check all nodes pairwise.
        LGNode a = nodes.get(i);
        LGNode b = nodes.get(j);

        boolean foundMatch = false;
        for (int u = 0; !foundMatch && u < a.les.size(); u++)
        {
          for (int v = 0; !foundMatch && v < b.les.size(); v++)
          {
            // In each pair, check all pairs of LineEnds.
            LineEnd x = a.les.get(u);
            LineEnd y = b.les.get(v);
            if (x.equals(y))
            {
              // Match found, so coallate.
              for (LineEnd le : b.les)
                if (!a.les.contains(le))
                  a.les.add(le);

              // Bookkeeping.
              a.prob += b.prob;
              nodes.remove(j);
              foundMatch = true;
              j -= 1;
            }
          }
        }
      }
    }

    // Normalize probabilities again.
    for (LGNode n : nodes)
      n.prob /= n.les.size() - 1;

    return 0;
  }
}
