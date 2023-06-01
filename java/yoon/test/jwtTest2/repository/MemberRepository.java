package yoon.test.jwtTest2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yoon.test.jwtTest2.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findById(String id);
}
