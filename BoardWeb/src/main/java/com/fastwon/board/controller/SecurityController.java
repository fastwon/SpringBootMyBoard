package com.fastwon.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastwon.board.domain.Member;
import com.fastwon.board.persistence.MemberRepository;
import com.fastwon.board.service.MemberService;

@Controller
public class SecurityController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepo;

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
	public void admin(Model model) {
		
		Page<Member> memberList = memberService.getMemberList();
		
		model.addAttribute("memberList", memberList);
	}
	
	@GetMapping("/admin/enabledChange")
	public String eabledChange(Member member) {
		Member findMember = memberRepo.findById(member.getId()).get();
		
		if(findMember.isEnabled()) {
			findMember.setEnabled(false);			
		} else {
			findMember.setEnabled(true);			
		}
		memberRepo.save(findMember);
		
		return "redirect:/admin/adminPage";
	}
}
