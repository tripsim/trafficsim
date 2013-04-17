package edu.trafficsim.model.demand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.trafficsim.model.Composition;

public class Randoms {

	public final static <T> T randomElement(Composition<T> composition,
			Random rand) {
		double threshold = rand.nextDouble();
		if (composition != null && composition.total() > 0) {
			T key = null;
			double sum = 0;
			for (T otherkey : composition.keys()) {
				key = otherkey;
				sum += composition.probability(otherkey);
				if (threshold <= sum)
					break;
			}
			return key;
		} else if (composition.keys().size() > 0) {
			randomElement(composition.keys(), rand);
		}
		return null;
	}

	public final static <T> T randomElement(Collection<T> c, Random rand) {
		List<T> shuffledList = new ArrayList<T>(c);
		Collections.shuffle(shuffledList, rand);
		return shuffledList.get(0);
	}

}
