document.getElementById("voterForm").addEventListener("submit", async function(e) {
    e.preventDefault();
    const form = e.target;

    // Validate form
    if (!form.checkValidity()) {
        alert("Please fill out all required fields correctly.");
        return;
    }

    // Create form data object
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    try {
        // Real API call
        const response = await fetch("/voter/registration", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            // Success handling
            alert("Voter added successfully!");
            form.reset();
            
            // Reset validation states
            document.querySelectorAll('input, select').forEach(el => {
                el.style.borderColor = '#ddd'; // Reset to default
            });
        } else {
            // Error handling
            const error = await response.json();
            alert("Error: " + (error.message || response.statusText));
        }
    } catch (err) {
        // Network error handling
        console.error("Submission error:", err);
        alert("Network error: " + err.message);
    }
});

// Real-time validation feedback
document.querySelectorAll('input, select').forEach(element => {
    element.addEventListener('input', () => {
        if (element.checkValidity()) {
            element.style.borderColor = '#8aff6d'; // Green for valid
        } else {
            element.style.borderColor = '#ff6d6d'; // Red for invalid
        }
    });
    
    // Add initial validation on page load for required fields
    if (element.required) {
        element.dispatchEvent(new Event('input'));
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const birthDateInput = document.getElementById('birthDate');
    birthDateInput.max = new Date().toISOString().split('T')[0];

    birthDateInput.addEventListener('change', function() {
        if (new Date(this.value) > new Date()) {
            this.setCustomValidity('Birth date cannot be in the future');
            document.getElementById('birthDateError').textContent = 'Birth date cannot be in the future';
        } else {
            this.setCustomValidity('');
            document.getElementById('birthDateError').textContent = '';
        }
    });
});