package com.fastwon.board.persistence;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fastwon.board.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {
	
	Optional<Member> findByName(String name);
	
	@Query("SELECT m FROM Member m")
	Page<Member> getMemberList(Pageable pageable);
}
