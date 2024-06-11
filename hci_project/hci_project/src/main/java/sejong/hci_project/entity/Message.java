package sejong.hci_project.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String context;
    @CreatedDate
    private LocalDateTime timeStamp;
    private boolean isUserMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
