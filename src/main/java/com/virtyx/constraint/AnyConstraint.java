package com.virtyx.constraint;

public abstract class AnyConstraint extends Constraint<Object> {

	public AnyConstraint(Class<Object> type) {
		super(type);
	}

	protected Object convert(String key, Object value) {
		return value;
	}

	static public class Required extends AnyConstraint {

		private final static String ERROR = "'%s' is required";

		public Required() {
			super(Object.class);
		}

		@Override
		public boolean valid(Object v) {
			return v != null;
		}

		@Override
		protected String getErrorMessage(String key, Object object) {
			return String.format(ERROR, key);
		}
		
		@Override
		public boolean equals(Object other) {
			if (other == null) return false;
			
			return other.getClass().equals(this.getClass());
		}
	}
	
	static public class Forbidden extends AnyConstraint {

		private final static String ERROR = "'%s' is forbidden";

		public Forbidden() {
			super(Object.class);
		}

		@Override
		public boolean valid(Object v) {
			return v == null;
		}

		@Override
		protected String getErrorMessage(String key, Object object) {
			return String.format(ERROR, key);
		}
	}

	static public class Valid extends AnyConstraint {

		private final static String ERROR = "'%s' must equal '%s'";

		private Object value;
			
		public Valid(Object value) {
			super(Object.class);
			this.value = value;
		}

		@Override
		public boolean valid(Object v) {
			return v.equals(value);
		}

		@Override
		protected String getErrorMessage(String key, Object object) {
			return String.format(ERROR, object, value);
		}
	}
}
