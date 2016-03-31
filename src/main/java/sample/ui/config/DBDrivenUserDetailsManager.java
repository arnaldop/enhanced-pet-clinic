package sample.ui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import lombok.extern.slf4j.Slf4j;
import sample.ui.model.User;
import sample.ui.repository.UserRepository;

@Slf4j
public class DBDrivenUserDetailsManager implements UserDetailsManager {

	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " not found.");
		}

		return user;
	}

	@Override
	public void createUser(UserDetails user) {
		userRepository.save((User) user);
	}

	@Override
	public void updateUser(UserDetails user) {
		userRepository.save((User) user);
	}

	/**
	 * Users are not deleted, just disabled.
	 */
	@Override
	public void deleteUser(String username) {
		User user = userRepository.findByUsername(username);
		user.setEnabled(false);
		userRepository.save(user);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

		if (currentUser == null) {
			// This would indicate bad coding somewhere
			throw new AccessDeniedException(
					"Can't change password as no Authentication object found in context for current user.");
		}

		String username = currentUser.getName();

		log.debug("Changing password for user '" + username + "'");

		// If an authentication manager has been set, re-authenticate the user
		// with the supplied password.
		if (authenticationManager != null) {
			log.debug("Reauthenticating user '" + username + "' for password change request.");

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			log.debug("No authentication manager set. Password won't be re-checked.");
		}

		User changedUser = userRepository.findByUsername(username);

		if (changedUser == null) {
			throw new IllegalStateException("Current user doesn't exist in database.");
		}

		changedUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));

		userRepository.save(changedUser);
	}

	@Override
	public boolean userExists(String username) {
		return userRepository.findByUsername(username) != null;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
}
