package com.virtyx.constraint;

import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class ListConstraint extends Constraint<List> {

	public ListConstraint() {
		super(List.class);
	}
	
	static public class Type extends ListConstraint {

		private Class value;

		private final static String ERROR = "The list %s contains objects that are not %s";

		public Type(Class value) {
			super();
			this.value = value;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean valid(List v) {
			boolean isValid = true;
			if (v == null) return isValid;
			
			for (Object o : v) {
				if (!value.isAssignableFrom(o.getClass())) {
					isValid = false;
					break;
				}
			}
			return isValid;
		}

		@Override
		protected String getErrorMessage(String key, List object) {
			return String.format(
					ERROR,
					key,
					value.getClass().getSimpleName()
			);
		}
	}

}
