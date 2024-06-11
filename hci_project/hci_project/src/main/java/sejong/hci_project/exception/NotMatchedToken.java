package sejong.hci_project.exception;

public class NotMatchedToken extends RuntimeException{
    @Override
    public String getMessage() {
        return "토큰의 유저정보가 일치하지 않습니다.";
    }
}
