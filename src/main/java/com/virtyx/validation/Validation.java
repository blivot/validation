package com.virtyx.validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.virtyx.exception.ValidationError;

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

	private boolean allowUnknown = false;

	public Validation() {
		this(null);
	}

	public Validation(Class<V> clazz) {
		this.properties = new HashMap<String, ValidationProperty>();
		this.setClazz(clazz);
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
	 * By default, any keys that are not 
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<ValidationError> validate(Object json) {

		List<ValidationError> errs = new ArrayList<ValidationError>();
		if (json instanceof Map) {
			Map<String, Object> coerced = (Map<String, Object>)json;
			Set<String> visitedKeys = new HashSet<String>();

			for (String key : properties.keySet()) {
				System.out.println("VALIDATING: " + key);
				visitedKeys.add(key);
				ValidationProperty prop = properties.get(key);
				errs.addAll(prop.validate(key, coerced.get(key)));
			}

			if (!allowUnknown) {
				Set<String> unusedFields = new HashSet<String>(coerced.keySet());
				unusedFields.removeAll(visitedKeys);

				for (String s : unusedFields) {
					errs.add(
							new ValidationError(
									s,
									null,
									String.format("Key '%s' was sent but is unnecessary", s)
									)
							);
				}
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
		if (this.clazz != null) {
			for (Field f : this.clazz.getClass().getDeclaredFields()) {

			}
		}
	}

	public boolean isAllowUnknown() {
		return allowUnknown;
	}

	public void setAllowUnknown(boolean allowUnknown) {
		this.allowUnknown = allowUnknown;
	}
}
