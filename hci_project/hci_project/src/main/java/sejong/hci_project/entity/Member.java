package sejong.hci_project.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nickname;
    private String password;
    @CreatedDate
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String email,String password,Authority authority ){
        this.email =email;
        this.password =password;
        this.authority=authority;
    }

    public Member(){
    }



}
