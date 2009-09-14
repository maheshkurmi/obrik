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

package com.fropsoft.util;

/**
 * A semi-persistent binary tree.  You can make lookups at any previous
 * point in time, but can only update the current time's version of the
 * tree.
 * @author jamoozy
 */
public class SPBinaryTree<T extends Comparable>
{
  /** The root of this tree. */
  private Node root;

  /** Creates a new binary tree at time 0. */
  public SPBinaryTree(T data)
  {
    root = new Node(data);
  }

  public void add(long ts, T data)
  {
    // TODO write me.
  }

  /** A node in this binary tree. */
  private class Node
  {
    /** The data this node contains. */
    T data;

    /** Modification in time. */
    ModBox mb;

    /** Left pointer (data is less). */
    Node left;

    /** Right pointer (data is greater). */
    Node right;

    /**
     * Creates a new node pointing to the passed data.
     * @param data
     *          The data this points to.
     */
    Node(T data)
    {
      this.data = data;
      mb = null;
      left = null;
      right = null;
    }

    /**
     * Compares this node to another.
     * <ul>
     *   <li>-1 ---&gt; this &lt; that</li>
     *   <li>-1 ---&gt; this &lt; that</li>
     *   <li>-1 ---&gt; this &lt; that</li>
     * </ul>
     *
     * @param that
     *          The node to compare to.
     * @return The comparison (see above).
     *
     * @see java.util.Comparable#compareTo()
     */
    int compareTo(Node that)
    {
      return this.data.compareTo(that.data);
    }

    /** Modification box. */
    abstract class ModBox
    {
      /** Time of this modification box. */
      final long ts;

      /** Construct a new modification box. */
      ModBox(long ts)
      {
        this.ts = ts;
      }
    }

    /** Logs a change to the data field. */
    class DataMod extends ModBox
    {
      /** The data this was changed to. */
      final T data;

      /**
       * Creates a new data modification.
       * @param type
       *          The type
       */
      DataMod(long ts, T data)
      {
        super(ts);
        this.data = data;
      }
    }

    /** Logs a change to a pointer. */
    class PointerMod extends ModBox
    {
      /** The new location of the node. */
      final Node node;

      /** Left not right. */
      final boolean lnr;

      /**
       * Creates a new pointer modification.
       * @param ts
       *          Time for this modification.
       * @param node
       *          The node to point to.
       * @param lnr
       *          Left not right.
       */
      PointerMod(long ts, Node node, boolean lnr)
      {
        super(ts);
        this.node = node;
        this.lnr = lnr;
      }
    }
  }
}
