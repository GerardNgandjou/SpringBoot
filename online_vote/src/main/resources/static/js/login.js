document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const togglePassword = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');

    // Toggle password visibility
    if (togglePassword && passwordInput) {
        togglePassword.addEventListener('click', function() {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            this.textContent = type === 'password' ? '👁️' : '👁️‍🗨️';
        });
    }

    // Form validation
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            // Client-side validation
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value.trim();

            if (!email || !password) {
                e.preventDefault();
                alert('Please fill in all fields');
                return;
            }

            // You can add more client-side validation here
            // For example: password strength, email format, etc.

            // If using AJAX instead of form submission:
            // e.preventDefault();
            // submitLoginForm(email, password);
        });
    }

});