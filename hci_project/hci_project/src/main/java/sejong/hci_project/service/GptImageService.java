package sejong.hci_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GptImageService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String analyzeImage(String imageUrl) {
        String prompt = "The user has uploaded an image. Analyze the emotions conveyed by the image. Please answer within 4 lines.";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare JSON part of the request
        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        Map<String, Object> imageMessage = new HashMap<>();
        imageMessage.put("type", "image_url");
        imageMessage.put("image_url", imageUrl);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4-vision-preview");
        requestBody.put("messages", List.of(userMessage, imageMessage));
        requestBody.put("max_tokens", 1000);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return extractResponse(response.getBody());
            } else {
                return "Error: " + response.getStatusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error analyzing image";
        }
    }

    private String extractResponse(String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(content, Map.class);

            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                if (message != null) {
                    return (String) message.get("content");
                }
            }
            return "No valid response found.";
        } catch (Exception e) {
            return "Error parsing response: " + content;
        }
    }
}
