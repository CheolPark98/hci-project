package sejong.hci_project.exception;


public class NoSuchEmaillException extends RuntimeException{
    @Override
    public String getMessage() {
        return "존재하지 않는 이메일입니다.";
    }
}
