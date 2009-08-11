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

#include "points.h"

obrik::geometry::Point2D::Point2D(int x, int y)
  : x(x), y(y) {}

int obrik::geometry::Point2D::getX()
{
  return x;
}

int obrik::geometry::Point2D::getY()
{
  return y;
}

obrik::geometry::Point2DT::Point2DT(int x, int y, long t)
  : Point2D(x, y), t(t) {}

int obrik::geometry::Point2DT::getT()
{
  return t;
}

obrik::geometry::Point2DTV::Point2DTV(int x, int y, long t, double v)
  : Point2DT(x, y, t), v(v) {}

int obrik::geometry::Point2DTV::getV()
{
  return v;
}
