package com.sovon9.Spring_Security.config.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sovon9.Spring_Security.config.model.Role;
import com.sovon9.Spring_Security.config.model.User;
import com.sovon9.Spring_Security.config.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		System.out.println("running..............");
		User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(
	            user.getUsername(),
	            user.getPassword(),
	            mapRolesToAuthorities(user.getRoles())
	        );
	}

	private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles)
	{
		return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());

	}
	
	@Transactional
	public User saveUser(User user)
	{
		return userRepository.save(user);
	}
	
}
