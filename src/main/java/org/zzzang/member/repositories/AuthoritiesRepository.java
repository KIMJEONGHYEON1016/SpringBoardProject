package org.zzzang.member.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zzzang.member.entities.Authorities;
import org.zzzang.member.entities.AuthoritiesId;
import org.zzzang.member.entities.Member;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesId>, QuerydslPredicateExecutor<Authorities> {
    List<Authorities> findByMember(Member member);
}
