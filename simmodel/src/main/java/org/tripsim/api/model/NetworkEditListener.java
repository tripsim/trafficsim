package org.tripsim.api.model;

public interface NetworkEditListener {

	void onNodeAdded(Node node);

	void onNodeRemoved(Node node);

	void onLinkAdded(Link link);

	void onLinkRemoved(Link link);

}
