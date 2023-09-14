package com.fastwon.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fastwon.board.domain.Board;
import com.fastwon.board.domain.Member;
import com.fastwon.board.persistence.BoardRepository;
import com.fastwon.board.persistence.CommentRepository;
import com.fastwon.board.persistence.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	
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
	public void deleteMember(String id) {
		Member member = memberRepo.findById(id).get();
		
		if(!member.getBoardList().isEmpty()) {
			List<Board> boardList = member.getBoardList();
			
			for(Board b : boardList) {
				if(!b.getCommentList().isEmpty()) {
					commentRepo.deleteAll(b.getCommentList());
				}
			}
			boardRepo.deleteAll(boardList);
		}
		
		memberRepo.deleteById(id);
		
	}
	
	@Override
	public Page<Member> getMemberList() {
		
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
		return memberRepo.getMemberList(pageable);
	}
	
	@Override
	public Member getMember(String id) {
		return memberRepo.findById(id).get();
	}

}
