// Polling stations data for each region
const pollingStations = {
    "Adamawa": [
        { id: "ADA001", name: "Ngaoundéré Main Station" },
        { id: "ADA002", name: "Tibati Central" },
        { id: "ADA003", name: "Mayo-Baléo Station" },
        { id: "ADA004", name: "Mbé Station" },
        { id: "ADA005", name: "Djohong Station" }
    ],
    // ... (keep all your existing polling station data)
};

document.addEventListener('DOMContentLoaded', function() {
    initForm();
});

function initForm() {
    document.getElementById('birthDate').max = new Date().toISOString().split('T')[0];
    setupEventListeners();
    updatePollingStations();
}

function setupEventListeners() {
    const form = document.getElementById("voterForm");

    form.addEventListener('input', function(event) {
        if (event.target.matches('input, select')) {
            validateField(event.target);
        }
    });

    form.addEventListener('focusout', function(event) {
        if (event.target.matches('input, select')) {
            validateField(event.target);
        }
    });

    document.getElementById('birthDate').addEventListener('change', validateBirthDate);
    document.getElementById('region').addEventListener('change', updatePollingStations);
    form.addEventListener('submit', handleFormSubmit);
}

function validateField(element) {
    const errorElement = element.nextElementSibling?.classList.contains('error-message')
        ? element.nextElementSibling
        : null;

    element.classList.remove('error-highlight', 'success-highlight');

    if (element.checkValidity()) {
        if (element.value && !element.hasAttribute('data-no-success')) {
            element.classList.add('success-highlight');
        }
        element.setAttribute('aria-invalid', 'false');
        if (errorElement) errorElement.textContent = '';
    } else {
        element.classList.add('error-highlight');
        element.setAttribute('aria-invalid', 'true');
        if (errorElement) {
            errorElement.textContent = element.validationMessage || 'Please fill this field correctly';
        }
    }
}

function validateBirthDate() {
    const birthDateInput = document.getElementById('birthDate');
    const errorElement = document.getElementById('birthDateError');
    const birthDate = new Date(birthDateInput.value);
    const today = new Date();

    birthDateInput.classList.remove('error-highlight', 'success-highlight');
    birthDateInput.setCustomValidity('');
    errorElement.textContent = '';
    birthDateInput.setAttribute('aria-invalid', 'false');

    if (!birthDateInput.value) return;

    if (birthDate > today) {
        const errorMsg = 'Birth date cannot be in the future';
        birthDateInput.setCustomValidity(errorMsg);
        errorElement.textContent = errorMsg;
        birthDateInput.classList.add('error-highlight');
        birthDateInput.setAttribute('aria-invalid', 'true');
        return;
    }

    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    if (age < 21) {
        const errorMsg = `You must be at least 21 years old (current age: ${age})`;
        birthDateInput.setCustomValidity(errorMsg);
        errorElement.textContent = errorMsg;
        birthDateInput.classList.add('error-highlight');
        birthDateInput.setAttribute('aria-invalid', 'true');
    } else {
        birthDateInput.classList.add('success-highlight');
        validateField(birthDateInput);
    }
}

function updatePollingStations() {
    const regionSelect = document.getElementById('region');
    const pollingStationSelect = document.getElementById('pollingStation');
    const selectedRegion = regionSelect.value;

    pollingStationSelect.innerHTML = '';

    const defaultOption = new Option('Select your polling station', '', true, true);
    defaultOption.disabled = true;
    pollingStationSelect.add(defaultOption);

    if (selectedRegion && pollingStations[selectedRegion]) {
        pollingStations[selectedRegion].forEach(station => {
            const option = new Option(`${station.name} (${station.id})`, station.id);
            pollingStationSelect.add(option);
        });
    }

    validateField(pollingStationSelect);
}

async function handleFormSubmit(e) {
    e.preventDefault();
    const form = e.target;
    const submitButton = document.getElementById('submitButton');

    if (!validateAllFields(form)) {
        showAlert('Please fill out all required fields correctly.', 'error');
        return;
    }

    try {
        submitButton.classList.add('loading');
        submitButton.disabled = true;

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());

        if (data.birthDate) {
            data.birthDate = new Date(data.birthDate).toISOString().split('T')[0];
        }

        // Get CSRF token from Thymeleaf
        const csrfToken = document.querySelector('input[name="_csrf"]').value;

        const response = await fetch('/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const result = await response.json();
            showSuccessMessage(result);
            form.reset();
        } else {
            const errorResponse = await response.json();
            handleServerErrors(errorResponse);
        }
    } catch (error) {
        showAlert('An error occurred while submitting the form. Please try again.', 'error');
        console.error('Submission error:', error);
    } finally {
        submitButton.classList.remove('loading');
        submitButton.disabled = false;
    }
}

function handleServerErrors(errorResponse) {
    document.querySelectorAll('.error-highlight').forEach(el => {
        el.classList.remove('error-highlight');
    });

    document.querySelectorAll('.error-message').forEach(el => {
        el.textContent = '';
    });

    if (errorResponse.errors) {
        for (const [field, message] of Object.entries(errorResponse.errors)) {
            const inputElement = document.getElementById(field);
            if (inputElement) {
                inputElement.classList.add('error-highlight');
                const errorElement = inputElement.nextElementSibling?.classList.contains('error-message')
                    ? inputElement.nextElementSibling
                    : document.getElementById(`${field}Error`);

                if (errorElement) {
                    errorElement.textContent = message;
                }
            }
        }
    } else if (errorResponse.message) {
        showAlert(errorResponse.message, 'error');
    }
}

function showSuccessMessage(result) {
    const existingMessage = document.querySelector('.success-message');
    if (existingMessage) existingMessage.remove();

    const successMessage = document.createElement('div');
    successMessage.className = 'success-message';
    successMessage.innerHTML = `
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"/>
        </svg>
        <div>
            <h3>Registration Successful!</h3>
            <p>Voter ID: ${result.id || 'N/A'}</p>
            <p>Thank you for registering. A confirmation email has been sent to ${result.email}.</p>
        </div>
    `;

    const form = document.getElementById('voterForm');
    form.parentNode.insertBefore(successMessage, form);
    successMessage.scrollIntoView({ behavior: 'smooth' });
}

function validateAllFields(form) {
    let isValid = true;

    validateBirthDate();

    form.querySelectorAll('input[required], select[required]').forEach(element => {
        validateField(element);
        if (!element.checkValidity()) {
            isValid = false;
        }
    });

    return isValid;
}

function showAlert(message, type = 'error') {
    alert(message);
}