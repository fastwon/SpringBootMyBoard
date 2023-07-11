package com.fastwon.board.persistence;

import org.springframework.data.repository.CrudRepository;

import com.fastwon.board.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

}
