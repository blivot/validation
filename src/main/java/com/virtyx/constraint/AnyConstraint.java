package com.virtyx.constraint;

import java.util.ArrayList;
import java.util.List;

import com.virtyx.exception.ValidationError;
import com.virtyx.exception.ValidationException;

public abstract class AnyConstraint extends Constraint {
	
	static public class Required extends AnyConstraint {
		
		private final static String ERROR = "'%s' is required";
		
		public List<ValidationError> validate(String key, Object toValidate) throws ValidationException {
			System.out.println("MAKE SURE IT IS HERE: " + toValidate);
			if (toValidate != null) return null;

			List<ValidationError> errs = new ArrayList<ValidationError>();
			
			errs.add(
					new ValidationError(
							key,
							toValidate,
							String.format(ERROR, key)
							)
					);
			return errs;
		}
	}
}
