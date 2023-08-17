package com.fastwon.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("/checkName")
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
	
	@PostMapping("/createMember")
	public String createMember(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberService.createMember(member);
		
		return "redirect:/";
	}
}
