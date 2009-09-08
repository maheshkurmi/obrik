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

package com.fropsoft.obrik.rec;

/**
 * @author jamoozy
 */
public abstract class AbstractUserEvent implements UserEvent
{
  /** The time this was created. */
  protected long timestamp;

  /**
   * Creates a new timestamped user event.
   * @param timestamp
   *          When this was created.
   */
  protected AbstractUserEvent(long timestamp)
  {
    this.timestamp = timestamp;
  }

  /* (non-Javadoc)
   * @see com.fropsoft.obrik.rec.UserEvent#getTimestamp()
   */
  public long getTimestamp()
  {
    return timestamp;
  }
}
