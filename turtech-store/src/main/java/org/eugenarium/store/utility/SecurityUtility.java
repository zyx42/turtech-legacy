package org.eugenarium.store.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Class (@code SecurityUtility) is a utility class that
 * provides a password encoder and can generate random salted
 * passwords on a request.
 * 
 * @author Yevhenii Zhyliaev
 */
@Component
public class SecurityUtility {

	private static final String SALT = "salt";

	/**
	 * Provides a password encoder for the requesting class.
	 */
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}

	/**
	 * Provides a random salted password for the requesting class.
	 */
	@Bean
	public static String randomPassword() {

		// salt should be securily hidden
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();

		// randomising salt
		while (salt.length() < 18) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		return salt.toString();
	}
}