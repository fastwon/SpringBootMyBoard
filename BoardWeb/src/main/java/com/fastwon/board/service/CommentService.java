package com.fastwon.board.service;

import org.springframework.data.domain.Page;

import com.fastwon.board.domain.Board;
import com.fastwon.board.domain.Comment;
import com.fastwon.board.domain.PageNum;

public interface CommentService {

	void insertComment(Comment comment);

	Comment updateComment(Comment comment);
	
	long deleteComment(Comment comment);
	
	Page<Comment> getCommentList(PageNum pn, Board board);
}
