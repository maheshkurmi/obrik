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

import com.fropsoft.geometry.Line;
import com.fropsoft.geometry.Shape;
import com.fropsoft.obrik.Box;

import java.util.LinkedList;
import java.util.Vector;

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
  private static LineConnection gaugeConnection(Line l1, Line l2)
  {
    int l1point = LineConnection.TAIL;
    int l2point = LineConnection.TAIL;
    double next, min = Double.MAX_VALUE;

    if ((next = l1.getStartPoint().distanceTo(l2.getStartPoint())) < min)
    {
      min = next;
    }
    else if ((next = l1.getEndPoint().distanceTo(l2.getStartPoint())) < min)
    {
      min = next;
      l1point = LineConnection.HEAD;
    }
    else if ((next = l1.getStartPoint().distanceTo(l2.getEndPoint())) < min)
    {
      min = next;
      l2point = LineConnection.HEAD;
    }
    else if ((next = l1.getEndPoint().distanceTo(l2.getEndPoint())) < min)
    {
      min = next;
      l1point = LineConnection.HEAD;
      l2point = LineConnection.HEAD;
    }

    double overlap1 = l1.getBounds().overlap(l2.getBounds());
    double overlap2 = l2.getBounds().overlap(l1.getBounds());

    // Use 1-overlap for both bboxes and (length-min)/length for both lines.
    double gage = (1 - overlap1) * (1 - overlap2) * (l1.length() - min) *
              (l2.length() - min) / (l1.length() * l2.length());

    // If this is negative, that means that one of the lines is shorter than
    // the gap between the closest endpoints.  "-1" is not a valid probability
    // (it's not in [0,1]), so return 0 instead.
    return new LineConnection(l1, l1point, l2, l2point, gage < 0 ? 0 : gage);
  }

//  /**
//   * Represents a loop in the graph.
//   */
//  private static class GraphLoop
//  {
//    /**
//     * Class used to store search queues for the breadth-first search.
//     */
//    private static class SearchQueue
//    {
//      /**
//       * Sum of the probabilities of all the nodes this represents.
//       */
//      public double prob;
//
//      /**
//       * The place this loop started.
//       */
//      public SearchNode root;
//
//      /**
//       * The end of this loop.
//       */
//      public SearchNode curr;
//
//      /**
//       * Creates a new search queue whose root node is representative of the
//       * passed line.
//       */
//      public SearchQueue(LineNode ln)
//      {
//        root = curr = new SearchNode(ln, this);
//      }
//
//      /**
//       * Adds a new line to the list
//       */
//      public void addNewNode(LineNode ln, int prevHT, int currHT)
//      {
//        SearchNode next = new SearchNode(ln, this);
//        if (prevHT == LineConnection.HEAD)
//          curr.head = next;
//        else
//          curr.tail = next;
//        if (currHT == LineConnection.HEAD)
//          next.head = curr;
//        else
//          next.tail = curr;
//        curr = next;
//      }
//
//      /**
//       * Makes a deep copy of this search queue.
//       * @return The new search queue.
//       */
//      public SearchQueue clone()
//      {
//        SearchQueue that = new SearchQueue(root.lineNode);
//        that.prob = this.prob;
//
//        int headOrTail = (root.head != null) ?
//                LineConnection.HEAD : LineConnection.TAIL;
//        SearchNode thisCurr = root;
//
//        while (thisCurr != null)
//        {
//          SearchNode next, thisNext;
//          if (headOrTail == LineConnection.HEAD)
//          {
//            next = new SearchNode(thisCurr.head.lineNode, that);
//            that.curr.head = next;
//            thisNext = thisCurr.head;
//          }
//          else
//          {
//            next = new SearchNode(thisCurr.tail.lineNode, that);
//            that.curr.tail = next;
//            thisNext = thisCurr.tail;
//          }
//
//          if (thisNext.head == thisCurr)
//            next.head = that.curr;
//          else
//            next.tail = that.curr;
//          that.curr = next;
//          thisCurr = thisNext;
//        }
//        return that;
//      }
//
//      public boolean equals(Object that)
//      {
//        // TODO
//        return false; //that.getClass() == SearchQueue.class && ((SearchQueue)that);
//      }
//
//      /**
//       * One node in the search queue.
//       */
//      private static class SearchNode
//      {
//        /**
//         * The line that that the head of the line this node represents is
//         * connecting to.
//         */
//        public SearchNode head;
//
//        /**
//         * The line that that the tail of the line this node represents is
//         * connecting to.
//         */
//        public SearchNode tail;
//
//        /**
//         * The queue this is a part of.
//         */
//        public SearchQueue queue;
//
//        /**
//         * The line this node represents.
//         */
//        public final LineNode lineNode;
//
//        /**
//         * Creates a new search node representing the passed line.
//         */
//        public SearchNode(LineNode lineNode, SearchQueue queue)
//        {
//          this.lineNode = lineNode;
//          this.queue = queue;
//        }
//      }
//    }
//
//    /**
//     * Finds the best loop in a graph using breadth-first search.  This
//     * ensures there is no 0-valued edge in the loop.  If no such loop could
//     * be found, this will return null.
//     *
//     * @param graph
//     *          The graph to search.
//     * @return The best loop or null if none could be found.
//     */
//    public static GraphLoop findBestLoop(LineGraph graph)
//    {
//      LinkedList<SearchQueue> list = new LinkedList<SearchQueue>();
//
//      // Populate a list to search.
//      for (int i = 0; i < graph.lns.length; i++)
//        list.addLast(new SearchQueue(graph.lns[i].line));
//
//      // Build the list of loops.
//      while (list.size() > 0)
//      {
//        SearchQueue next = list.removeFirst();
//        if (next.head != null)
//        {
//          if (next.head != next.queue.root)
//          {
//            for (int i = 0; i < next.lineNode.head
//              ;
//          }
//          else
//          {
//          }
//        }
//      }
//
//      return null;
//    }
//
//    /**
//     * Probability of this loop being awesome.
//     */
//    public final double prob;
//
//    /**
//     * The lines involved in the loop.
//     */
//    public LinkedList<LineNode> loop;
//
//    /**
//     */
//    public GraphLoop(LineNode start)
//    {
//      prob = 0;
//      loop = new LinkedList<LineNode>();
//      loop.addLast(start);
//    }
//  }

  /**
   * A complete, undirected graph representing all lines and how they relate
   * to each other in terms of a {@link LineConnection} objects.  Nodes are
   * {@link LineNode} objects, and edges are {@link LineConnection} objects.
   */
  private static class LineGraph
  {
    /**
     * The line nodes.
     */
    private LineNode[] lns;

    /**
     * Creates a new line graph for the set of lines.
     *
     * @param lines
     *          The set of lines to create the graph for.
     */
    public LineGraph(Line... lines)
    {
      lns = new LineNode[lines.length];
      for (int i = 0; i < lines.length; i++)
      {
        lns[i] = new LineNode(lines[i], lines.length);
        lns[i].tailProbs[i] = 0.0;
        lns[i].headProbs[i] = 0.0;
      }
    }

    /**
     * Sets the connection between node i and j.
     *
     * @param i
     *          The first node index.
     * @param j
     *          The second node index.
     * @param lc
     *          The relationship between the two nodes.
     */
    public void setLC(int i, int j, LineConnection lc)
    {
      if (lc.l1 != lns[i].line || lc.l2 != lns[j].line)
        throw new RuntimeException("Wrong order.");

      lns[i].tailProbs[j] = lc.side1 == LineConnection.TAIL ? lc.prob : 0.0;
      lns[i].headProbs[j] = lc.side1 == LineConnection.HEAD ? lc.prob : 0.0;
      lns[j].tailProbs[i] = lc.side2 == LineConnection.TAIL ? lc.prob : 0.0;
      lns[j].headProbs[i] = lc.side2 == LineConnection.HEAD ? lc.prob : 0.0;
    }
  }

  /**
   * A node in the {@link LineGraph}.
   */
  private static class LineNode
  {
    /**
     * The line this is a node for.
     */
    public final Line line;

    /**
     * Probabilities of this head connecting somewhere else.
     */
    public final double[] headProbs;

    /**
     * Probabilities of this tail connecting somewhere else.
     */
    public final double[] tailProbs;

    /**
     * Creates a new node that is part of a graph with numLines members, for
     * the passed line.
     *
     * @param line
     *          The line this node represents.
     * @param numLines
     *          The number of nodes in the graph.
     */
    public LineNode(Line line, int numLines)
    {
      this.line = line;
      this.headProbs = new double[numLines];
      this.tailProbs = new double[numLines];
    }
  }

  /**
   * Used as a return value for the {@link #gaugeConnection(Line,Line)}
   * method.  This represents the probability that two lines were meant to be
   * connected AND which ends of the lines were meant to be connected.
   */
  private static class LineConnection
  {
    /**
     * Field that signifies the start point.
     */
    public static int TAIL = 1;

    /**
     * Field that signifies the end point.
     */
    public static int HEAD = 2;

    /**
     * The one line.
     */
    public final Line l1;

    /**
     * The side of the one line that's closest to the other line.
     *
     * @see {@link #TAIL}, {@link #HEAD}
     */
    public final int side1;

    /**
     * The other line.
     */
    public final Line l2;

    /**
     * The side of the other line that's closest to the one line.
     *
     * @see {@link #TAIL}, {@link #HEAD}
     */
    public final int side2;

    /**
     * Probability that these two lines were meant to be connected.
     */
    public final double prob;

    /**
     * Creates a new object with all data.
     *
     * @param l1
     *          The one line.
     * @param side1
     *          The side of the one line, closest to the other line.
     * @param l2
     *          The other line.
     * @param side2
     *          The side of the other line, closest to the one line.
     * @param p
     *          The probability these lines were meant to be paired.
     */
    public LineConnection(Line l1, int side1, Line l2, int side2, double p)
    {
      this.l1 = l1;
      this.side1 = side1;
      this.l2 = l2;
      this.side2 = side2;
      prob = p;
    }
  }

  /**
   * Creaes a new box recognizer.
   */
  public BoxRecognizer()
  {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.fropsoft.sketch.ItemRecognizer#gauge(com.fropsoft.geometry.Shape[])
   */
  public double gauge(Shape... shapes)
  {
    Line[] lines = Line.findLines(shapes);

    LineGraph graph = new LineGraph(lines);

    // Build a graph that represents the relationship between all lines on the
    // canvas.
    for (int i = 0; i < lines.length; i++)
      for (int j = i + 1; j < lines.length; j++)
        graph.setLC(i, j, BoxRecognizer.gaugeConnection(lines[i], lines[j]));

    return 0;

//    GraphLoop loop = GraphLoop.findBestLoop(graph);
//    if (loop == null)
//    {
//      return 0;
//    }
//    else
//    {
//      return loop.prob;
//    }
  }
}
