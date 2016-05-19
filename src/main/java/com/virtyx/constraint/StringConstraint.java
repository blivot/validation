package com.virtyx.constraint;

public abstract class StringConstraint extends Constraint<String> {
	
	public StringConstraint() {
		super(String.class);
	}

	static public class Min extends StringConstraint {

		private Integer value;

		private final static String ERROR = "The string '%s' needs to be at least %d characters long";

		public Min(Integer value) {
			super();
			this.value = value;
		}
		
		@Override
		public boolean valid(String v) {
			return v == null || v.length() >= value;
		}

		@Override
		protected String getErrorMessage(String key, String object) {
			return String.format(ERROR, object, value);
		}
	}
	
	static public class Max extends StringConstraint {

		private Integer value;

		private final static String ERROR = "The string '%s' needs to be less than or equal to %d characters long";

		public Max(Integer value) {
			super();
			this.value = value;
		}
		
		@Override
		public boolean valid(String v) {
			return v == null || v.length() <= value;
		}

		@Override
		protected String getErrorMessage(String key, String object) {
			return String.format(ERROR, object, value);
		}
	}

}
