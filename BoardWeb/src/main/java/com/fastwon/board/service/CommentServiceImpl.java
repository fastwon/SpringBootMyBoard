package com.fastwon.board.service;

import java.util.Date;

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
	public Comment updateComment(Comment comment) {
		Comment comment1 = commentRepo.findById(comment.getCmtId()).get();
		
		comment1.setCmtContent(comment.getCmtContent());
		comment1.setCmtCreateDate(new Date());
		
		commentRepo.save(comment1);
		
		return comment1;
	}
	
	@Override
	public long deleteComment(Comment comment) {
		Comment comment1 = commentRepo.findById(comment.getCmtId()).get();
		
		long bSeq = comment1.getBoard().getSeq();
		
		commentRepo.deleteById(comment.getCmtId());
		
		return bSeq;
	}
	
	@Override
	public Page<Comment> getCommentList(PageNum pn, Board board) {
		BooleanBuilder bb = new BooleanBuilder();
		
		QComment qcmt = QComment.comment;
		
//		System.out.println("보드 시퀀스 뭐야???" + board.getSeq());
		
		bb.and(qcmt.board.seq.eq(board.getSeq()));
		
		Pageable pageable = PageRequest.of(pn.getCmtPage()-1, 10, Sort.Direction.DESC, "cmtId");
		return commentRepo.findAll(bb, pageable);
	}

}
