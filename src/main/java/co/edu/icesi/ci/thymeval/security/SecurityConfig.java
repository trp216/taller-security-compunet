package co.edu.icesi.ci.thymeval.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	//	@Autowired
	//	private MyCustomUserDetailsService userDetailsService;

	//	@Override
	//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//		auth.authenticationProvider(authenticationProvider());
	//	}

	//	@Bean
	//	public DaoAuthenticationProvider authenticationProvider() {
	//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	//		authProvider.setUserDetailsService(userDetailsService);
	//		authProvider.setPasswordEncoder(encoder());
	//		return authProvider;
	//	}
	//
	//	@Bean
	//	public PasswordEncoder encoder() {
	//		return new BCryptPasswordEncoder(11);
	//	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		//		//httpSecurity.authorizeRequests().antMatchers("/secure/**").authenticated().anyRequest().permitAll().and().httpBasic().and().logout()
		//		httpSecurity.authorizeRequests().antMatchers("/**").authenticated().anyRequest().permitAll().
		//		and().formLogin().loginPage("/login")
		//		
		//		//.and().logout()
		//			//.invalidateHttpSession(true).clearAuthentication(true)
		//				//.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
		//				.permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		httpSecurity.formLogin().loginPage("/login").permitAll().and().authorizeRequests()
		
		.antMatchers("/users/**").hasRole("admin")
		.antMatchers("/apps/**").hasRole("doctor")
		.anyRequest().authenticated().and().httpBasic().and().logout().invalidateHttpSession(true)
		.clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout").
		permitAll().and().exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler);
	}
}