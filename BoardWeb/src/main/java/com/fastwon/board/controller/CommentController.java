package com.fastwon.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastwon.board.domain.Comment;
import com.fastwon.board.security.SecurityUser;
import com.fastwon.board.service.CommentService;

@Controller
@RequestMapping
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/comment/insertComment")
	public String insertComment(Comment comment, @AuthenticationPrincipal SecurityUser principal) {
		comment.setCmtWriter(principal.getMember().getName());
		commentService.insertComment(comment);
		
		return "redirect:/board/getBoard?seq=" + comment.getBoard().getSeq();
	}
	
	@PostMapping("/comment/updateComment")
	public String updateComment(Comment comment) {
		Comment comment1 =commentService.updateComment(comment);
		
		return "redirect:/board/getBoard?seq=" + comment1.getBoard().getSeq();
	}
	
	@GetMapping("/comment/deleteComment")
	public String deleteComment(Comment comment) {
		long bSeq = comment.getBoard().getSeq();

		commentService.deleteComment(comment);
		
		return "forward:/board/getBoard?seq=" + bSeq;
	}

}
