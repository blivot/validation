package com.virtyx.constraint;

import java.util.ArrayList;
import java.util.List;

import com.virtyx.exception.ValidationError;
import com.virtyx.exception.ValidationException;

public abstract class StringConstraint extends Constraint {

	protected String attemptCast(String key, Object value) {
		if (value == null) return null;
		if (value instanceof String) return (String)value;

		if (value instanceof Object[]) {
			Object[] coerced = (Object[])value;
			if (coerced.length == 0) return null;
			if (coerced.length == 1) attemptCast(key, coerced[0]);
		}

		if (value instanceof Integer || value instanceof Boolean) {
			return value.toString();
		}

		if (value.getClass().isEnum()) {
			return value.toString();
		}

		return null; // I dunno
	}

	protected String attemptCastOrThrow(String key, Object value) throws ValidationException {
		String returned = attemptCast(key, value);
		if (returned == null && value != null) {
			throw new ValidationException(
					new ValidationError(
					key,
					value,
					"Failed to convert the input into a String"
					)
			);
		}
		return returned;
	}

	static public class Min extends StringConstraint {

		private Integer value;

		private final static String ERROR = "The string '%s' needs to be at least %d characters long";

		public Min(Integer value) {
			this.value = value;
		}

		public List<ValidationError> validate(String key, Object toValidate) throws ValidationException {
			String coerced = attemptCastOrThrow(key, toValidate);
			if (coerced == null || coerced.length() >= value) return null;

			List<ValidationError> errs = new ArrayList<ValidationError>();
			
			errs.add(
					new ValidationError(
							key,
							toValidate,
							String.format(ERROR, coerced, value)
							)
					);
			return errs;
		}
	}

}
