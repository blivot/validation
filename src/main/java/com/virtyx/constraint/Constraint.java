package com.virtyx.constraint;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtyx.exception.ValidationError;
import com.virtyx.exception.ValidationException;

@SuppressWarnings("rawtypes")
public abstract class Constraint <T> implements Comparable<Constraint> {
	
	final protected Logger log = LogManager.getLogger();
	
	protected Integer priority = -1;

	@SuppressWarnings("unchecked")
	public List<ValidationError> validate(
			String key,
			Object toValidate
			) throws ValidationException {
		log.debug("Type: " + type);
		log.debug("TO Validate: " + toValidate);
		if ( toValidate != null && !type.isAssignableFrom(toValidate.getClass()) ) {
			throw new ValidationException(
					new ValidationError(
							key,
							toValidate,
							String.format("Failed to convert the value of '%s' into a %s", key, type.getSimpleName())
							)
					);
		}

		T cast = (T)toValidate;
		boolean valid = valid(cast);

		if (!valid) {
			return createError(
					key,
					toValidate,
					getErrorMessage(key, cast)
					);
		}
		return null;
	}

	private Class<T> type;

	public Constraint(Class<T> type) {
		this.type = type;
	}

	protected abstract String getErrorMessage(String key, T object);

	public abstract boolean valid(T v);

	protected List<ValidationError> createErrors(ValidationError...errors) {
		List<ValidationError> errs = new ArrayList<ValidationError>();
		for (int i = 0; i < errors.length; ++i) {
			errs.add(errors[i]);
		}
		return errs;
	}

	protected List<ValidationError> createError(String key, Object value, String message) {
		return createErrors(new ValidationError(key, value, message));
	}
	
	public int compareTo(Constraint o) {
		return this.getClass().getName().compareTo(o.getClass().getName());
	}
	
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
