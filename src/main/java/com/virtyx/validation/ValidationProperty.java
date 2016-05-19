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
	
	public ValidationEnum enumm(Class<?> enumm) {
		this.type = new ValidationEnum(this.parent, enumm);
		return (ValidationEnum) this.type;
	}
	
	public List<ValidationError> validate(String key, Object value) {
		log.debug("Type: {}", this.type);
		return this.type.validateValue(key, value);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
