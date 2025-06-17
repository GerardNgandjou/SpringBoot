// DOM elements
const electionsContainer = document.getElementById('electionsContainer');
const filterButtons = document.querySelectorAll('.filter-btn');

// Initialize the page
document.addEventListener('DOMContentLoaded', () => {
    fetchElections(); // Fetch elections from backend when page loads
    setupFilterButtons();
});

// Fetch elections from backend API
async function fetchElections() {
    try {
        const response = await fetch('/election/show');  // Updated path
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const elections = await response.json();
        renderElections(elections);

        // Store elections for filtering
        window.electionData = elections;
    } catch (error) {
        console.error('Error fetching elections:', error);
        electionsContainer.innerHTML = '<p>Error loading elections. Please try again later.</p>';
    }
}

// Render elections to the page
function renderElections(electionsToRender) {
    electionsContainer.innerHTML = '';

    if (!electionsToRender || electionsToRender.length === 0) {
        electionsContainer.innerHTML = '<p>No elections found matching your criteria.</p>';
        return;
    }

    electionsToRender.forEach(election => {
        const electionCard = createElectionCard(election);
        electionsContainer.appendChild(electionCard);
    });
}

// Create an election card element
function createElectionCard(election) {
    const card = document.createElement('div');
    card.className = 'election-card';

    // Determine status badge
    let statusBadgeClass, statusText;
    switch(election.electionStatus) {
        case 'active':
            statusBadgeClass = 'status-active';
            statusText = 'Active';
            break;
        case 'upcoming':
            statusBadgeClass = 'status-upcoming';
            statusText = 'Upcoming';
            break;
        case 'ended':
            statusBadgeClass = 'status-ended';
            statusText = 'Ended';
            break;
        default:
            statusBadgeClass = 'status-ended';
            statusText = 'Unknown';
    }

    // Format dates
    const startDate = new Date(election.electionStartDate).toLocaleDateString();
    const endDate = new Date(election.electionEndDate).toLocaleDateString();

    // Determine action button
    let actionButton;
    if (election.electionStatus === 'upcoming') {
        actionButton = `<a href="#" class="action-btn register-btn" data-id="${election.idElection}">
                    <i class="fas fa-user-plus"></i> Register
                </a>`;
    } else {
        actionButton = `<a href="/elections/${election.idElection}" class="action-btn view-btn" data-id="${election.idElection}">
                    <i class="fas fa-eye"></i> View
                </a>`;
    }

    card.innerHTML = `
                <div class="card-header">
                    <span class="status-badge ${statusBadgeClass}">${statusText}</span>
                    <h3 class="card-title">${election.electionName}</h3>
                </div>
                <div class="card-body">
                    <p class="card-description">${election.electionDescription}</p>
                    <div class="card-dates">
                        <div class="date-item">
                            <i class="fas fa-calendar-alt"></i>
                            <span>Starts: ${startDate}</span>
                        </div>
                        <div class="date-item">
                            <i class="fas fa-calendar-check"></i>
                            <span>Ends: ${endDate}</span>
                        </div>
                    </div>
                </div>
                <div class="card-footer">
                    <div class="participants">
                        <i class="fas fa-users"></i>
                        <span>${election.users} participants</span>
                    </div>
                    ${actionButton}
                </div>
            `;

    return card;
}

// Set up filter buttons
function setupFilterButtons() {
    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Update active button
            filterButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');

            // Filter elections
            const status = button.dataset.status;
            filterElections(status);
        });
    });
}

// Filter elections by status
function filterElections(status) {
    if (!window.electionData) return;

    if (status === 'all') {
        renderElections(window.electionData);
        return;
    }

    const filteredElections = window.electionData.filter(election =>
        election.electionStatus === status
    );

    renderElections(filteredElections);
}