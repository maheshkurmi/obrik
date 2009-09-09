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

import java.util.HashMap;
import java.util.Vector;

/**
 * This is a data structure that gives O(1) inserts and O(log n) deletes.
 * @author jamoozy
 */
public class FibonacciHeap<T>
{
  /** Buckets of roots by rank. */
  private final Vector<Vector<Node>> buckets;

  /** Map of objects --&gt; nodes. */
  private final HashMap<T,Node> map;

  /**
   * Creates a new fibonacci heap.
   */
  public FibonacciHeap()
  {
    buckets = new Vector<Vector<Node>>();
    map = new HashMap<T,Node>();
  }

  /**
   * Inserts a new object into this heap.
   * @param key
   *          The value of the object.
   * @param obj
   *          The object to insert.
   */
  public void insert(int key,T obj)
  {
    Node node = new Node(key, obj);
    addTo(0, node);
    map.put(obj, node);
  }

  /**
   * Deletes and returns the object with the lowest key.
   * @return The object with the lowest key.
   */
  public T deleteMin()
  {
    // Do the rank-based comparison.
    for (int b = 0; b < buckets.size(); b++)
    {
      Vector<Node> bucket = buckets.get(b);
      for (int i = 1; i < bucket.size(); i += 2)
      {
        Node node1 = bucket.remove(i-1);
        Node node2 = bucket.remove(i);
        int comparison = node1.compareTo(node2);
        if (comparison < 0)
        {
          node1.merge(node2);
          addTo(b+1, node1);
        }
        else
        {
          node2.merge(node1);
          addTo(b+1, node2);
        }
      }
    }

    // Find the lowest root.
    Node min = null;
    for (Vector<Node> bucket : buckets)
      for (Node root : bucket)
        if (min == null || min.compareTo(root) > 0)
          min = root;
    remove(min);
    return min.data;
  }

  /**
   * Update the key of the object.
   * @param key
   *          The new key.
   * @param obj
   *          The associated object.
   */
  public void update(int key, T obj)
  {
    Node node = map.get(obj);
    node.key = key;
  }

  /**
   * Remove the passed node from the graph entirely.
   * @param node
   *          The node to remove.
   */
  private void remove(Node node)
  {
    if (node.parent != null)
    {
      if (node.parent.first_kid == node)
        node.parent.first_kid = node.right;
      
      if (node.parent.isMarked())
      {

      }
      else
      {
        node.parent.sad = true;
      }
    }
    // TODO finish me.
  }

  /**
   * Removes the node associated with the passed object.
   * @param obj
   *          The object of the node to remove.
   */
  public void remove(T obj)
  {
    Node node = map.get(obj);
    remove(node);
  }

  /**
   * Add the given node to the bucket with the given index.
   * @param b
   *          Bucket index.
   * @param root
   *          The node to add.
   */
  private void addTo(int b, Node root)
  {
    // Ensure this is big enough.
    for (int i = buckets.size(); i <= b; i++)
      buckets.add(new Vector<Node>(100));

    // Add it to the proper bucket.
    buckets.get(b).add(root);
  }

  /**
   * A node in this fibonacci heap.
   */
  private class Node
  {
    /** Parent node. */
    Node parent = null;

    /** "left-most" child node. */
    Node first_kid = null;

    /** Next node with same parent. */
    Node right = null;

    /** The key (probably a number). */
    int key = Integer.MAX_VALUE;

    /** The data (anything). */
    T data = null;

    /** The number of children this has. */
    int rank = 0;

    /** Sad when child is lost. */
    boolean sad = false;

    /**
     * Creates a new node.
     * @param data
     *          The data this node holds.
     */
    Node(int key, T data)
    {
      this.key = key;
      this.data = data;
    }

    /**
     * Determines if this is marked (lost a child).
     * @return Whether this has lost a child.
     */
    boolean isMarked()
    {
      if (parent == null)
        sad = false;
      return sad;
    }

    /**
     * Decreases the rank of this an its parents by delta.
     * @param delta
     *          The amount to decrease by.
     */
    void decreaseRank(int delta)
    {
      parent.decreaseRank(delta);
      rank -= delta;
    }

    /**
     * Compares this node to that node.
     * @param that
     *          The other node.
     * @returns A negative number, 0, or a positive number if this is less
     * than, equal to, or greater than that.
     *
     * @see {@link java.util.Comparable#compareTo(T)}
     */
    int compareTo(Node that)
    {
      return that.key - this.key;
    }

    /**
     * Merges that node to be a child of this node.
     * @param that
     *          The newest child node.
     */
    void merge(Node that)
    {
      that.right = this.first_kid;
      this.first_kid = that;
      that.parent = this;
      this.rank += that.rank + 1;
    }
  }
}
