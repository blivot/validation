package com.virtyx.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	protected Collection<Constraint<?>> constraints;

	protected Converter<T> converter;
	
	protected Validation<?> parent;
	
	public ValidationAny() {
		this(null);
	}
	
	public ValidationAny(Validation<?> parent) {
		this.parent = parent;
		this.constraints = new ArrayList<Constraint<?>>();
	}
	
	public Validation<?> getParent() {
		return parent;
	}
	
	@SuppressWarnings("unchecked")
	protected V getThis() {
		return (V)this;
	}
	
	@SuppressWarnings("unchecked")
	public List<ValidationError> validateValue(final String key, final Object value) {
		List<ValidationError> errs = new ArrayList<ValidationError>();
		
		T toValidate = null;
		
		try {
			if (this.converter != null) {
				toValidate = this.converter.convert(value);  				
			} else {
				toValidate = (T)value;
			}
		} catch (ConvertException | ClassCastException e) {
			errs.add(
					new ValidationError(
							key,
							value,
							e.getMessage()
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
		return errs;
	}
	
	public ValidationProperty property(String name) {
		return this.parent.property(name);
	}
	
	public V required() {
		this.constraints.add(new AnyConstraint.Required());
		return getThis();
	}
	
	public V optional() {
		return getThis();
	}
	
	public V forbidden() {
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
