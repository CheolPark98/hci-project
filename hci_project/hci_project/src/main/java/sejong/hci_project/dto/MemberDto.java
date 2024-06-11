package sejong.hci_project.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private String nickname;
    private String email;

}
