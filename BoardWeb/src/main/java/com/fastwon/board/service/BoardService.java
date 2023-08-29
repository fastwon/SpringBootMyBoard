package com.fastwon.board.service;

import org.springframework.data.domain.Page;

import com.fastwon.board.domain.Board;
import com.fastwon.board.domain.PageNum;
import com.fastwon.board.domain.Search;

public interface BoardService {

	void insertBoard(Board board);
	
	void updateBoard(Board board);
	
	void deleteBoard(Board board);
	
	Board getBoard(Board board);
	
	Page<Board> getBoardList(Search search, PageNum pn);
	
	Board getUdateBoard(Board board);
	
	
}
