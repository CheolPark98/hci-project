package sejong.hci_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        // 텍스트와 이미지의 URL을 설정
        return "홈화면"; // home.html로 연결
    }

    @GetMapping("/select")
    public String select(){
        return "텍스트_또는_사진_선택";
    }

    @GetMapping("/text_analyze")
    public String textAnalyzeLink(){
        return "텍스트입력 및 분석";
    }


    @GetMapping("/picture_analyze")
    public String pictureAnalyzeLink(){
        return "사진입력 및 분석";
    }
}

