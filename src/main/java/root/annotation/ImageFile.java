package root.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy=ImageFileValidation.class)
public @interface ImageFile {
	
	String message() default "Incorrect file type";
	
	Class<?>[] groups() default {};
	
	public abstract Class<? extends Payload>[] payload() default {};

}
