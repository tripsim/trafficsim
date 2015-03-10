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
package org.tripsim.engine.type;

import java.util.List;

import org.tripsim.api.model.TypesComposition;

public interface TypesManager {

	// Link
	String getDefaultLinkTypeName();

	List<String> getLinkTypeNames();

	LinkType getDefaultLinkType();

	LinkType getLinkType(String name);

	List<LinkType> getLinkTypes();

	void saveLinkType(LinkType type);

	// Node
	String getDefaultNodeTypeName();

	List<String> getNodeTypeNames();

	NodeType getDefaultNodeType();

	NodeType getNodeType(String name);

	List<NodeType> getNodeTypes();

	void saveNodeType(NodeType type);

	// Vehicle
	String getDefaultVehicleTypeName();

	List<String> getVehicleTypeNames();

	VehicleType getVehicleType(String name);

	VehicleType getDefaultVehicleType();

	List<VehicleType> getVehicleTypes();

	void saveVehicleType(VehicleType type);

	void deleteVehicleType(String name);

	// Driver
	String getDefaultDriverTypeName();

	List<String> getDriverTypeNames();

	DriverType getDriverType(String name);

	DriverType getDefaultDriverType();

	List<DriverType> getDriverTypes();

	void saveDriverType(DriverType type);

	void deleteDriverType(String name);

	// Type Composition Vehicle
	String getDefaultVehicleTypeCompositionName();

	List<String> getVehicleTypeCompositionNames();

	TypesComposition getDefaultVehicleTypeComposition();

	TypesComposition getVehicleTypeComposition(String name);

	List<TypesComposition> getVehicleTypeCompositions();

	long countCompositionWithVehicleType(String type);

	void insertVehicleTypesComposition(TypesComposition typesComposition);

	void deleteVehicleTypesComposition(String name);

	// Type Composition Driver
	String getDefaultDriverTypeCompositionName();

	List<String> getDriverTypeCompositionNames();

	TypesComposition getDefaultDriverTypeComposition();

	TypesComposition getDriverTypeComposition(String name);

	List<TypesComposition> getDriverTypeCompositions();

	long countCompositionWithDriverType(String type);

	void insertDriverTypesComposition(TypesComposition typesComposition);

	void deleteDriverTypesComposition(String name);

}
