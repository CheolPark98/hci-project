package sejong.hci_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sejong.hci_project.dto.ChatResponseDto;
import sejong.hci_project.service.GptChatService;

@RestController
@RequiredArgsConstructor
public class GptChatController {

    private final GptChatService gptChatService;

    @PostMapping("/text_analyze")
    @ResponseBody
    public ResponseEntity<ChatResponseDto> getChat(@RequestBody String prompt) {

        String result = gptChatService.getPromptResult(prompt);
        ChatResponseDto chatResponseDto = new ChatResponseDto();
        chatResponseDto.setResponse(result);

        return new ResponseEntity<ChatResponseDto> (chatResponseDto,HttpStatus.OK);
    }

}