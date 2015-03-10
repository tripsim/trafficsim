package org.tripsim.web.service.statistics;

import java.util.Arrays;
import java.util.List;

public class CoordDto {

	List<Number> numbers;

	CoordDto(Number... numbers) {
		this.numbers = Arrays.asList(numbers);
	}

	public List<Number> getNumbers() {
		return numbers;
	}

	@Override
	public String toString() {
		return "CoordDto [numbers=" + numbers + "]";
	}

}
