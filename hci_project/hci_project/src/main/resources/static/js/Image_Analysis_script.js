document.getElementById("analyzeButton").addEventListener("click", function() {
  var inputFile = document.getElementById("uploadImage").files[0];
  if (inputFile) {
    var result = performAnalysis(inputFile);
    displayResult(result);
  } else {
    alert("Please Upload Image");
  }
});

function performAnalysis(file) {
  return "Result : " + file.name;
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
