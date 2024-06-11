package sejong.hci_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import sejong.hci_project.entity.Authority;

@Data
@AllArgsConstructor

public class MemberJoinDto {
    @Email
    private String email;
    @NotEmpty(message = "비밀번호가 입력되지 않았습니다.")
    private String password;
    @NotEmpty(message = "닉네임이 입력되지 않았습니다.")
    private String nickname;

    private Authority authority = Authority.ROLE_ADMIN;

    public MemberJoinDto(){}
    public MemberJoinDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}