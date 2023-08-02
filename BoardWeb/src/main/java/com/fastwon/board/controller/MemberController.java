package com.fastwon.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String createMemberViews() {
		return "system/signUp";
	}
	
	@GetMapping("/checkId")
	public String checkMemberId(Member member, Model model) {
		String mId = memberService.checkId(member);
		if(mId != null) {
			model.addAttribute("rightId", mId);
		} else {
			model.addAttribute("rightId", "중복입니다.");
		}
		return "system/signUp";
	}
	
	@PostMapping("/createMember")
	public String createMember(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberService.createMember(member);
		
		return "redirect:/";
	}
}
