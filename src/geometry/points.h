/**
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

namespace obrik
{
  namespace geometry
  {
    class Point2D
    {
    protected:

      int x, y;

    public:

      Point2D(int x, int y);

      virtual int getX();

      virtual int getY();
    };

    class Point2DT : Point2D
    {
    protected:

      long t;

    public:

      Point2DT(int x, int y, long t);

      virtual int getT();
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
