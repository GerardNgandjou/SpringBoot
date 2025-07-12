// ===== CONFIGURATION =====
const DEFAULT_ERROR_MSG = 'An error occurred. Please try again.';
const API_ROUTES = {
    elections: '/election/show',
    candidates: '/disp_cand',
    voters: '/disp_voter',
    voteoffice: '/disp_vote_office',
    results: '/election/results'
};

// ===== UTILITY FUNCTIONS =====
function showToast(message, type = 'success') {
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.setAttribute('role', 'alert');
    toast.innerHTML = `
        <div class="toast-message">${message}</div>
        <button class="toast-close" aria-label="Close">&times;</button>
    `;
    
    document.body.appendChild(toast);
    setTimeout(() => toast.classList.add('show'), 100);
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 300);
    }, 5000);
    
    toast.querySelector('.toast-close').addEventListener('click', () => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 300);
    });
}

function showLoading(containerId) {
    const container = document.getElementById(containerId);
    if (container) {
        container.innerHTML = `
            <div class="loading-spinner" aria-live="polite">
                <div class="spinner-border" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        `;
    }
}

function showErrorMessage(containerId, message = DEFAULT_ERROR_MSG) {
    const container = document.getElementById(containerId);
    if (container) {
        container.innerHTML = `
            <div class="error-message" role="alert">
                <i class="fas fa-exclamation-triangle"></i>
                <p>${message}</p>
                <button class="btn btn-retry" onclick="retryLastFetch('${containerId}')">
                    <i class="fas fa-sync-alt"></i> Retry
                </button>
            </div>
        `;
    }
}

// ===== CORE FUNCTIONALITY =====
class ElectionDashboard {
    constructor() {
        this.currentSection = 'dashboard';
        this.currentElectionId = null;
        this.init();
    }

    init() {
        this.setupNavigation();
        this.setupEventListeners();
        this.loadInitialSection();
    }

    setupNavigation() {
        // Handle both hash-based and Thymeleaf navigation
        document.querySelectorAll('.nav-link').forEach(link => {
            link.addEventListener('click', (e) => {
                if (link.hasAttribute('th:href')) {
                    // Thymeleaf links will handle their own navigation
                    return;
                }
                
                e.preventDefault();
                const section = link.getAttribute('href').substring(1);
                this.navigateToSection(section);
            });
        });

        window.addEventListener('hashchange', () => {
            const section = window.location.hash.substring(1);
            if (section) this.navigateToSection(section);
        });
    }

    setupEventListeners() {
        // Filter inputs
        document.querySelectorAll('.filter-input').forEach(input => {
            input.addEventListener('input', () => this.applyFilters());
        });

        // Dynamic element event delegation
        document.addEventListener('click', (e) => {
            // Handle election registration
            if (e.target.closest('.register-election')) {
                const electionId = e.target.closest('.register-election').dataset.electionId;
                this.registerForElection(electionId);
            }
            
            // Handle candidate actions
            if (e.target.closest('.view-candidate')) {
                const candidateId = e.target.closest('.view-candidate').dataset.candidateId;
                this.viewCandidateDetails(candidateId);
            }
        });
    }

    navigateToSection(section) {
        // Update active nav link
        document.querySelectorAll('.nav-link').forEach(link => {
            link.classList.remove('active');
            link.removeAttribute('aria-current');
        });
        
        const activeLink = document.querySelector(`.nav-link[href="#${section}"], .nav-link[data-section="${section}"]`);
        if (activeLink) {
            activeLink.classList.add('active');
            activeLink.setAttribute('aria-current', 'page');
        }

        // Hide all sections
        document.querySelectorAll('.content-section').forEach(sec => {
            sec.classList.remove('active');
        });
        
        // Show target section
        const targetSection = document.getElementById(`${section}-content`);
        if (targetSection) {
            targetSection.classList.add('active');
            this.currentSection = section;
            
            // Load data if not already loaded
            if (!targetSection.dataset.loaded) {
                this.loadSectionData(section);
                targetSection.dataset.loaded = 'true';
            }
        }
    }

    loadSectionData(section) {
        switch(section) {
            case 'dashboard':
                this.loadDashboardStats();
                break;
            case 'elections':
                this.fetchElections();
                break;
            case 'candidates':
                this.fetchCandidates();
                break;
            case 'voteoffice':
                this.fetchVoteOffices();
                break;
            case 'results':
                if (this.currentElectionId) {
                    this.loadElectionResults(this.currentElectionId);
                }
                break;
        }
    }

    loadInitialSection() {
        const hash = window.location.hash.substring(1);
        const initialSection = hash || 'dashboard';
        this.navigateToSection(initialSection);
    }

    // ===== DATA FETCHING METHODS =====
    async fetchElections(filters = {}) {
        try {
            showLoading('electionsContainer');
            const response = await fetch(API_ROUTES.elections);
            
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            
            const elections = await response.json();
            this.renderElections(elections);
        } catch (error) {
            console.error('Fetch elections error:', error);
            showErrorMessage('electionsContainer');
        }
    }

    async fetchCandidates(filters = {}) {
        try {
            showLoading('candidateGrid');
            const response = await fetch(API_ROUTES.candidates);
            
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            
            const candidates = await response.json();
            this.renderCandidates(candidates);
        } catch (error) {
            console.error('Fetch candidates error:', error);
            showErrorMessage('candidateGrid', 'Failed to load candidates');
        }
    }

    async fetchVoteOffices(filters = {}) {
        try {
            showLoading('voteOfficeContainer');
            const response = await fetch(API_ROUTES.voteoffice);
            
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            
            const offices = await response.json();
            this.renderVoteOffices(offices);
        } catch (error) {
            console.error('Fetch vote offices error:', error);
            showErrorMessage('voteOfficeContainer', 'Failed to load vote offices');
        }
    }

    async loadElectionResults(electionId) {
        try {
            showLoading('resultsContainer');
            this.currentElectionId = electionId;
            const response = await fetch(`${API_ROUTES.results}/${electionId}`);
            
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            
            const results = await response.json();
            this.renderElectionResults(results);
        } catch (error) {
            console.error('Fetch results error:', error);
            showErrorMessage('resultsContainer', 'Failed to load election results');
        }
    }

    // ===== RENDER METHODS =====
    renderElections(elections) {
        const container = document.getElementById('electionsContainer');
        if (!elections || !elections.length) {
            container.innerHTML = `
                <div class="no-data">
                    <i class="fas fa-info-circle"></i>
                    <p>No elections found matching your criteria.</p>
                </div>
            `;
            return;
        }

        container.innerHTML = elections.map(election => `
            <div class="election-card">
                <div class="card-header">
                    <span class="status-badge ${this.getStatusClass(election.electionStatus)}">
                        ${election.electionStatus || 'UNKNOWN'}
                    </span>
                    <h3 class="card-title">${election.electionName || 'Unnamed Election'}</h3>
                </div>
                <div class="card-body">
                    <p class="card-description">${election.electionDescription || 'No description available'}</p>
                    <div class="card-dates">
                        <div class="date-item">
                            <i class="fas fa-calendar-alt"></i>
                            <span>Starts: ${this.formatDate(election.electionStartDate)}</span>
                        </div>
                        <div class="date-item">
                            <i class="fas fa-calendar-check"></i>
                            <span>Ends: ${this.formatDate(election.electionEndDate)}</span>
                        </div>
                    </div>
                </div>
                <div class="card-footer">
                    <div class="participants">
                        <i class="fas fa-users"></i>
                        <span>${election.users?.length || 0} participants</span>
                    </div>
                    ${this.createActionButton(election)}
                </div>
            </div>
        `).join('');
    }

    renderCandidates(candidates) {
        const container = document.getElementById('candidateGrid');
        if (!candidates || !candidates.length) {
            container.innerHTML = `
                <div class="no-data">
                    <i class="fas fa-user-slash fa-3x mb-3"></i>
                    <h3>No candidates found</h3>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addCandidateModal">
                        <i class="fas fa-plus"></i> Add Candidate
                    </button>
                </div>
            `;
            return;
        }

        container.innerHTML = candidates.map(candidate => `
            <div class="col">
                <div class="candidate-card">
                    <div class="candidate-header">
                        <img src="${candidate.photoUrl || '/images/default-candidate.jpg'}" 
                             alt="${candidate.firstName} ${candidate.lastName}" 
                             class="candidate-photo">
                    </div>
                    <div class="candidate-body">
                        <h3 class="candidate-name">${candidate.firstName} ${candidate.lastName}</h3>
                        <span class="candidate-party">${candidate.party || 'Independent'}</span>
                        <div class="candidate-meta">
                            <span><i class="fas fa-vote-yea"></i> ${candidate.votes || 0}</span>
                            <span><i class="fas fa-map-marker-alt"></i> ${candidate.region}</span>
                        </div>
                        <p class="candidate-bio">${candidate.biography || 'No biography provided'}</p>
                    </div>
                    <div class="candidate-footer">
                        <span class="candidate-status ${candidate.status.toLowerCase()}">
                            <i class="fas fa-circle"></i> ${candidate.status}
                        </span>
                        <div class="candidate-actions">
                            <button class="btn btn-sm btn-outline view-candidate" data-candidate-id="${candidate.id}">
                                <i class="fas fa-eye"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `).join('');
    }

    renderVoteOffices(offices) {
        const container = document.getElementById('voteOfficeContainer');
        if (!offices || !offices.length) {
            container.innerHTML = `
                <div class="empty-state">
                    <i class="fas fa-building fa-3x mb-3"></i>
                    <h3>No vote offices found</h3>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addOfficeModal">
                        <i class="fas fa-plus"></i> Add Office
                    </button>
                </div>
            `;
            return;
        }

        container.innerHTML = offices.map(office => `
            <div class="office-card">
                <div class="office-card-header">
                    <h3>${office.name}</h3>
                    <span class="office-region">${office.region}</span>
                </div>
                <div class="office-card-body">
                    <div class="office-info-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <div>
                            <h4>Address</h4>
                            <p>${office.address}</p>
                        </div>
                    </div>
                    <div class="office-info-item">
                        <i class="fas fa-phone"></i>
                        <div>
                            <h4>Phone</h4>
                            <p>${office.phone || 'Not provided'}</p>
                        </div>
                    </div>
                </div>
                <div class="office-card-footer">
                    <button class="btn btn-sm btn-primary view-office" data-office-id="${office.id}">
                        <i class="fas fa-map-marked-alt"></i> View Map
                    </button>
                </div>
            </div>
        `).join('');
    }

    renderElectionResults(results) {
        const container = document.getElementById('resultsContainer');
        if (!results) {
            showErrorMessage('resultsContainer');
            return;
        }

        // Update summary cards
        document.querySelector('.summary-value').textContent = results.totalVotes.toLocaleString();
        document.querySelector('.summary-meta').textContent = `${results.participationRate}% participation`;
        
        // Render chart
        this.renderResultsChart(results.chartData);
    }

    // ===== HELPER METHODS =====
    getStatusClass(status) {
        if (!status) return 'status-unknown';
        switch(status.toUpperCase()) {
            case 'ACTIVE': return 'status-active';
            case 'UPCOMING': return 'status-upcoming';
            case 'SCHEDULED': return 'status-upcoming';
            case 'ENDED': return 'status-ended';
            default: return 'status-unknown';
        }
    }

    formatDate(dateString) {
        if (!dateString) return 'N/A';
        try {
            return new Date(dateString).toLocaleDateString();
        } catch {
            return 'Invalid date';
        }
    }

    createActionButton(election) {
        const status = election.electionStatus?.toUpperCase();
        const isUpcoming = status === 'UPCOMING' || status === 'SCHEDULED';
        const id = election.idElection || election.id;
        
        return isUpcoming ?
            `<button class="btn btn-outline register-election" data-election-id="${id}">
                <i class="fas fa-user-plus"></i> Register
            </button>` :
            `<a href="#results" class="btn btn-primary" onclick="dashboard.loadElectionResults('${id}')">
                <i class="fas fa-eye"></i> View Results
            </a>`;
    }

    renderResultsChart(chartData) {
        const ctx = document.getElementById('resultsChart').getContext('2d');
        
        if (window.resultsChart) {
            window.resultsChart.destroy();
        }
        
        window.resultsChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: chartData.labels,
                datasets: [{
                    data: chartData.data,
                    backgroundColor: chartData.colors,
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'right',
                    }
                },
                cutout: '70%'
            }
        });
    }

    // ===== ACTION METHODS =====
    async registerForElection(electionId) {
        try {
            const response = await fetch(`/election/${electionId}/register`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('authToken')}`
                }
            });
            
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            
            showToast('Registration successful!', 'success');
            this.fetchElections();
        } catch (error) {
            console.error('Registration error:', error);
            showToast('Failed to register for election', 'error');
        }
    }

    async viewCandidateDetails(candidateId) {
        try {
            const response = await fetch(`/candidate/${candidateId}`);
            
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            
            const candidate = await response.json();
            this.showCandidateModal(candidate);
        } catch (error) {
            console.error('Fetch candidate error:', error);
            showToast('Failed to load candidate details', 'error');
        }
    }

    showCandidateModal(candidate) {
        // Implementation would show a modal with candidate details
        console.log('Showing candidate:', candidate);
    }

    applyFilters() {
        const filters = {
            search: document.getElementById('searchInput')?.value,
            status: document.getElementById('statusFilter')?.value,
            region: document.getElementById('regionFilter')?.value
        };

        switch(this.currentSection) {
            case 'elections':
                this.fetchElections(filters);
                break;
            case 'candidates':
                this.fetchCandidates(filters);
                break;
            case 'voteoffice':
                this.fetchVoteOffices(filters);
                break;
        }
    }
}

// Initialize the dashboard when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    window.dashboard = new ElectionDashboard();
});