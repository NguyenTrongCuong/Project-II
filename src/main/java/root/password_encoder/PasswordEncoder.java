package root.password_encoder;

public interface PasswordEncoder {
	
	public String hash(String password, int salt);

}
