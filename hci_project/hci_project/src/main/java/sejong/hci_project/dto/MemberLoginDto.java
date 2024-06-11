package sejong.hci_project.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginDto {
    @Email
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
