package edu.trafficsim.plugin;

public @interface Parameter {

	String name();
	double max() default Double.POSITIVE_INFINITY;
	double min() default Double.NEGATIVE_INFINITY;
}
