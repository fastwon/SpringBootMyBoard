package com.fastwon.board.service;

import org.springframework.data.domain.Page;

import com.fastwon.board.domain.Member;

public interface MemberService {

	void createMember(Member member);
	
	String checkId(Member member);
	
	String checkName(Member member);
	
	void deleteMember(String id);
	
	Page<Member> getMemberList();
	
	Member getMember(String id);
	
	String updateMember(Member member, String newPassword);
}
