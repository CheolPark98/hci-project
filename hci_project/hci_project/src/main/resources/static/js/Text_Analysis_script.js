document.getElementById("analyzeButton").addEventListener("click", function() {
  var inputText = document.getElementById("inputText").value;
  var result = performAnalysis(inputText);
  displayResult(result);
});

function performAnalysis(text) {
  return "Result : " + text.length;
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
