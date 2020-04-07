package xyz.zyv42.turtech_legacy.store.utility;

import xyz.zyv42.turtech_legacy.store.persistence.domain.Order;
import xyz.zyv42.turtech_legacy.store.persistence.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import java.util.Locale;

/**
 * Class {@code MailConstructor} is a utility class used to construct and send emails,
 * like password recovery or order confirmation emails to users of the website.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Component
public class MailConstructor {

	private final Environment env;

	private final TemplateEngine templateEngine;

	@Autowired
	public MailConstructor(Environment env, TemplateEngine templateEngine) {
		this.env = env;
		this.templateEngine = templateEngine;
	}

	/**
	 * Constructs a password recovery email with a generated
	 * reset token in it and returns it to a class that is going
	 * to send it to a user.
	 * 
	 * @param contextPath - a path used to generate a url for password recovery
	 * @param locale - a locale of the user requested password recovery
	 * @param token - a generated token for password recovery
	 * @param user - a user that requested password recovery
	 * @param password - a newly generated random password
	 * @return a fully constructed password recovery email
	 */
	public SimpleMailMessage constructResetTokenEmail(String contextPath,
			Locale locale,
			String token,
			User user,
			String password) {


		String url = contextPath + "/newUser?token=" + token;
		String message = "\nPlease click on this link to verify your email and edit your personal information. Your password is: \n" + password;
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("TurTech - New User");
		email.setText(url + message);
		email.setFrom(env.getProperty("support.email"));

		return email;
	}

	/**
	 * Constructs an order confirmation email and returns it to a class
	 * that is going to send it to the user.
	 * 
	 * @param user - a user that placed an order
	 * @param order - an order placed by the user
	 * @param locale - a locale of the user
	 * @return a fully constructed order confirmation email
	 */
	public MimeMessagePreparator constructOrderConfirmationEmail (User user, Order order, Locale locale) {

		Context context = new Context();
		context.setVariable("order", order);
		context.setVariable("user", user);
		context.setVariable("cartItemList", order.getCartItemList());
		String text = templateEngine.process("orderConfirmationEmailTemplate", context);

		return mimeMessage -> {
			MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
			email.setTo(user.getEmail());
			email.setSubject("Order Confirmation - " + order.getId());
			email.setText(text, true);
			email.setFrom(new InternetAddress("turtech@gmail.com"));
		};
	}
}