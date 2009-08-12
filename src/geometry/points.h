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

#ifndef POINTS_H_
#define POINTS_H_

/**
 * All things obrik.
 */
namespace obrik
{
  /**
   * All things having to do with screen geometry.
   */
  namespace geometry
  {
    /**
     * A 2-dimensional point.
     */
    class Point2D
    {
    protected:

      /**
       * The position of the point.
       */
      int x, y;

    public:

      /**
       * Creates a new 2-dimensional point.
       */
      Point2D(int x, int y);

      /**
       * Get's the x coordinate of this point.
       */
      virtual int getX();

      /**
       * Get's the y coordinate of this point.
       */
      virtual int getY();
    };

    /**
     * Represents a timestamped 2-dimensional point.
     */
    class Point2DT : Point2D
    {
    protected:

      /**
       * The time this point was created.
       */
      long t;

    public:

      /**
       * Creates a new 2D point with timestamp.
       */
      Point2DT(int x, int y, long t);

      /**
       * Gets the time this point was created.
       */
      virtual long getT();
    };

    /**
     * A class that keeps track of the position, time, and pen velocity for a
     * point.
     */
    class Point2DTV : Point2DT
    {
    protected:

      /**
       * The velocity of the pen at this point in pixels-per-second.
       */
      double v;

    public:

      /**
       * Create a new Point2DTV at (x,y) at time t with speed v.
       */
      Point2DTV(int x, int y, long t, double v);

      /**
       * Gets the velocity of the pen when it was here in pixels-per-second.
       */
      virtual int getV();
    };
  }
}

#endif
