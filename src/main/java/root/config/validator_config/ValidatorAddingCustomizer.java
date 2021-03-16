package root.config.validator_config;

import java.util.Map;

import javax.validation.Validator;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
public class ValidatorAddingCustomizer implements HibernatePropertiesCustomizer {

    private final ObjectProvider<javax.validation.Validator> provider;

    public ValidatorAddingCustomizer(ObjectProvider<javax.validation.Validator> provider) {
        this.provider = provider;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        Validator validator = provider.getIfUnique();

        if (validator != null) {
            hibernateProperties.put("javax.persistence.validation.factory", validator);
        }
    }
}
