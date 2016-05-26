package com.virtyx.constraint;

public abstract class NumberConstraint extends Constraint<Number> {
	
	public NumberConstraint() {
		super(Number.class);
	}
	
	public static String fmt(double d) {
	    if(d == (long) d)
	        return String.format("%d",(long)d);
	    else
	        return String.format("%s",d);
	}
	
	static public class Min extends NumberConstraint {

		private Number value;

		private final static String ERROR = "The number %s cannot be less than %s";

		public Min(Number value) {
			super();
			this.value = value;
		}

		@Override
		public boolean valid(Number v) {
			return v.doubleValue() >= value.doubleValue();
		}

		@Override
		protected String getErrorMessage(String key, Number object) {
			return String.format(
					ERROR,
					NumberConstraint.fmt(object.doubleValue()),
					NumberConstraint.fmt(value.doubleValue())
			);
		}
	}
	
	static public class Max extends NumberConstraint {

		private Number value;

		private final static String ERROR = "The number %s cannot be greater than %s";

		public Max(Number value) {
			super();
			this.value = value;
		}

		@Override
		public boolean valid(Number v) {
			return v.doubleValue() <= value.doubleValue();
		}

		@Override
		protected String getErrorMessage(String key, Number object) {
			return String.format(
					ERROR,
					NumberConstraint.fmt(object.doubleValue()),
					NumberConstraint.fmt(value.doubleValue())
			);
		}
	}


}
