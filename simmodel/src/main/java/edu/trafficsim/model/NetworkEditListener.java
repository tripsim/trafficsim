package edu.trafficsim.model;

import edu.trafficsim.model.core.ModelInputException;

public interface NetworkEditListener {

	void onNodeAdded(Node node);

	void onNodeRemoved(Node node);

	void onLinkAdded(Link link);

	void onLinkRemoved(Link link) throws ModelInputException;

}
