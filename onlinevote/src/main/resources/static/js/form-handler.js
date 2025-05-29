/**
 * Voter Registration Form Handler
 * Handles form submission, validation, and provides real-time feedback
 */

// Form submission handler
document.getElementById("voterForm").addEventListener("submit", async function(e) {
    e.preventDefault(); // Prevent default form submission
    const form = e.target;

    // Validate form before submission
    if (!form.checkValidity()) {
        alert("Please fill out all required fields correctly.");
        return;
    }

    // Convert form data to JSON
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    try {
        // API call to register voter
        const response = await fetch("/voters/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            // Success case
            alert("Voter added successfully!");
            form.reset();

            // Reset visual validation states
            document.querySelectorAll('input, select').forEach(el => {
                el.style.borderColor = '#ddd'; // Reset to default border color
            });

            // Optional: Redirect or update UI
            // window.location.href = "/success-page";

        } else {
            // Handle server-side errors
            const error = await response.json();
            alert("Error: " + (error.message || response.statusText));

            // Special handling for email exists error
            if (error.code === 'email_exists') {
                document.getElementById('email').style.borderColor = '#ff6d6d';
            }
        }
    } catch (err) {
        // Network or unexpected errors
        console.error("Submission error:", err);
        alert("Network error: " + err.message);
    }
});

/**
 * Real-time Form Validation Feedback
 * Provides visual feedback as user fills out the form
 */
document.querySelectorAll('input, select').forEach(element => {
    // Validate on input changes
    element.addEventListener('input', () => {
        validateField(element);
    });

    // Add initial validation for required fields on page load
    if (element.required) {
        validateField(element);
    }
});

/**
 * Validates a single form field and updates its visual state
 * @param {HTMLElement} element - The form element to validate
 */
function validateField(element) {
    if (element.checkValidity()) {
        element.style.borderColor = '#8aff6d'; // Green for valid
    } else {
        element.style.borderColor = '#ff6d6d'; // Red for invalid
    }
}

/**
 * Birth Date Validation
 * Ensures birth date is not in the future
 */
document.addEventListener('DOMContentLoaded', () => {
    const birthDateInput = document.getElementById('birthDate');

    // Set max date to today
    birthDateInput.max = new Date().toISOString().split('T')[0];

    // Validate on date change
    birthDateInput.addEventListener('change', function() {
        const errorElement = document.getElementById('birthDateError') ||
            createErrorElement(this);

        if (new Date(this.value) > new Date()) {
            this.setCustomValidity('Birth date cannot be in the future');
            errorElement.textContent = 'Birth date cannot be in the future';
            this.style.borderColor = '#ff6d6d';
        } else {
            this.setCustomValidity('');
            errorElement.textContent = '';
            validateField(this);
        }
    });
});

/**
 * Creates an error message element if it doesn't exist
 * @param {HTMLElement} inputElement - The input element needing error display
 * @returns {HTMLElement} The created or existing error element
 */
function createErrorElement(inputElement) {
    const errorElement = document.createElement('div');
    errorElement.id = inputElement.id + 'Error';
    errorElement.className = 'error-message';
    inputElement.parentNode.appendChild(errorElement);
    return errorElement;
}