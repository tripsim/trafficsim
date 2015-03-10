package org.tripsim.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeUtils {

	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final String newDate() {
		return DATE_FORMAT.format(new Date());
	}
}
