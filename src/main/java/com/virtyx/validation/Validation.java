package com.virtyx.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.virtyx.constraint.Constraint;
import com.virtyx.exception.ValidationError;
import com.virtyx.exception.ValidationException;

/**
 * 
 * Usage:
 * 
 * validation = new Validation(Animal.class)
 * validation
 * 	.property("id").forbidden()
 * 	.property("age").integer().min(0).max(100).optional()
 * 	.property("type").enum(AnimalType.class)
 * 		.fromInt(AFunctionalInterface)
 * 		.fromString(AFunctionalInterface)
 * 		.required()
 *  .property("aList").collection().notEmpty()
 *  .property("friend").validate(AnotherSchema)
 *  .respectAnnotations() //Repects JavaX validation annotations
 *  
 * 
 * @author ethanmick
 *
 * @param <V>
 */
public class Validation <V> {
	
	private Class<V> clazz;
	
	private Map<String, ValidationProperty> properties;
	
	protected Collection<Constraint> constraints;
	
	protected Validation<V> parent;
	
	public Validation() {
		this(null);
	}
	
	public Validation(Class<V> clazz) {
		this.clazz = clazz;
		this.properties = new HashMap<String, ValidationProperty>();
		this.constraints = new ArrayList<Constraint>();
		this.parent = null;
	}
	
	public ValidationProperty property(String name) {
		System.out.println("THIS: " + this);
		Validation<V> p = getParent();
		System.out.println("Parent: " + p);
		if (p == null) p = this;
		System.out.println("Parent 2: " + p);
		
		ValidationProperty vp = new ValidationProperty(p, name);
		p.addProperty(name, vp);
		return vp;
	}

	protected void addProperty(String name, ValidationProperty vp) {
		this.properties.put(name, vp);
	}

	/**
	 * You can probably set a few different modes.
	 * 
	 * For now, if it comes up to an error, it'll throw an exception
	 * 
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<ValidationError> validate(Object json) {
		
		List<ValidationError> errs = new ArrayList<ValidationError>();
		if (json instanceof Map) {
			Map<String, Object> coerced = (Map<String, Object>)json;
			
			for (String key : properties.keySet()) {
				System.out.println("VALIDATING: " + key);
				ValidationProperty prop = properties.get(key);
				errs.addAll(prop.validate(key, coerced.get(key)));
			}
		}
		
		return errs;
	}
	
	public List<ValidationError> validateValue(String key, Object value) {
		List<ValidationError> errs = new ArrayList<ValidationError>();
		
		for (Constraint c : constraints) {
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
		return errs;
	}

	public Validation<V> getParent() {
		return parent;
	}
	
	

}
