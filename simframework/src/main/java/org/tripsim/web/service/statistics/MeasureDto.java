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
package org.tripsim.web.service.statistics;

import java.io.Serializable;
import java.util.List;

public class MeasureDto implements Serializable {

	private static final long serialVersionUID = 1L;

	long linkId;

	long startFrame;

	// series in the format of
	// a list of points [time1,position1],[time2,position2],...
	List<List<List<Number>>> serieses;

	MeasureDto(long linkId, long startFrame) {
		this.linkId = linkId;
		this.startFrame = startFrame;
	}

	public List<List<List<Number>>> getSerieses() {
		return serieses;
	}

	void setSerieses(List<List<List<Number>>> serieses) {
		this.serieses = serieses;
	}

}
