// script.js
document.getElementById('analyzeButton').addEventListener('click', () => {
    const imageInput = document.getElementById('imageInput');
    const resultContainer = document.getElementById('resultContainer');
    resultContainer.innerHTML = '';
  
    if (imageInput.files.length === 0) {
      alert('Please select an image file.');
      return;
    }
  
    const file = imageInput.files[0];
    const reader = new FileReader();
  
    reader.onload = function(e) {
      const img = new Image();
      img.src = e.target.result;
  
      img.onload = function() {
        Tesseract.recognize(
          img,
          'eng',
          {
            logger: m => console.log(m)
          }
        ).then(({ data: { text } }) => {
          resultContainer.innerHTML = `<p>${text}</p>`;
        }).catch(err => {
          console.error(err);
          resultContainer.innerHTML = `<p>Failed to analyze image.</p>`;
        });
      }
    };
  
    reader.readAsDataURL(file);
  });
  