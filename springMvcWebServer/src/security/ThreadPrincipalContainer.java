package security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import objects.User;

@SuppressWarnings("serial")
public class ThreadPrincipalContainer  extends org.springframework.security.core.userdetails.User{

	private User user;
	
	public ThreadPrincipalContainer(Collection<? extends GrantedAuthority> authorities, User user) {
		super(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
