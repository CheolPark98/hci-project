document.getElementById("analyzeButton").addEventListener("click", function() {
    var prompt = document.getElementById("prompt").value;
    performAnalysis(prompt);
});

function performAnalysis(text) {
    var endpoint = "/text_analyze"; // 실제 엔드포인트로 변경하세요.

    fetch(endpoint, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ prompt: text })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            displayResult(data.response); // 응답 데이터를 사용하여 결과 표시
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
            displayResult("Error: " + error.message);
        });
}

function displayResult(result) {
    var modal = document.getElementById("resultModal");
    var resultText = document.getElementById("resultText");
    var closeButton = document.getElementsByClassName("close-button")[0];

    resultText.textContent = result;
    modal.style.display = "block";

    closeButton.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}
