package sejong.hci_project.exception;


public class DuplicateEmailException extends RuntimeException{
    @Override
    public String getMessage() {
        return "이미 존재하는 이메일입니다.";
    }
}
