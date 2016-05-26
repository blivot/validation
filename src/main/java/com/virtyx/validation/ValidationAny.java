package com.virtyx.validation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtyx.constraint.AnyConstraint;
import com.virtyx.constraint.Constraint;
import com.virtyx.converter.Converter;
import com.virtyx.exception.ValidationError;
import com.virtyx.exception.ValidationException;

/**
 * Parent class.
 * @author ethanmick
 *
 */
@SuppressWarnings("rawtypes")
public class ValidationAny <T, V extends ValidationAny> {

	final protected Logger log = LogManager.getLogger();

	protected TreeSet<Constraint<?>> hpConstraints;
	
	protected TreeSet<Constraint<?>> lpConstraints;

	protected Converter<T> converter;

	protected Validation<?> parent;

	protected T defaultValue = null;

	public ValidationAny() {
		this(null);
	}

	public ValidationAny(Validation<?> parent) {
		this.parent = parent;
		this.hpConstraints = new TreeSet<Constraint<?>>(new ConstraintComparator());
		this.lpConstraints = new TreeSet<Constraint<?>>(new ConstraintComparator());
		required();
	}

	public Validation<?> getParent() {
		return parent;
	}

	@SuppressWarnings("unchecked")
	protected V getThis() {
		return (V)this;
	}
	
	protected V addConstraint(Constraint c) {
		if (c.getPriority() > 0) {
			this.hpConstraints.add(c);
		} else {
			this.lpConstraints.add(c);
		}
		return getThis();
	}
	
	protected V removeConstraint(Class clazz) {
		Constraint c = constraintForClass(clazz);
		this.hpConstraints.remove(c);
		this.lpConstraints.remove(c);
		return getThis();
	}
	
	protected Constraint constraintForClass(Class clazz) {
		Constraint found = null;
		for (Constraint<?> c : this.lpConstraints) {
		    if (c.getClass().equals(clazz)) {
		    	found = c;
		    	break;
		    }
		}
		for (Constraint<?> c : this.hpConstraints) {
		    if (c.getClass().equals(clazz)) {
		    	found = c;
		    	break;
		    }
		}
		return found;
	}

	public List<ValidationError> validateValue(final String key, final Object value, final Container container) {
		List<ValidationError> errs = new ArrayList<ValidationError>();

		T toValidate = null;
		/**
		 * --> Constraints with Priority greater than 0 run
		 * --> Convert the Object to it's type
		 * --> Constraints with Priority less than 0 run
		 */
		for (Constraint<?> c : hpConstraints) {
			log.debug("Validate (hp) Value Constraint: {}", c);
			List<ValidationError> e = new ArrayList<ValidationError>();

			try {
				e = c.validate(key, value);
			} catch (ValidationException ex) {
				e.add(ex.getError());
			}
			if (e != null && e.size() > 0) {
				errs.addAll(e);
			}
		}
		
		// Bail early if you failed a precheck
		if (errs.size() > 0) return errs;
		
		try {
			log.debug("Trying to Convert");
			toValidate = convert(value);
		} catch (Exception error) {
			errs.add(
					new ValidationError(
							key,
							value,
							String.format("Cannot convert '%s' to %s", key, error.getMessage())
							)
					);
			log.debug("Failed to convert, returing.");
			return errs;
		}
		log.debug("Done Conversion");
		
		for (Constraint<?> c : lpConstraints) {
			log.debug("Validate (lp) Value Constraint: {}", c);
			List<ValidationError> e = new ArrayList<ValidationError>();

			try {
				e = c.validate(key, toValidate);
			} catch (ValidationException ex) {
				e.add(ex.getError());
			}
			if (e != null && e.size() > 0) {
				errs.addAll(e);
			}
		}
	
		if (errs.size() == 0) container.object = toValidate;

		return errs;
	}

	@SuppressWarnings("unchecked")
	protected T convert(Object value) throws Exception {
		T toValidate = null;
		if (this.converter != null) {
			toValidate = this.converter.convert(value);  				
		} else if (value == null && defaultValue != null) {
			toValidate = defaultValue;
		} else {
			toValidate = (T)value;
		}
		return toValidate;
	}

	public ValidationProperty property(String name) {
		return this.parent.property(name);
	}
	
	
	/*
	 * 
	 */
	public V required() {
		this.defaultValue = null;
		return addConstraint(new AnyConstraint.Required());
	}

	public V optional() {
		return removeConstraint(AnyConstraint.Required.class);
	}

	public V forbidden() {
		optional();
		return addConstraint(new AnyConstraint.Forbidden());
	}

	public V valid(Object value) {
		AnyConstraint.Valid valid = (AnyConstraint.Valid)this.constraintForClass(AnyConstraint.Valid.class);
		if (valid != null) {
			valid.addValidValue(value);
		} else {
			addConstraint(new AnyConstraint.Valid(value));
		}
		return getThis();
	}

	public V only(Object value) {
		return valid(value);
	}

	public V equal(Object value) {
		return valid(value);
	}

	public V setDefault(T defaultValue) {
		this.defaultValue = defaultValue;
		return getThis();
	}

	private static final class ConstraintComparator implements Comparator<Constraint> {
		@Override
		public int compare(Constraint o1, Constraint o2) {
			return o1.compareTo(o2);
		}
	}

}
