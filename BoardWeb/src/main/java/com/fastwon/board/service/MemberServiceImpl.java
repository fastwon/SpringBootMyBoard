package com.fastwon.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
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
	public String deleteMember(String id, String password) {
		Member member = memberRepo.findById(id).get();
		
		if(!passwordEncoder.matches(password, member.getPassword())) {
			return "비밀번호가 일치하지 않습니다.";
		}
		
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
		
		return null;
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
	
	@Override
	public String updateMember(Member member, String newPassword) {
		Member saveMember = memberRepo.findById(member.getId()).get();
		
		if(passwordEncoder.matches(member.getPassword(), saveMember.getPassword())) {
			saveMember.setPassword(passwordEncoder.encode(newPassword));
			memberRepo.save(saveMember);
			
			return null;
		}
		return "기존 비밀번호가 일치하지 않습니다.";
		
	}

}
