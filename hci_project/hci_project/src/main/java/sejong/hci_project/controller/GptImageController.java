package sejong.hci_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sejong.hci_project.service.GptImageService;

@Controller
@RequiredArgsConstructor
public class GptImageController {

    @Autowired
    private GptImageService gptService;

    @PostMapping("/picture_analyze")
    public String generateImage(@RequestParam("file") MultipartFile file) {
        return gptService.analyzeImage(file);
    }
}
