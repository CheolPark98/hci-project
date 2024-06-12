// script.js
document.getElementById('analyzeButton').addEventListener('click', () => {
    const inputText = document.getElementById('inputText').value.toLowerCase();
    const resultContainer = document.getElementById('resultContainer');
    resultContainer.innerHTML = '';
  
    if (inputText.trim() === '') {
      alert('Please enter some text.');
      return;
    }
  
    const words = inputText.split(/\W+/).filter(Boolean);
    const wordCounts = {};
  
    words.forEach(word => {
      wordCounts[word] = (wordCounts[word] || 0) + 1;
    });
  
    const sortedWords = Object.keys(wordCounts).sort((a, b) => wordCounts[b] - wordCounts[a]);
  
    const resultList = document.createElement('ul');
  
    sortedWords.forEach(word => {
      const listItem = document.createElement('li');
      listItem.textContent = `${word}: ${wordCounts[word]}`;
      resultList.appendChild(listItem);
    });
  
    resultContainer.appendChild(resultList);
  });
  