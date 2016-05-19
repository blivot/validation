package com.virtyx.validation;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

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
	
	final protected Logger log = LogManager.getLogger();

	private Class<V> clazz;

	private Map<String, ValidationProperty> properties;

	private boolean allowUnknown = false;
	
	private PropertyDescriptor[] pda;

	private BeanWrapperImpl bw;

	public Validation() {
		this(null);
	}

	public Validation(Class<V> clazz) {
		this.properties = new HashMap<String, ValidationProperty>();
		this.setClazz(clazz);
	}

	public ValidationProperty property(String name) {
		log.debug("Adding Validation on Property: {}", name);
		if (properties.containsKey(name)) {
			log.debug("Already have it! {}", properties.get("name"));
			return properties.get("name");
		}
		log.debug("Making it instead");
		ValidationProperty vp = new ValidationProperty(this, name);
		addProperty(name, vp);
		log.debug("Returning!");
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
		log.debug("VAlidate {}", json);

		List<ValidationError> errs = new ArrayList<ValidationError>();
		if (json instanceof Map) {
			log.debug("It's a Map");
			Map<String, Object> coerced = (Map<String, Object>)json;
			Set<String> visitedKeys = new HashSet<String>();

			for (String key : properties.keySet()) {
				log.debug("VALIDATING: " + key);
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
			
			bw = new BeanWrapperImpl(clazz);
			pda = bw.getPropertyDescriptors();
			
			for (PropertyDescriptor pd : pda) {
				if (pd.getWriteMethod() == null) continue;
				
				log.debug("What: {}", pd.getWriteMethod());
				String getter = pd.getReadMethod().getName(); // getMySampleProperty
				String property = getter.replaceFirst("[a-z]+", ""); // MySampleProperty
				property = property.substring(0, 1).toLowerCase() + property.substring(1); // mySampleProperty
				log.debug("Adding Property: {}", property);
				property(property);
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
