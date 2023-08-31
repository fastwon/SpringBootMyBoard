package com.fastwon.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fastwon.board.domain.Member;
import com.fastwon.board.domain.PageNum;
import com.fastwon.board.domain.QBoard;
import com.fastwon.board.domain.QMember;
import com.fastwon.board.persistence.MemberRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public void createMember(Member member) {
//		member.setPassword("{noop}" +member.getPassword());
		if(memberRepo.findById(member.getId()).isPresent()) {
			return;
		}
		
		memberRepo.save(member);
	}
	
	@Override
	public String checkId(Member member) {
		if(memberRepo.findById(member.getId()).isPresent()) {
			
			return null;
		}
		return member.getId();
	}
	
	@Override
	public String checkName(Member member) {
		if(memberRepo.findByName(member.getName()).isPresent()) {
			
			return null;
		}
		return member.getName();
	}
	
	@Override
	public void deleteMember(Member member) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Page<Member> getMemberList() {
		
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
		return memberRepo.getMemberList(pageable);
	}

}
