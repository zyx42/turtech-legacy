package xyz.zyv42.turtech_legacy.store.config;

import xyz.zyv42.turtech_legacy.store.persistence.service.impl.UserSecurityService;
import xyz.zyv42.turtech_legacy.store.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserSecurityService userSecurityService;

	@Autowired
	public SecurityConfig(UserSecurityService userSecurityService) {
		this.userSecurityService = userSecurityService;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}

	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}

	// paths available for everyone
	private static final String[] PUBLIC_MATCHERS = {
			"/",
			"/newUser",
			"/newUserAction",
			"/sendMessage",
			"/forgetPassword",
			"/login",
			"/loginAction",
			"/signIn",
			"/signUp",
			"/products/**",
			"/productDetails/**",
			"/about",
			"/faq",
			"/contact",
			"/searchByCategory",
			"/searchProduct"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.authorizeRequests()
				.antMatchers(PUBLIC_MATCHERS).permitAll()
				.antMatchers("/shoppingCart/**", "/myProfile", "/leaveReview").hasRole("USER")
					.anyRequest().authenticated()
			.and()
				.csrf().disable().cors().disable()
				.formLogin().failureUrl("/signIn?error")
				.defaultSuccessUrl("/")
				.loginProcessingUrl("/loginAction")
				.loginPage("/signIn")
					.permitAll()
			.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/signIn?logout")
					.deleteCookies("remember-me")
					.permitAll()
			.and()
				.rememberMe()
					.key("unique-and-secret")
					.rememberMeCookieName("remember-me")
					.tokenValiditySeconds(24 * 60 * 60);
	}

	@Override
    public void configure(WebSecurity web) throws Exception {

		web.ignoring()
				.antMatchers("/css/**")
				.antMatchers("/images/**")
				.antMatchers("/js/**")
				.antMatchers("/webjars/**")
				.antMatchers("/favicon.png");
    	}
}
