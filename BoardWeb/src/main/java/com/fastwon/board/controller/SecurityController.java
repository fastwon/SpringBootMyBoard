package com.fastwon.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {

	@GetMapping("/system/login")
	public String login(@RequestParam(value = "error", required = false)String error, @RequestParam(value = "exception", required = false)String exception, Model model) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "/system/login";
	}
	
	@GetMapping("/system/accessDenied")
	public void accessDenied() {
	}
	
	@GetMapping("/system/logout")
	public void logout() {
	}
	
	@GetMapping("/admin/adminPage")
	public void admin() {
	}
}
