package sejong.hci_project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GptChatService {

    @Value("${open.api.key}")
    private String openaiApiKey;



    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String getPromptResult(String prompt) {


        log.info("Received prompt: {}", prompt);
        prompt="The user will provide the text, and look at it and analyze the user's emotions. Answer the analyzed user's emotions accurately and comprehensively.\n" +
                "Please answer within 4 lines"+prompt;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("model", "gpt-4");
        requestBody.put("messages", List.of(message));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody.containsKey("choices")) {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                    if (!choices.isEmpty()) {
                        Map<String, Object> choice = choices.get(0);
                        Map<String, Object> messageResponse = (Map<String, Object>) choice.get("message");
                        if (messageResponse != null && messageResponse.containsKey("content")) {
                            String content = (String) messageResponse.get("content");
                            return extractResponse(content);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Error generating response";
    }


    private String extractResponse(String content) {
        try {
            // JSON 파싱을 위한 ObjectMapper 사용
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String, String> responseMap = mapper.readValue(content, Map.class);

            String finalAnswer = responseMap.values().toString();

            return finalAnswer.substring(1,finalAnswer.length()-1);

        } catch (Exception e) {
            return content;
        }
    }
}