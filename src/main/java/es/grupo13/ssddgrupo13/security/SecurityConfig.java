package es.grupo13.ssddgrupo13.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import es.grupo13.ssddgrupo13.security.jwt.JwtRequestFilter;
import es.grupo13.ssddgrupo13.security.jwt.UnauthorizedHandlerJwt;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	RepositoryUserDetailsService userDetailsService;

	@Autowired
	private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	/*
	 * @Bean
	 * 
	 * @Order(1)
	 * public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception
	 * {
	 * 
	 * http.authenticationProvider(authenticationProvider());
	 * 
	 * http
	 * .securityMatcher("/api/**")
	 * .exceptionHandling(handling ->
	 * handling.authenticationEntryPoint(unauthorizedHandlerJwt));
	 * 
	 * http
	 * .authorizeHttpRequests(authorize -> authorize
	 * // PRIVATE ENDPOINTS
	 * .requestMatchers(HttpMethod.POST,"/api/books/").hasRole("USER")
	 * .requestMatchers(HttpMethod.PUT,"/api/books/**").hasRole("USER")
	 * .requestMatchers(HttpMethod.DELETE,"/api/books/**").hasRole("ADMIN")
	 * // PUBLIC ENDPOINTS
	 * .anyRequest().permitAll()
	 * );
	 * 
	 * // Disable Form login Authentication
	 * http.formLogin(formLogin -> formLogin.disable());
	 * 
	 * // Disable CSRF protection (it is difficult to implement in REST APIs)
	 * http.csrf(csrf -> csrf.disable());
	 * 
	 * // Disable Basic Authentication
	 * http.httpBasic(httpBasic -> httpBasic.disable());
	 * 
	 * // Stateless session
	 * http.sessionManagement(management ->
	 * management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	 * 
	 * // Add JWT Token filter
	 * http.addFilterBefore(jwtRequestFilter,
	 * UsernamePasswordAuthenticationFilter.class);
	 * 
	 * return http.build();
	 * }
	 */

	// Web Security Configuration

	// When using both web and API we will implement apiFilterChain and
	// webFilterChain,
	// and we will use the securityMatcher method to separate the endpoints
	@Bean
	@Order(1)
	public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());

		http
				.authorizeHttpRequests(authorize -> authorize
						// PUBLIC PAGES
						.requestMatchers("/logoutSuccess").permitAll()
						.requestMatchers("addNewEvent").hasRole("ADMIN")
						.requestMatchers("/admin/deleteEvent/**").hasRole("ADMIN")
						.requestMatchers("/admin/deleteComment/**").hasRole("ADMIN")
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/", "/css/**", "/event-image/**", "/img/**", "/js/**", "/videos/**")
						.permitAll()
						.requestMatchers("/authenticate", "/favicon/**").permitAll()
						.requestMatchers("/login", "/loginError").permitAll()
						.requestMatchers("/clubbing/**", "/events/**", "/festivals/**", "/sign-in/**").permitAll()
						.requestMatchers("/concerts/**").permitAll()
						.requestMatchers("/logout/**").permitAll()
						.requestMatchers("/register/**").permitAll()
						.requestMatchers("/profileTest").permitAll()
						.requestMatchers("/comment_in").permitAll()
						.requestMatchers("/comment_out/**").permitAll()
						.requestMatchers("/contact").permitAll()
						.requestMatchers("/ticket/**").permitAll()

						// PRIVATE PAGES

						.requestMatchers("/event/**").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/newEvent/**").hasAnyRole("ADMIN")
						.requestMatchers("/data/**").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/profilePage/**").authenticated()
						.requestMatchers("/buyTicket/**").authenticated()
						.requestMatchers("/editprofilepage").authenticated()
						.requestMatchers("/edit-profile").authenticated()
				// We have to make an admin page (/admin/**)
				)
				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.failureUrl("/loginError")
						.defaultSuccessUrl("/", true)
						.permitAll()

				// We have to make an only login page
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/logoutSuccess")
						.permitAll()
				)
				.csrf(csrf -> csrf
   						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				);

		return http.build();
	}
}
