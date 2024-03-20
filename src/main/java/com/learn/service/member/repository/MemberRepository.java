package com.learn.service.member.repository;

import com.learn.service.member.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository  extends CrudRepository<Member, Long> {
}
