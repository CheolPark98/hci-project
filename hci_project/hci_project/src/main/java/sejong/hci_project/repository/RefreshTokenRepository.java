package sejong.hci_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sejong.hci_project.entity.RefreshToken;


import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByKey(String key);
}
