package tw.team1.member.model;

public class UsernameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UsernameNotFoundException() {
		
	}
	
	public UsernameNotFoundException(String message) {
		super(message);
	}
}
