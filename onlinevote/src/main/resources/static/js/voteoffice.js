document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('officeForm');
    const statusMessage = document.getElementById('statusMessage');

    // Base URL for your Spring Boot API
    const API_BASE_URL = 'http://localhost:8080/api'; // Adjust this to your actual API URL
    const OFFICES_ENDPOINT = `${API_BASE_URL}/offices`;

    // Form validation and submission
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        let isValid = true;

        // Clear previous status message
        statusMessage.className = 'status-message';
        statusMessage.textContent = '';

        // Validate office name
        const nameField = document.getElementById('nameOffice');
        const nameError = document.getElementById('nameError');
        if (!nameField.value.trim()) {
            nameError.classList.add('show');
            isValid = false;
        } else {
            nameError.classList.remove('show');
        }

        // Validate position
        const positionField = document.getElementById('positionOffice');
        const positionError = document.getElementById('positionError');
        if (!positionField.value.trim()) {
            positionError.classList.add('show');
            isValid = false;
        } else {
            positionError.classList.remove('show');
        }

        // If form is valid, proceed with submission
        if (isValid) {
            const formData = {
                nameOffice: nameField.value.trim(),
                positionOffice: positionField.value.trim(),
                descriptionOffice: document.getElementById('descriptionOffice').value.trim()
            };

            try {
                // Disable submit button during request
                const submitBtn = form.querySelector('button[type="submit"]');
                submitBtn.disabled = true;
                submitBtn.textContent = 'Saving...';

                // Send data to Spring Boot endpoint
                const response = await fetch(OFFICES_ENDPOINT, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        // Include this if your API requires authentication
                        // 'Authorization': 'Bearer ' + yourAuthToken
                    },
                    body: JSON.stringify(formData)
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const result = await response.json();

                // Show success message
                statusMessage.textContent = 'Office saved successfully!';
                statusMessage.className = 'status-message success';

                // Reset form after successful submission
                form.reset();

                console.log('Success:', result);
            } catch (error) {
                console.error('Error:', error);
                statusMessage.textContent = `Error saving office: ${error.message}`;
                statusMessage.className = 'status-message error';
            } finally {
                // Re-enable submit button
                const submitBtn = form.querySelector('button[type="submit"]');
                submitBtn.disabled = false;
                submitBtn.textContent = 'Save Office';
            }
        }
    });
});