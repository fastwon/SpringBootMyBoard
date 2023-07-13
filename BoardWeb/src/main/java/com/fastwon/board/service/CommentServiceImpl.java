package com.fastwon.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fastwon.board.domain.Board;
import com.fastwon.board.domain.Comment;
import com.fastwon.board.domain.PageNum;
import com.fastwon.board.domain.QComment;
import com.fastwon.board.persistence.CommentRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Override
	public void insertComment(Comment comment) {
		commentRepo.save(comment);
		
	}
	
	@Override
	public void updateComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deleteComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Page<Comment> getCommentList(PageNum pn, Board board) {
		BooleanBuilder bb = new BooleanBuilder();
		
		QComment qcmt = QComment.comment;
		
		System.out.println("보드 시퀀스 뭐야???" + board.getSeq());
		
		bb.and(qcmt.board.seq.eq(board.getSeq()));
		
		Pageable pageable = PageRequest.of(pn.getCmtPage()-1, 10, Sort.Direction.DESC, "cmtId");
		return commentRepo.findAll(bb, pageable);
	}

}
