package root.annotation;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidation implements ConstraintValidator<ImageFile, MultipartFile> {

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		try {
			return ImageIO.read(value.getInputStream()) == null ? false : true;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
