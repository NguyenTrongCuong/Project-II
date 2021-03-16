package root.password_encoder;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptPasswordEncoder implements PasswordEncoder {

	@Override
	public String hash(String password, int salt) {
		return BCrypt.hashpw(password, BCrypt.gensalt(salt));
	}

}
