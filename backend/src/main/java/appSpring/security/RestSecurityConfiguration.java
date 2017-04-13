package appSpring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import appSpring.repository.UserRepositoryAuthenticationProvider;

@Configuration
@Order(1)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserRepositoryAuthenticationProvider userRepoAuthProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.antMatcher("/api/**");

		// URLs that need authentication to access to it
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/resources", "/api/resources/{id}", "/api/genres",
				"/api/genres/{id}", "/api/resourcetypes", "/api/resourcetypes/{id}").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/{id}", "/api/loans", "/api/loans/{id}",
				"/api/fines", "/api/fines/{id}", "/api/resourcecopies", "/api/resourcecopies/{id}").hasAnyRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/loans").hasAnyRole("USER", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/resources", "/api/genres", "/api/resourcetypes",
				"/api/resourcecopies").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/**/**").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/users/{id}").hasAnyRole("USER", "ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/resources/{id}", "/api/loans/{id}",
				"/api/genres/{id}", "/api/resourcetypes/{id}", "/api/resourcecopies/{id}").hasAnyRole("ADMIN");

		// USE HTTP BASIC AUTHENTICATION
		http.httpBasic();

		// Do not redirect when logout
		http.logout().logoutSuccessHandler((rq, rs, a) -> {
		});

		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(userRepoAuthProvider);
	}
}
