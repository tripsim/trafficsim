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
package org.tripsim.data.dom;

import java.util.HashMap;
import java.util.Map;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public enum TypeCategoryDo {

	LINK_TYPE("LinkType"),

	NODE_TYPE("NodeType"),

	VEHICLE_TYPE("VehicleType"),

	DRIVER_TYPE("DriverType"),

	PEDESTRAIN_TYPE("PedenstrainType"),

	EVENT_TYPE("EventType"),

	SIGNAL_TYPE("SignalType");

	private String displayName;

	TypeCategoryDo(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	private static final Map<String, TypeCategoryDo> typeCategories = new HashMap<String, TypeCategoryDo>();

	static {
		for (TypeCategoryDo type : values()) {
			typeCategories.put(type.displayName, type);
		}
	}

	public static TypeCategoryDo getByDisplayName(String category) {
		return typeCategories.get(category);
	}
}