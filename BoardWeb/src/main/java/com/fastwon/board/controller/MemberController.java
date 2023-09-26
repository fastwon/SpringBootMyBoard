package com.fastwon.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastwon.board.domain.Member;
import com.fastwon.board.security.SecurityUser;
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
	
	@GetMapping("/{id}/deleteMember")
	public String deleteMember(@PathVariable String id, @AuthenticationPrincipal SecurityUser principal) {
		if(!id.equals(principal.getMember().getId())) {
			return "system/accessDenied";
		}
		memberService.deleteMember(id);
		return "redirect:/system/logout";
	}
	
	@GetMapping("/{id}/myPage")
	public String getMyPage(@PathVariable String id, Model model, @AuthenticationPrincipal SecurityUser principal) {
		if(!id.equals(principal.getMember().getId())) {
			return "system/accessDenied";
		}
		
		Member member = memberService.getMember(id);
		model.addAttribute("member", member);
		return "system/myPage";
	}
	
	@PostMapping("/updateMember")
	public String updateMember(Member member, @RequestParam("passwordSet") String newPassword, Model model) {
		String errorMsg = memberService.updateMember(member, newPassword);
		
		if(errorMsg != null) {
			model.addAttribute("errorMsg", errorMsg);
			return "/system/myPage";
		}
		
		return "redirect:/board/getBoardList";
	}
}
