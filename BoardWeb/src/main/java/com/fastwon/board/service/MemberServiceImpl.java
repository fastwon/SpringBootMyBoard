package com.fastwon.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastwon.board.domain.Member;
import com.fastwon.board.persistence.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public void createMember(Member member) {
//		member.setPassword("{noop}" +member.getPassword());
		if(memberRepo.findById(member.getId()) != null) {
			return;
		}
		
		
		
		memberRepo.save(member);
	}
	
	@Override
	public void deleteMember(Member member) {
		// TODO Auto-generated method stub
		
	}

}