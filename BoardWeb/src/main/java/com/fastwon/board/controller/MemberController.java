package com.fastwon.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastwon.board.domain.Member;
import com.fastwon.board.service.MemberService;

@Controller
@RequestMapping("/member/")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/createMember")
	public String createMemberVies() {
		return "system/signUp";
	}
	
	@PostMapping("/createMember")
	public String createMember(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberService.createMember(member);
		
		return "redirect:/";
	}
}
