package com.virtyx.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.virtyx.constraint.Constraint;
import com.virtyx.converter.Converter;
import com.virtyx.exception.ConvertException;
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
	
	public Validation() {
		this(null);
	}
	
	public Validation(Class<V> clazz) {
		this.clazz = clazz;
		this.properties = new HashMap<String, ValidationProperty>();
	}
	
	public ValidationProperty property(String name) {
		ValidationProperty vp = new ValidationProperty(this, name);
		addProperty(name, vp);
		return vp;
	}

	protected void addProperty(String name, ValidationProperty vp) {
		this.properties.put(name, vp);
	}

	/**
	 * You can probably set a few different modes.
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
		} else if (json instanceof Collection) {
			//Validation a collection
		}
		
		return errs;
	}

	public Class<V> getClazz() {
		return clazz;
	}

	public void setClazz(Class<V> clazz) {
		this.clazz = clazz;
	}
}
