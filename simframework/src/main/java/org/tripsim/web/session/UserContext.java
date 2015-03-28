package org.tripsim.web.session;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.engine.simulation.SimulationProjectBuilder;
import org.tripsim.engine.simulation.SimulationSettings;
import org.tripsim.web.Sequence;
import org.tripsim.web.service.ProjectService;
import org.tripsim.web.service.entity.NetworkService;
import org.tripsim.web.service.entity.OdService;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	NetworkService networkService;
	@Autowired
	OdService odService;
	@Autowired
	ProjectService projectService;

	private Sequence sequence;;
	private Network network;
	private OdMatrix odMatrix;
	private SimulationSettings settings;

	public void clear() {
		sequence = null;
		network = null;
		odMatrix = null;
		settings = null;
	}

	public Sequence getSequence() {
		if (sequence == null) {
			sequence = projectService.newSequence();
		}
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	public void setSequence(long init) {
		this.sequence = projectService.newSequence(init);
	}

	public Network getNetwork() {
		if (network == null) {
			network = networkService.createNetwork();
		}
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
		this.odMatrix = null;
	}

	public OdMatrix getOdMatrix() {
		if (odMatrix == null) {
			odMatrix = odService.createOdMatrix(getNetwork().getName());
		}
		return odMatrix;
	}

	public void setOdMatrix(OdMatrix odMatrix) {
		this.odMatrix = odMatrix;
	}

	public SimulationSettings getSettings() {
		if (settings == null) {
			settings = projectService.newSettings();
		}
		return settings;
	}

	public void setSettings(SimulationSettings settings) {
		this.settings = settings;
	}

	public SimulationProject asProject() {
		return new SimulationProjectBuilder().withNetwork(getNetwork())
				.withOdMatrix(getOdMatrix()).withSettings(getSettings())
				.build();
	}

	public void importProject(SimulationProject project) {
		network = project.getNetwork();
		odMatrix = project.getOdMatrix();
		settings = project.getSettings();
		setSequence(network == null ? null : network.getHighestElementId());
	}
}
