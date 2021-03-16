package root.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import root.entity_service.ClientCredentialsService;

public class UniqueClientEmailValidation implements ConstraintValidator<UniqueClientEmail, String> {
	@Autowired
	private ClientCredentialsService clientCredentialsServices;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return this.clientCredentialsServices.isEmailUnique(value);
	}

}
