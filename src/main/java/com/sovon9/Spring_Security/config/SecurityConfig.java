package com.sovon9.Spring_Security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sovon9.Spring_Security.config.service.MyUserDetailsService;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig
{
//	@Bean
//	public UserDetailsService userDetailsService()
//	{
//		UserDetails user1 = User.withUsername("sovon")
//				.password("password")
//				.roles("user")
//				.build();
//		UserDetails user2 = User.withUsername("sougata")
//				.password("password")
//				.roles("admin")
//				.build();
//		return new InMemoryUserDetailsManager(user1,user2);
//	}
	
	// After adding password encoder we can store passwords using encoder
//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder)
//	{
//		UserDetails user1 = User.withUsername("sovon")
//				.password(passwordEncoder.encode("password"))
//				.roles("USER")
//				.build();
//		UserDetails user2 = User.withUsername("sougata")
//				.password(passwordEncoder.encode("password"))
//				.roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(user1,user2);
//	}
	
	/**
	 * loading password from database by extending userdetails service class
	 * @param passwordEncoder
	 * @return
	 */
	@Bean
	public UserDetailsService userDetailsService()
	{
		return new MyUserDetailsService();
	}
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
//	{
//		httpSecurity.authorizeHttpRequests((req)->req.requestMatchers("/home")
//				.permitAll()
//				.anyRequest()
//				.authenticated()
//				)
////		.csrf(csrf->csrf.disable())
//		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////		httpSecurity.formLogin(form -> form
////				.loginPage("/login")
////				.permitAll());
//		.httpBasic(withDefaults());
//		return httpSecurity.build();
//	}
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/login").permitAll() // permitting all access without authentication
                .requestMatchers("/home").permitAll() 
                .anyRequest().authenticated() // authenticating all other requests
            )
//            .formLogin(formLogin -> formLogin
//                .loginPage("/login")
//                .defaultSuccessUrl("/home")
//                .permitAll()
//            )
            .formLogin(withDefaults())
            //.httpBasic(withDefaults())
            .logout(logout -> logout.permitAll());

        return http.build();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
}
