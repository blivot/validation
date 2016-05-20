package com.virtyx.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtyx.constraint.AnyConstraint;
import com.virtyx.constraint.Constraint;
import com.virtyx.converter.Converter;
import com.virtyx.exception.ConvertException;
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

	protected List<Constraint<?>> constraints;

	protected Converter<T> converter;
	
	protected Validation<?> parent;
	
	public ValidationAny() {
		this(null);
	}
	
	public ValidationAny(Validation<?> parent) {
		this.parent = parent;
		this.constraints = new ArrayList<Constraint<?>>();
		required();
	}
	
	public Validation<?> getParent() {
		return parent;
	}
	
	@SuppressWarnings("unchecked")
	protected V getThis() {
		return (V)this;
	}
	
	@SuppressWarnings("unchecked")
	public List<ValidationError> validateValue(final String key, final Object value, final Container container) {
		List<ValidationError> errs = new ArrayList<ValidationError>();
		
		T toValidate = null;
		
		try {
			if (this.converter != null) {
				toValidate = this.converter.convert(value);  				
			} else {
				toValidate = (T)value;
			}
			log.debug("Converted: {}", toValidate);
		} catch (ConvertException | ClassCastException e) {
			errs.add(
					new ValidationError(
							key,
							value,
							String.format("Cannot convert '%s' to %s", key, e.getMessage())
					)
			);
			return errs;
		}
		
		for (Constraint<?> c : constraints) {
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
	
	public ValidationProperty property(String name) {
		return this.parent.property(name);
	}
	
	public V required() {
		AnyConstraint.Required req = new AnyConstraint.Required();
		if (!this.constraints.contains(req)) {
			this.constraints.add(req);	
		}
		return getThis();
	}
	
	public V optional() {
		constraints.remove(new AnyConstraint.Required());
		return getThis();
	}
	
	public V forbidden() {
		optional();
		this.constraints.add(new AnyConstraint.Forbidden());
		return getThis();
	}
	
	public V valid(Object value) {
		this.constraints.add(new AnyConstraint.Valid(value));
		return getThis();
	}
	
	public V only(Object value) {
		return valid(value);
	}
	
	public V equal(Object value) {
		return valid(value);
	}
}
