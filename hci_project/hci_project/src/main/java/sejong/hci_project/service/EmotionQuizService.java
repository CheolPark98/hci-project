package sejong.hci_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmotionQuizService {

    private final GptImageService gptImageService;

    public void getEmotionPicture(String prompt) {
        //String imgURL = gptImageService.generateImage(prompt);

    }

    public void checkEmotionResult(int answer) {

    }
}
