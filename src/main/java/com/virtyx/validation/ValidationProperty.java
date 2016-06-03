package com.virtyx.validation;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtyx.exception.ValidationError;

public class ValidationProperty {
	
	final protected Logger log = LogManager.getLogger();
	
	private Validation<?> parent;
	
	private String property;
	
	private ValidationAny<?, ?> type;
	
	@SuppressWarnings("rawtypes")
	public ValidationProperty(Validation<?> parent, String property) {
		this.parent = parent; 
		this.property = property;
		this.type = new ValidationAny().required();
	}
	
	public ValidationString string() {
		this.type = new ValidationString(this.parent);
		return (ValidationString) this.type;
	}
	
	public ValidationNumber number() {
		this.type = new ValidationNumber(this.parent);
		return (ValidationNumber) this.type;
	}
	
	public ValidationBoolean bool() {
		this.type = new ValidationBoolean(this.parent);
		return (ValidationBoolean) this.type;
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Enum<E>> ValidationEnum<E> enumm(Class<E> enumm) {
		this.type = new ValidationEnum<E>(this.parent, enumm);
		return (ValidationEnum<E>) this.type;
	}
	
	@SuppressWarnings("unchecked")
	public <E> ValidationList<E> list(Class<E> clazz) {
		this.type = new ValidationList<E>(this.parent, clazz);
		return (ValidationList<E>) this.type;
	}
	
	public ValidationMap map() {
		this.type = new ValidationMap(this.parent);
		return (ValidationMap) this.type;
	}
	
	public List<ValidationError> validate(String key, Object value, Container container) {
		log.debug("Type: {}", this.type);
		return this.type.validateValue(key, value, container);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
