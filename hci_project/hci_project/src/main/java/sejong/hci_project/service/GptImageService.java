package sejong.hci_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GptImageService {

    @Value("${open.api.key}")
    private String openaiApiKey;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String analyzeImage(MultipartFile imageFile) {
        String prompt = "The user has uploaded an image. Analyze the emotions conveyed by the image. Please answer within 4 lines.";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 이미지 파일을 base64로 인코딩
        String base64Image = encodeImageToBase64(imageFile);

        // Prepare JSON part of the request
        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        Map<String, Object> imageUrl = new HashMap<>();
        imageUrl.put("type", "image_url");
        imageUrl.put("image_url", "data:image/jpeg;base64," + base64Image);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4-vision-preview");
        requestBody.put("messages", List.of(userMessage, imageUrl));
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

    private String encodeImageToBase64(MultipartFile imageFile) {
        try {
            byte[] imageBytes = imageFile.getBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to encode image to base64", e);
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
