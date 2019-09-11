package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.config.jwt.JWTAuthenticationFilter;
import com.example.config.jwt.JWTFilterEntryPoint;
import com.example.service.UserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private UserDetailService userDetailService;
	
	
	@Autowired
	private JWTFilterEntryPoint jWTFilterEntryPoint;
	
	@Bean
	public JWTAuthenticationFilter jwtAuthFilter() {
		return new JWTAuthenticationFilter();
	}
	


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("Http Configured" + http);
		System.err.print(passwordEncoder().encode("pass@1234"));
		http
			.cors().disable()
			.csrf().disable()
			.authorizeRequests().antMatchers("/", "/api/auth/seller").permitAll()
			.anyRequest().authenticated()
			.and()
			.httpBasic().disable()
			.formLogin().disable()
			.exceptionHandling().authenticationEntryPoint(jWTFilterEntryPoint).and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
	}
	
	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	

}
