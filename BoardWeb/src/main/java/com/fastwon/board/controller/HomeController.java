package com.fastwon.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fastwon.board.domain.Board;
import com.fastwon.board.service.BoardService;

@Controller
public class HomeController {
	@Autowired
	private BoardService boardService;
	
	
	@GetMapping("/")
	public String home(Model model) {
		Page<Board> boardList = boardService.getMostViewedPostsInOneWeek();
		
		model.addAttribute("boardList", boardList);
		
		return "home";
	}
}
