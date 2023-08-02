package com.fastwon.board.service;

import com.fastwon.board.domain.Member;

public interface MemberService {

	void createMember(Member member);
	
	String checkId(Member member);
	
	void deleteMember(Member member);
}
