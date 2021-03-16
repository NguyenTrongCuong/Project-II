package root.config.security_config;

import javax.sql.DataSource;

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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import root.api.client_sign_in.ClientAuthenticationFailureHandler;
import root.api.client_sign_in.ClientAuthenticationSuccessHandler;
import root.api.client_sign_in.ClientDetailsService;
import root.config.access_denied_handler_config.ClientAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@Order(2)
public class ClientSecurityConfig<S extends Session> extends WebSecurityConfigurerAdapter {	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private FindByIndexNameSessionRepository<S> sessionRepository;
	
	@Bean
	public PasswordEncoder getClientPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public AuthenticationSuccessHandler getClientAuthenticationSuccessHandler() {
		return new ClientAuthenticationSuccessHandler();
	}
	
	@Bean
	public AuthenticationFailureHandler getClientAuthenticationFailureHandler() {
		return new ClientAuthenticationFailureHandler();
	}
	
	@Bean
	public AccessDeniedHandler getClientAccessDeniedHandler() {
		return new ClientAccessDeniedHandler();
	}
	
	@Bean
	public UserDetailsService getClientDetailsService() {
		return new ClientDetailsService();
	}
	
	@Bean
	public PersistentTokenRepository getPersistentTokenRepository() {
		JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
		repository.setDataSource(dataSource);
		return repository;
	}
	
	@Bean
	public DaoAuthenticationProvider getClientDaoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(getClientDetailsService());
		provider.setPasswordEncoder(getClientPasswordEncoder());
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getClientDaoAuthenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**","/webjars/**", "/css/**", "/js/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
			.requestMatchers()
				.antMatchers("/**")
				.and()
			.authorizeRequests()
				.antMatchers("/", "/sign-in", "/do-sign-in", "/sign-up", "/do-sign-up", "/invalid-session").permitAll()
				.antMatchers("/**").hasRole("USER")
				.and()
			.formLogin()
				.loginPage("/sign-in")
				.loginProcessingUrl("/do-sign-in")
				.successHandler(getClientAuthenticationSuccessHandler())
				.failureHandler(getClientAuthenticationFailureHandler())
				.and()
			.exceptionHandling()
				.accessDeniedHandler(getClientAccessDeniedHandler())
				.and()
			.sessionManagement()
				.maximumSessions(1)
				.sessionRegistry(ClientSessionRegistry())
				.expiredUrl("/invalid-session")
				.and()
				.invalidSessionUrl("/invalid-session")
				.and()
			.rememberMe()
				.authenticationSuccessHandler(getClientAuthenticationSuccessHandler())
				.tokenRepository(getPersistentTokenRepository())
				.userDetailsService(getClientDetailsService())
				.and()
			.logout()
				.invalidateHttpSession(true)
				.deleteCookies("remember-me", "SESSION")
				.clearAuthentication(true)
				.logoutSuccessUrl("/")
				.logoutUrl("/do-sign-out");		
	}
	
	@Bean
	public SpringSessionBackedSessionRegistry<S> ClientSessionRegistry() {
		return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
	}

}













































