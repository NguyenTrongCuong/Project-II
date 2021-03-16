package root.config.security_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import root.api.employee_sign_in.EmployeeAuthenticationFailureHandler;
import root.api.employee_sign_in.EmployeeAuthenticationSuccessHandler;
import root.api.employee_sign_in.EmployeeDetailsService;
import root.config.access_denied_handler_config.EmployeeAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@Order(1)
public class EmployeeSecurityConfig<S extends Session> extends WebSecurityConfigurerAdapter {
	@Autowired
	private FindByIndexNameSessionRepository<S> sessionRegistry;
	
	@Bean
	public UserDetailsService getEmployeeDetailsService() {
		return new EmployeeDetailsService();
	}
	
	@Bean
	public PasswordEncoder getEmployeePasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public AuthenticationSuccessHandler getEmployeeAuthenticationSuccessHandler() {
		return new EmployeeAuthenticationSuccessHandler();
	}
	
	@Bean
	public AuthenticationFailureHandler getEmployeeAuthenticationFailureHandler() {
		return new EmployeeAuthenticationFailureHandler();
	}
	
	@Bean
	public AccessDeniedHandler getEmployeeAccessDeniedHandler() {
		return new EmployeeAccessDeniedHandler();
	}
	
	@Bean
	public DaoAuthenticationProvider getDaoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(getEmployeeDetailsService());
		provider.setPasswordEncoder(getEmployeePasswordEncoder());
		return provider;
	}
	
	@Bean
	public SpringSessionBackedSessionRegistry<S> EmployeeSessionRegistry() {
		return new SpringSessionBackedSessionRegistry<>(this.sessionRegistry);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**","/webjars/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getDaoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
			.requestMatchers()
				.antMatchers("/employee/**")
				.and()
			.authorizeRequests()
				.antMatchers("/employee/sign-in", "/employee/do-sign-in", "/employee/invalid-session").permitAll()
				.antMatchers("/employee/**").hasAnyRole("ADMIN", "STAFF")
				.and()
			.formLogin()
				.loginPage("/employee/sign-in")
				.loginProcessingUrl("/employee/do-sign-in")
				.successHandler(getEmployeeAuthenticationSuccessHandler())
				.failureHandler(getEmployeeAuthenticationFailureHandler())
				.and()
			.sessionManagement()
				.maximumSessions(1)
				.sessionRegistry(EmployeeSessionRegistry())
				.expiredUrl("/employee/invalid-session")
				.and()
				.invalidSessionUrl("/employee/invalid-session")
				.and()
			.exceptionHandling()
				.accessDeniedHandler(getEmployeeAccessDeniedHandler())
				.and()
			.logout()
				.invalidateHttpSession(true)
				.deleteCookies("SESSION", "remember-me")
				.clearAuthentication(true)
				.logoutSuccessUrl("/employee/sign-in")
				.logoutUrl("/employee/do-sign-out");
			
	}
	
	

}








































