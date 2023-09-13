package com.fastwon.board.persistence;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fastwon.board.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board> {
	
	@Query("SELECT b FROM Board b")
	Page<Board> getBoardList(Pageable pageable);
	
	@Query("SELECT b FROM Board b WHERE b.createDate >= :oneWeekAgo ORDER BY b.cnt DESC")
	Page<Board> findTopByOrderByViewCountDescAndCreatedAtAfter(@Param("oneWeekAgo") Date oneWeekAgo, Pageable pageable);

}
