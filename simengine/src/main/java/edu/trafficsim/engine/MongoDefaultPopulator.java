package edu.trafficsim.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import edu.trafficsim.data.persistence.CompositionDao;
import edu.trafficsim.data.persistence.ElementTypeDao;

@Component
public class MongoDefaultPopulator implements
		ApplicationListener<ContextStartedEvent> {

	@Autowired
	ElementTypeDao elementTypeDao;
	@Autowired
	CompositionDao compositionDao;

	@Override
	public void onApplicationEvent(ContextStartedEvent event) {

	}

}