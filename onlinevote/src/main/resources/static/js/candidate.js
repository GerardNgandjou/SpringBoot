document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('candidateForm');
    
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const submitButton = form.querySelector('button[type="submit"]');
        const originalButtonText = submitButton.innerHTML;
        submitButton.disabled = true;
        submitButton.innerHTML = '<i class="fas fa-spinner animate-spin mr-2"></i> Processing...';
        
        try {
            const formData = new FormData(form);
            const candidateData = {
                id: formData.get('id'),
                deposit: formData.get('deposit') ? parseFloat(formData.get('deposit')) : null,
                score: formData.get('score') ? parseInt(formData.get('score')) : null
            };
            
            const response = await fetch('/candidate/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(candidateData)
            });
            
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to save candidate');
            }
            
            const result = await response.json();
            showNotification('Candidate added successfully!', 'success');
            
            // Reset form or redirect
            // setTimeout(() => {
            //     window.location.href = '/candidates';
            // }, 1500);
            
        } catch (error) {
            showNotification(error.message, 'error');
            console.error('Error:', error);
        } finally {
            submitButton.disabled = false;
            submitButton.innerHTML = originalButtonText;
        }
    });
    
    function showNotification(message, type) {
        const notification = document.createElement('div');
        notification.className = `fixed top-4 right-4 px-6 py-3 rounded-md shadow-lg text-white ${
            type === 'error' ? 'bg-red-500' : 'bg-green-500'
        }`;
        notification.textContent = message;
        document.body.appendChild(notification);
        
        setTimeout(() => {
            notification.classList.add('opacity-0', 'transition-opacity', 'duration-500');
            setTimeout(() => notification.remove(), 500);
        }, 3000);
    }
});