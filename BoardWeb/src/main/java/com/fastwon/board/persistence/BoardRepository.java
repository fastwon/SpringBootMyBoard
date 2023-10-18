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
import com.fastwon.board.domain.Category;

public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board> {
	
	@Query("SELECT b FROM Board b")
	Page<Board> getBoardList(Pageable pageable);
	
//	@Query("SELECT b FROM Board b WHERE b.createDate >= :oneWeekAgo ORDER BY b.cnt DESC")
//	Page<Board> findTopByOrderByViewCountDescAndCreatedAtAfter(@Param("oneWeekAgo") Date oneWeekAgo, Pageable pageable);

	@Query("SELECT b FROM Board b WHERE b.category = :category AND b.createDate >= :oneWeekAgo ORDER BY b.cnt DESC")
	Page<Board> findTopByCategoryAndCreateDateAfterOrderByViewCountDesc(@Param("category") Category category, @Param("oneWeekAgo") Date oneWeekAgo, Pageable pageable);

	@Query("SELECT b FROM Board b WHERE b.category IS NULL AND b.createDate >= :oneWeekAgo ORDER BY b.cnt DESC")
	Page<Board> findByCategoryIsNullAndCreateDateAfterOrderByViewCountDesc(@Param("oneWeekAgo") Date oneWeekAgo, Pageable pageable);

}
