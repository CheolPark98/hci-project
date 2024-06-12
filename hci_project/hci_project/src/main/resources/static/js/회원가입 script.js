// script.js
document.getElementById('signUpForm').addEventListener('submit', function(event) {
    event.preventDefault();
  
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const confirmPassword = document.getElementById('confirmPassword').value.trim();
    const message = document.getElementById('message');
  
    if (name === '' || email === '' || password === '' || confirmPassword === '') {
      message.textContent = 'All fields are required.';
      return;
    }
  
    if (password !== confirmPassword) {
      message.textContent = 'Passwords do not match.';
      return;
    }
  
    if (password.length < 6) {
      message.textContent = 'Password must be at least 6 characters long.';
      return;
    }
  
    message.textContent = 'Sign up successful!';
    message.style.color = 'green';
  });
  