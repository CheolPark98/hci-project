package sejong.hci_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sejong.hci_project.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail (String email);

    boolean existsByEmail(String email);


}
