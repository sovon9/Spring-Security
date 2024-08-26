package com.sovon9.Spring_Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sovon9.Spring_Security.config.model.User;
import com.sovon9.Spring_Security.config.service.MyUserDetailsService;

@Controller
public class UserController
{
//	@Autowired
//	private MyUserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/home")
	public String home()
	{
		return "home";
	}
	
	//@PreAuthorize("hasRole('user')")
	@ResponseBody
	@GetMapping("/user")
	public String user()
	{
		return "Only User Access Allowed";
	}
	
	//@PreAuthorize("hasRole('admin')")
	@ResponseBody
	@GetMapping("/admin")
	public String admin()
	{
		return "Only Admin Access Allowed";
	}
//	@GetMapping("/login")
//	public String login()
//	{
//		return "login";
//	}
	
	@PostMapping("/saveUser")
	public void saveUser(@RequestBody User user)
	{
		user.setPassword(encoder.encode(user.getPassword()));
		//userDetailsService.saveUser(user);
	}
}
