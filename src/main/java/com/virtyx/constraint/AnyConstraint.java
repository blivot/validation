package com.virtyx.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
			this.priority = 10;
		}

		@Override
		public boolean valid(Object v) {
			return v != null;
		}

		@Override
		protected String getErrorMessage(String key, Object object) {
			return String.format(ERROR, key);
		}
	}
	
	static public class Forbidden extends AnyConstraint {

		private final static String ERROR = "'%s' is forbidden";

		public Forbidden() {
			super(Object.class);
			this.priority = 9;
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

		private List<Object> validValues;
			
		public Valid(Object value) {
			super(Object.class);
			this.validValues = new ArrayList<Object>();
			
			if (Object[].class.isAssignableFrom(value.getClass())) {
				Object[] casted = (Object[])value;
				for (Object o : casted) {
					this.addValidValue(o);
				}
			} else {
				this.addValidValue(value);
			}
		}
		
		public void addValidValue(Object o) {
			this.validValues.add(o);
		}

		@Override
		public boolean valid(Object v) {
			return validValues.contains(v);
		}

		@Override
		protected String getErrorMessage(String key, Object object) {
			return String.format(ERROR, object, validValues);
		}
	}
}
