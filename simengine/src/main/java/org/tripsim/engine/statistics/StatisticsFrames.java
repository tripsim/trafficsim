/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.engine.statistics;

import java.util.Collection;
import java.util.Set;

import org.tripsim.util.MultiKeyedMap;

public class StatisticsFrames<T> {

	String simulationName;

	final MultiKeyedMap<Long, Long, T> states;

	StatisticsFrames() {
		states = new MultiKeyedMap<Long, Long, T>();
	}

	public Set<Long> getIds() {
		return states.getPrimayKeys();
	}

	public Set<Long> getSequences() {
		return states.getSecondaryKeys();
	}

	public T getState(long id, long sequence) {
		return states.get(id, sequence);
	}

	public Collection<T> getStatesById(long id) {
		return states.getByPrimary(id).values();
	}

	public Collection<T> getStatesBySequence(long sequence) {
		return states.getBySecondary(sequence).values();
	}

	void addState(long id, long sequence, T state) {
		states.put(id, sequence, state);
	}

	public String getSimulationName() {
		return simulationName;
	}

	@Override
	public String toString() {
		return "StatisticsFrames [simulationName=" + simulationName
				+ ", states=" + states + "]";
	}

}
