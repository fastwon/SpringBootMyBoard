package com.fastwon.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("/system/checkId")
	public ResponseEntity<?> checkMemberId(Member member) {
	    String mId = memberService.checkId(member);
	    Map<String, Object> response = new HashMap<>();

	    if(mId != null) {
	        response.put("isDuplicated", false);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.put("isDuplicated", true);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	}
	
	@GetMapping("/system/checkName")
	public ResponseEntity<?> checkMemberName(Member member) {
	    String mName = memberService.checkName(member);
	    Map<String, Object> response = new HashMap<>();

	    if(mName != null) {
	        response.put("nameDuplicated", false);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.put("nameDuplicated", true);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	}
	
	@GetMapping("/admin/enabledChange")
	public String eabledChange(Member member) {
		Member findMember = memberService.getMember(member.getId());
		
		
		if(findMember.isEnabled()) {
			findMember.setEnabled(false);			
		} else {
			findMember.setEnabled(true);			
		}
		memberRepo.save(findMember);
		
		return "redirect:/admin/adminPage";
	}
}
