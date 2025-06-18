document.addEventListener('DOMContentLoaded', function() {
    // Initialize the dashboard with default view
    initDashboard();

    // Notification system
    setupNotifications();

    // Tab functionality for all tabbed interfaces
    setupTabs();

    // Filter tag functionality
    setupFilterTags();

    // Pagination controls
    setupPagination();

    // Table interactions
    setupTableInteractions();

    // Form buttons and modals
    setupFormButtons();

    // Simulate data loading
    simulateDataLoading();

    // Initialize elections page
    fetchElections(); // Fetch elections from backend when page loads
    setupFilterButtons();
});

function initDashboard() {
    // Get all navigation links
    const navLinks = document.querySelectorAll('.nav-link');

    // Set first nav item as active by default
    navLinks[0].classList.add('active');

    // Add click event listeners to each link
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();

            // Remove active class from all links and sections
            navLinks.forEach(navLink => navLink.classList.remove('active'));
            document.querySelectorAll('.content-section').forEach(section => {
                section.classList.remove('active');
            });

            // Add active class to clicked link
            this.classList.add('active');

            // Show the corresponding section
            const target = this.getAttribute('href');
            const contentId = target.substring(1) + '-content';
            const contentElement = document.getElementById(contentId);

            if (contentElement) {
                contentElement.classList.add('active');
                // Dispatch custom event when section changes
                document.dispatchEvent(new CustomEvent('sectionChanged', {
                    detail: { sectionId: contentId }
                }));
            }
        });
    });

    // Initialize with dashboard showing
    document.getElementById('dashboard-content').classList.add('active');
}

function setupNotifications() {
    const notificationBell = document.querySelector('.notification-bell');
    if (notificationBell) {
        notificationBell.addEventListener('click', function() {
            const notificationCount = this.querySelector('.notification-count');
            if (notificationCount) {
                notificationCount.textContent = '0';
                notificationCount.style.backgroundColor = '#10b981';
                // Show notification panel (would be implemented in a real app)
                showNotificationPanel();
            }
        });
    }
}

function showNotificationPanel() {
    // In a real implementation, this would show a dropdown with notifications
    console.log('Notification panel would appear here');
}

function setupTabs() {
    // Main tab functionality
    document.querySelectorAll('.tabs').forEach(tabContainer => {
        const tabBtns = tabContainer.querySelectorAll('.tab-btn');
        const tabContents = tabContainer.parentElement.querySelectorAll('.tab-content');

        tabBtns.forEach(btn => {
            btn.addEventListener('click', () => {
                const tabId = btn.getAttribute('data-tab');

                // Update active tab button
                tabBtns.forEach(b => b.classList.remove('active'));
                btn.classList.add('active');

                // Show corresponding content
                tabContents.forEach(content => {
                    content.classList.remove('active');
                    if (content.id === `${tabId}-tab`) {
                        content.classList.add('active');
                    }
                });
            });
        });
    });

    // Demographic tabs
    const demoTabs = document.querySelectorAll('.demo-tab');
    const demoContents = document.querySelectorAll('.demographic-content');

    demoTabs.forEach(tab => {
        tab.addEventListener('click', () => {
            const tabId = tab.getAttribute('data-tab');

            // Update active tab button
            demoTabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');

            // Show corresponding content
            demoContents.forEach(content => {
                content.classList.remove('active');
                if (content.id === `${tabId}-tab`) {
                    content.classList.add('active');
                }
            });
        });
    });

    // Time filter tabs
    const timeBtns = document.querySelectorAll('.time-btn');
    timeBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            timeBtns.forEach(b => b.classList.remove('active'));
            this.classList.add('active');
            // In a real app, this would update the chart data
            updateTimeFilter(this.textContent.trim());
        });
    });
}

function updateTimeFilter(timeRange) {
    console.log(`Time filter updated to: ${timeRange}`);
    // This would update chart data in a real implementation
}

function setupFilterTags() {
    const filterTags = document.querySelectorAll('.filter-tag');
    filterTags.forEach(tag => {
        tag.addEventListener('click', function(e) {
            const isActive = this.classList.contains('active');
            const closeIcon = this.querySelector('i.fa-times');

            if (closeIcon && e.target.closest('i')) {
                // If clicking the close icon, remove the tag
                this.remove();
                updateFilters(); // Would update filtered results in real app
            } else if (!isActive) {
                // Otherwise toggle active state
                if (this.parentElement.classList.contains('filter-tags')) {
                    // For filter tags that should be single-select
                    filterTags.forEach(t => t.classList.remove('active'));
                }
                this.classList.add('active');
                updateFilters(); // Would update filtered results in real app
            }
        });
    });
}

function updateFilters() {
    console.log('Filters updated - would refresh data in real app');
}

function setupPagination() {
    document.querySelectorAll('.pagination').forEach(pagination => {
        const pageBtns = pagination.querySelectorAll('.page-btn:not(.disabled)');

        pageBtns.forEach(btn => {
            btn.addEventListener('click', function() {
                if (!this.classList.contains('active') && !this.classList.contains('disabled')) {
                    pagination.querySelector('.page-btn.active')?.classList.remove('active');
                    this.classList.add('active');
                    // In a real app, this would load the new page of data
                    loadPage(this.textContent.trim());
                }
            });
        });
    });
}

function loadPage(pageNumber) {
    console.log(`Loading page ${pageNumber}`);
    // This would fetch and display the requested page in a real implementation
}

function setupTableInteractions() {
    // Select all checkbox
    const selectAllCheckbox = document.getElementById('select-all');
    if (selectAllCheckbox) {
        selectAllCheckbox.addEventListener('change', function() {
            const checkboxes = document.querySelectorAll('.voters-table tbody input[type="checkbox"]');
            checkboxes.forEach(checkbox => {
                checkbox.checked = this.checked;
            });
        });
    }

    // Table row click handlers
    document.querySelectorAll('.voters-table tbody tr').forEach(row => {
        row.addEventListener('click', function(event) {
            if (!event.target.closest('.icon-btn') && !event.target.closest('input[type="checkbox"]')) {
                const voterId = this.querySelector('.details .id')?.textContent || 'unknown';
                showVoterDetails(voterId);
            }
        });
    });

    document.querySelectorAll('.results-table tbody tr').forEach(row => {
        row.addEventListener('click', function(event) {
            if (!event.target.closest('button')) {
                const candidateName = this.querySelector('.details .name')?.textContent || 'unknown';
                showCandidateDetails(candidateName);
            }
        });
    });
}

function showVoterDetails(voterId) {
    console.log(`Showing details for voter: ${voterId}`);
    // In a real app, this would open a modal or navigate to a details page
    alert(`Voter details would open for ID: ${voterId}`);
}

function showCandidateDetails(candidateName) {
    console.log(`Showing details for candidate: ${candidateName}`);
    // In a real app, this would open a modal or navigate to a details page
    alert(`Candidate details would open for: ${candidateName}`);
}

function setupFormButtons() {
    // Create election button
    const createElectionBtn = document.getElementById('create-election');
    if (createElectionBtn) {
        createElectionBtn.addEventListener('click', () => {
            showFormModal('create-election-form');
        });
    }

    // Add candidate button
    const addCandidateBtn = document.getElementById('add-candidate');
    if (addCandidateBtn) {
        addCandidateBtn.addEventListener('click', () => {
            showFormModal('add-candidate-form');
        });
    }

    // Add voter button
    const addVoterBtn = document.getElementById('add-voter');
    if (addVoterBtn) {
        addVoterBtn.addEventListener('click', () => {
            showFormModal('add-voter-form');
        });
    }

    // Share results button
    const shareResultsBtn = document.getElementById('share-results');
    if (shareResultsBtn) {
        shareResultsBtn.addEventListener('click', () => {
            showShareDialog();
        });
    }
}

function showFormModal(formType) {
    console.log(`Showing form for: ${formType}`);
    // In a real app, this would open a modal with the appropriate form
    alert(`${formType.replace('-', ' ')} would open here`);
}

function showShareDialog() {
    console.log('Opening share dialog');
    // In a real app, this would show sharing options
    alert('Share dialog would open here with options to share via email, social media, etc.');
}

function simulateDataLoading() {
    // Simulate loading data after a delay
    setTimeout(() => {
        const voterCount = document.querySelector('.dashboard-cards .card:nth-child(1) .card-value');
        if (voterCount) {
            voterCount.textContent = '2,463';
        }

        const changeElement = document.querySelector('.dashboard-cards .card:nth-child(1) .card-change');
        if (changeElement) {
            changeElement.innerHTML = '<i class="fas fa-arrow-up"></i> 14% from last month';
        }

        // Animate bars on load
        document.querySelectorAll('.bar').forEach(bar => {
            bar.style.height = '0';
            setTimeout(() => {
                bar.style.height = '';
            }, 100);
        });
    }, 1500);
}

// Chart initialization would go here in a real implementation
function initCharts() {
    // This would initialize Chart.js or other charting library
    console.log('Initializing charts...');
}

// Initialize charts when results section becomes active
document.addEventListener('sectionChanged', function(e) {
    if (e.detail.sectionId === 'results-content') {
        initCharts();
    }
});

// DOM elements
const electionsContainer = document.getElementById('electionsContainer');
const filterButtons = document.querySelectorAll('.filter-btn');

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