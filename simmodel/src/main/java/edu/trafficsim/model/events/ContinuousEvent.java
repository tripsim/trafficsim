/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.model.events;

import edu.trafficsim.api.model.Motion;
import edu.trafficsim.model.core.MovingObject;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class ContinuousEvent extends AbstractEvent {

	private static final long serialVersionUID = 1L;

	public final static class EventAgent extends MovingObject {

		private static final long serialVersionUID = 1L;

		public EventAgent(long id, String name, int startFrame) {
			super(id, startFrame);
		}

		@Override
		protected void onUpdate(Motion motion) {

		}

	}

	@SuppressWarnings("unused")
	private EventAgent agent;

	public ContinuousEvent(long id, String name, double startTime,
			double endTime, int startFrame) {
		super(id, startTime, endTime);
		agent = new EventAgent(id, name, startFrame);
	}

}
