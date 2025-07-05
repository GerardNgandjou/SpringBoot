document.addEventListener('DOMContentLoaded', function() {
    // ======================
    // Global Variables
    // ======================
    const sidebar = document.querySelector('.sidebar');
    const sidebarToggle = document.querySelector('.sidebar-toggle');
    const navLinks = document.querySelectorAll('.nav-link');
    const contentSections = document.querySelectorAll('.content-section');
    const notificationBell = document.querySelector('.notification-bell');
    const notificationDropdown = document.querySelector('.notification-dropdown');
    const profileDropdownBtn = document.querySelector('.profile-dropdown');
    const profileDropdownMenu = document.querySelector('.dropdown-menu');
    const logoutBtn = document.getElementById('logout-btn');
    const logoutDropdownBtn = document.getElementById('logout-dropdown-btn');
    const logoutModal = document.getElementById('logoutModal');
    const confirmLogoutBtn = document.getElementById('confirmLogout');
    const cancelLogoutBtn = document.querySelector('.modal-cancel');
    const createElectionBtn = document.getElementById('createElectionBtn');
    const createElectionModal = document.getElementById('createElectionModal');
    const closeModalBtns = document.querySelectorAll('.modal-close, .modal-cancel');
    const filterBtns = document.querySelectorAll('.filter-btn');
    const addCandidateBtn = document.querySelector('[data-bs-target="#addCandidateModal"]');
    const addOfficeBtn = document.getElementById('addOfficeBtn');
    const applyFiltersBtn = document.getElementById('applyFilters');
    const searchInputs = document.querySelectorAll('input[type="search"], #searchInput');
    const electionSelect = document.querySelector('.election-select');
    const settingsTabs = document.querySelectorAll('.settings-tab');
    const settingsTabContents = document.querySelectorAll('.settings-tab-content');
    const toggleSwitch = document.getElementById('2fa-toggle');

    // ======================
    // Helper Functions
    // ======================
    function debounce(func, wait) {
        let timeout;
        return function() {
            const context = this, args = arguments;
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(context, args), wait);
        };
    }

    function showSection(sectionId) {
        contentSections.forEach(section => {
            section.classList.remove('active');
        });
        document.getElementById(`${sectionId}-content`).classList.add('active');
        
        // Update URL hash
        window.location.hash = sectionId;
        
        // Update active nav link
        navLinks.forEach(link => {
            link.classList.remove('active');
            if (link.getAttribute('data-section') === sectionId) {
                link.classList.add('active');
            }
        });
        
        // Initialize charts when results section is shown
        if (sectionId === 'results') {
            initResultsChart();
        }
    }

    function closeAllModals() {
        document.querySelectorAll('.modal').forEach(modal => {
            modal.classList.remove('active');
        });
    }

    function toggleDropdown(dropdown) {
        const isOpen = dropdown.style.display === 'block';
        document.querySelectorAll('.dropdown-menu, .notification-dropdown').forEach(d => {
            d.style.display = 'none';
        });
        dropdown.style.display = isOpen ? 'none' : 'block';
    }

    // ======================
    // Event Listeners
    // ======================
    // Sidebar toggle for mobile
    sidebarToggle.addEventListener('click', function() {
        sidebar.classList.toggle('active');
        this.setAttribute('aria-expanded', sidebar.classList.contains('active'));
    });

    // Navigation between sections
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const section = this.getAttribute('data-section');
            showSection(section);
            
            // Close sidebar on mobile after navigation
            if (window.innerWidth < 768) {
                sidebar.classList.remove('active');
                sidebarToggle.setAttribute('aria-expanded', 'false');
            }
        });
    });

    // Notification dropdown
    notificationBell.addEventListener('click', function(e) {
        e.stopPropagation();
        toggleDropdown(notificationDropdown);
    });

    // Profile dropdown
    profileDropdownBtn.addEventListener('click', function(e) {
        e.stopPropagation();
        toggleDropdown(profileDropdownMenu);
    });

    // Logout buttons
    [logoutBtn, logoutDropdownBtn].forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            logoutModal.classList.add('active');
        });
    });

    // Confirm logout
    confirmLogoutBtn.addEventListener('click', function() {
        // In a real app, you would handle logout logic here
        console.log('User logged out');
        window.location.href = 'login.html'; // Redirect to login page
    });

    // Cancel logout
    cancelLogoutBtn.addEventListener('click', function() {
        logoutModal.classList.remove('active');
    });

    // Close modals when clicking outside or on close button
    closeModalBtns.forEach(btn => {
        btn.addEventListener('click', closeAllModals);
    });

    window.addEventListener('click', function(e) {
        // Close dropdowns when clicking outside
        if (!e.target.closest('.notification-bell') && notificationDropdown.style.display === 'block') {
            notificationDropdown.style.display = 'none';
        }
        
        if (!e.target.closest('.profile-dropdown') && profileDropdownMenu.style.display === 'block') {
            profileDropdownMenu.style.display = 'none';
        }
        
        // Close modals when clicking on backdrop
        if (e.target.classList.contains('modal')) {
            closeAllModals();
        }
    });

    // Filter buttons in elections section
    filterBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            filterBtns.forEach(b => b.classList.remove('active'));
            this.classList.add('active');
            const status = this.getAttribute('data-status');
            filterElections(status);
        });
    });

    // Search functionality with debounce
    searchInputs.forEach(input => {
        input.addEventListener('input', debounce(function() {
            const searchTerm = this.value.toLowerCase();
            const containerId = this.closest('.container') ? 'electionsContainer' : 
                              this.closest('.app-main') ? 'candidatesTable' : 
                              this.closest('.controls') ? 'officesContainer' : 
                              'votersTableBody';
            
            const items = document.querySelectorAll(`#${containerId} .election-card, #${containerId} tr`);
            
            items.forEach(item => {
                const text = item.textContent.toLowerCase();
                item.style.display = text.includes(searchTerm) ? '' : 'none';
            });
        }, 300));
    });

    // Election select change
    if (electionSelect) {
        electionSelect.addEventListener('change', function() {
            // In a real app, you would fetch results for the selected election
            console.log('Selected election:', this.value);
            updateResults(this.value);
        });
    }

    // Settings tabs
    settingsTabs.forEach(tab => {
        tab.addEventListener('click', function() {
            const tabName = this.getAttribute('data-tab');
            
            settingsTabs.forEach(t => t.classList.remove('active'));
            settingsTabContents.forEach(c => c.classList.remove('active'));
            
            this.classList.add('active');
            document.querySelector(`.settings-tab-content[data-tab="${tabName}"]`).classList.add('active');
        });
    });

    // 2FA toggle switch
    if (toggleSwitch) {
        toggleSwitch.addEventListener('change', function() {
            console.log('2FA status:', this.checked ? 'Enabled' : 'Disabled');
            // In a real app, you would update the user's 2FA setting via API
        });
    }

    // Apply filters in voters section
    if (applyFiltersBtn) {
        applyFiltersBtn.addEventListener('click', function() {
            const region = document.getElementById('regionFilter').value;
            const party = document.getElementById('partyFilter').value;
            const gender = document.getElementById('genderFilter').value;
            
            // In a real app, you would filter voters based on these criteria
            console.log('Applying filters:', { region, party, gender });
            filterVoters(region, party, gender);
        });
    }

    // ======================
    // Initialization Functions
    // ======================
    function initDashboard() {
        // Show section based on URL hash or default to dashboard
        const hash = window.location.hash.substring(1);
        const validSections = ['dashboard', 'elections', 'candidates', 'voters', 'voteoffice', 'results', 'settings'];
        
        if (hash && validSections.includes(hash)) {
            showSection(hash);
        } else {
            showSection('dashboard');
        }
        
        // Load initial data
        loadElections();
        loadCandidates();
        loadVoters();
        loadVoteOffices();
    }

    function loadElections() {
        // In a real app, you would fetch this data from an API
        const elections = [
            {
                id: 1,
                title: "Student Council Election 2024",
                description: "Annual election for student council positions",
                startDate: "2024-05-15",
                endDate: "2024-05-30",
                status: "active",
                candidates: 4,
                voters: 1245,
                verified: 782,
                pending: 463,
                participation: 62,
                bannerColor: "linear-gradient(135deg, #4361ee, #4895ef)"
            },
            {
                id: 2,
                title: "HOA Board Election",
                description: "Homeowners association board member election",
                startDate: "2024-05-25",
                endDate: "2024-05-31",
                status: "active",
                candidates: 5,
                voters: 892,
                verified: 512,
                pending: 380,
                participation: 45,
                bannerColor: "linear-gradient(135deg, #f72585, #b5179e)"
            },
            {
                id: 3,
                title: "Faculty Senate Election (2023)",
                description: "Election for faculty senate representatives",
                startDate: "2023-11-01",
                endDate: "2023-11-15",
                status: "ended",
                candidates: 8,
                voters: 1560,
                verified: 1450,
                pending: 110,
                participation: 93,
                bannerColor: "linear-gradient(135deg, #3f37c9, #560bad)"
            }
        ];

        const container = document.getElementById('electionsContainer');
        container.innerHTML = '';

        elections.forEach(election => {
            const daysLeft = election.status === 'active' ? 
                Math.ceil((new Date(election.endDate) - new Date()) / (1000 * 60 * 60 * 24)) : 
                null;

            const electionCard = document.createElement('div');
            electionCard.className = 'election-card';
            electionCard.innerHTML = `
                <div class="election-banner" style="background: ${election.bannerColor}">
                    <i class="fas fa-vote-yea"></i>
                    <span class="election-status ${election.status}">
                        ${election.status === 'active' ? `Active • ${daysLeft} days left` : 
                          election.status === 'coming' ? 'Coming Soon' : 'Ended'}
                    </span>
                </div>
                <div class="election-details">
                    <h3 class="election-title">${election.title}</h3>
                    <div class="election-meta">
                        <div class="election-date">
                            <i class="far fa-calendar-alt"></i> 
                            ${formatDate(election.startDate)} - ${formatDate(election.endDate)}
                        </div>
                        <div><i class="fas fa-users"></i> ${election.candidates} candidates</div>
                    </div>
                    <div class="progress-container">
                        <div class="progress-label">
                            <span>Participation (${election.voters} voters)</span>
                            <span>${election.participation}%</span>
                        </div>
                        <div class="progress-bar">
                            <div class="progress-fill" style="width: ${election.participation}%"></div>
                        </div>
                    </div>
                    <div class="election-stats">
                        <div class="stat-item">
                            <i class="fas fa-check-circle"></i>
                            <span>${election.verified} Verified</span>
                        </div>
                        <div class="stat-item">
                            <i class="fas fa-user-clock"></i>
                            <span>${election.pending} Pending</span>
                        </div>
                    </div>
                    <div class="election-actions">
                        <button class="btn btn-outline">
                            <i class="fas fa-chart-bar"></i> Results
                        </button>
                        <button class="btn btn-primary">
                            <i class="fas fa-cog"></i> Manage
                        </button>
                    </div>
                </div>
            `;
            container.appendChild(electionCard);
        });
    }

    function loadCandidates() {
        // In a real app, you would fetch this data from an API
        const candidates = [
            {
                id: 1,
                firstname: "Michael",
                lastname: "Chen",
                birthdate: "1990-05-15",
                gender: "Male",
                party: "Progressive",
                role: "President",
                region: "North",
                location: "Capital City"
            },
            {
                id: 2,
                firstname: "Sarah",
                lastname: "Williams",
                birthdate: "1985-08-22",
                gender: "Female",
                party: "Unity",
                role: "Vice President",
                region: "East",
                location: "Coastal Town"
            },
            {
                id: 3,
                firstname: "James",
                lastname: "Wilson",
                birthdate: "1978-11-30",
                gender: "Male",
                party: "Progressive",
                role: "Secretary",
                region: "West",
                location: "Mountain View"
            }
        ];

        const tbody = document.querySelector('#candidatesTable tbody');
        tbody.innerHTML = '';

        candidates.forEach(candidate => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>
                    <div class="d-flex align-items-center">
                        <div class="avatar me-3">
                            ${candidate.firstname.charAt(0)}${candidate.lastname.charAt(0)}
                        </div>
                        <div>
                            <div class="fw-bold">${candidate.firstname} ${candidate.lastname}</div>
                            <div class="text-muted small">${candidate.role}</div>
                        </div>
                    </div>
                </td>
                <td>
                    <div>${formatDate(candidate.birthdate, 'dd MMM yyyy')}</div>
                    <div class="text-muted small">${candidate.gender}</div>
                </td>
                <td>${candidate.location}</td>
                <td>${candidate.region}</td>
                <td>
                    <span class="badge bg-primary">${candidate.party}</span>
                </td>
                <td>
                    <div class="btn-group btn-group-sm">
                        <button class="btn btn-outline-primary" title="View">
                            <i class="fas fa-eye"></i>
                        </button>
                        <button class="btn btn-outline-secondary" title="Edit">
                            <i class="fas fa-pencil-alt"></i>
                        </button>
                        <button class="btn btn-outline-danger" title="Delete">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                </td>
            `;
            tbody.appendChild(row);
        });
    }

    function loadVoters() {
        // In a real app, you would fetch this data from an API
        const voters = [
            {
                id: 1,
                firstname: "Alex",
                lastname: "Johnson",
                birthdate: "1982-03-12",
                gender: "Male",
                location: "Capital City",
                region: "North",
                party: "Progressive",
                role: "Member",
                status: "active"
            },
            {
                id: 2,
                firstname: "Maria",
                lastname: "Garcia",
                birthdate: "1991-07-25",
                gender: "Female",
                location: "Coastal Town",
                region: "East",
                party: "Unity",
                role: "Volunteer",
                status: "active"
            },
            {
                id: 3,
                firstname: "David",
                lastname: "Kim",
                birthdate: "1975-11-08",
                gender: "Male",
                location: "Mountain View",
                region: "West",
                party: "Independent",
                role: "Observer",
                status: "inactive"
            }
        ];

        const tbody = document.getElementById('votersTableBody');
        tbody.innerHTML = '';

        voters.forEach(voter => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${voter.firstname} ${voter.lastname}</td>
                <td>${formatDate(voter.birthdate)}</td>
                <td>${voter.gender}</td>
                <td>${voter.location}</td>
                <td>${voter.region}</td>
                <td>${voter.party}</td>
                <td>${voter.role}</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary view-voter" data-id="${voter.id}">
                        <i class="fas fa-eye"></i> View
                    </button>
                </td>
            `;
            tbody.appendChild(row);
        });

        // Add event listeners to view buttons
        document.querySelectorAll('.view-voter').forEach(btn => {
            btn.addEventListener('click', function() {
                const voterId = this.getAttribute('data-id');
                showVoterDetails(voterId);
            });
        });
    }

    function loadVoteOffices() {
        // In a real app, you would fetch this data from an API
        const offices = [
            {
                id: 1,
                name: "Main Election Office",
                location: "123 Democracy Ave, Capital City",
                manager: "John Smith",
                contact: "office@votenow.org"
            },
            {
                id: 2,
                name: "North Region Office",
                location: "456 Freedom St, North Town",
                manager: "Lisa Brown",
                contact: "north@votenow.org"
            },
            {
                id: 3,
                name: "East Coastal Office",
                location: "789 Liberty Blvd, Coastal City",
                manager: "Robert Lee",
                contact: "east@votenow.org"
            }
        ];

        const container = document.getElementById('officesContainer');
        container.innerHTML = '';

        offices.forEach(office => {
            const officeCard = document.createElement('div');
            officeCard.className = 'office-card';
            officeCard.innerHTML = `
                <h3 class="office-name">${office.name}</h3>
                <div class="office-location">
                    <i class="fas fa-map-marker-alt"></i>
                    ${office.location}
                </div>
                <div class="office-staff">
                    <p><strong>Manager:</strong> ${office.manager}</p>
                    <p><strong>Contact:</strong> ${office.contact}</p>
                </div>
                <div class="office-actions">
                    <button class="btn btn-outline btn-sm">
                        <i class="fas fa-edit"></i> Edit
                    </button>
                    <button class="btn btn-outline btn-sm">
                        <i class="fas fa-users"></i> Staff
                    </button>
                </div>
            `;
            container.appendChild(officeCard);
        });
    }

    function initResultsChart() {
        const ctx = document.createElement('canvas');
        const placeholder = document.querySelector('.chart-placeholder');
        placeholder.parentNode.replaceChild(ctx, placeholder);

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Michael Chen', 'Sarah Williams', 'James Wilson'],
                datasets: [{
                    label: 'Votes',
                    data: [342, 289, 151],
                    backgroundColor: [
                        '#4f46e5',
                        '#f59e0b',
                        '#10b981'
                    ],
                    borderColor: [
                        '#4f46e5',
                        '#f59e0b',
                        '#10b981'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return `${context.dataset.label}: ${context.raw}`;
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Number of Votes'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Candidates'
                        }
                    }
                }
            }
        });
    }

    // ======================
    // Utility Functions
    // ======================
    function formatDate(dateString, format = 'yyyy-MM-dd') {
        const date = new Date(dateString);
        const options = { year: 'numeric', month: 'short', day: 'numeric' };
        return date.toLocaleDateString(undefined, options);
    }

    function filterElections(status) {
        const electionCards = document.querySelectorAll('.election-card');
        
        electionCards.forEach(card => {
            const cardStatus = card.querySelector('.election-status').className.includes('active') ? 'active' : 
                             card.querySelector('.election-status').className.includes('coming') ? 'upcoming' : 
                             'ended';
            
            if (status === 'all' || cardStatus === status) {
                card.style.display = '';
            } else {
                card.style.display = 'none';
            }
        });
    }

    function filterVoters(region, party, gender) {
        const voterRows = document.querySelectorAll('#votersTableBody tr');
        
        voterRows.forEach(row => {
            const rowRegion = row.cells[4].textContent;
            const rowParty = row.cells[5].textContent;
            const rowGender = row.cells[2].textContent;
            
            const regionMatch = !region || rowRegion === region;
            const partyMatch = !party || rowParty === party;
            const genderMatch = !gender || rowGender === gender;
            
            row.style.display = regionMatch && partyMatch && genderMatch ? '' : 'none';
        });
    }

    function showVoterDetails(voterId) {
        // In a real app, you would fetch voter details from an API
        const voterDetails = {
            id: voterId,
            firstname: "Alex",
            lastname: "Johnson",
            birthdate: "1982-03-12",
            gender: "Male",
            email: "alex.johnson@example.com",
            phone: "+1 555-123-4567",
            address: "123 Main St, Capital City",
            region: "North",
            party: "Progressive",
            role: "Member",
            status: "active",
            registered: "2023-01-15",
            lastVoted: "2023-11-05"
        };

        const modalContent = document.getElementById('voterDetailsContent');
        modalContent.innerHTML = `
            <div class="row">
                <div class="col-md-4 text-center mb-4">
                    <div class="voter-avatar mb-3" style="width: 120px; height: 120px; font-size: 2.5rem;">
                        ${voterDetails.firstname.charAt(0)}${voterDetails.lastname.charAt(0)}
                    </div>
                    <h4>${voterDetails.firstname} ${voterDetails.lastname}</h4>
                    <span class="badge ${voterDetails.status === 'active' ? 'bg-success' : 'bg-secondary'}">
                        ${voterDetails.status}
                    </span>
                </div>
                <div class="col-md-8">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p><strong>Birth Date:</strong> ${formatDate(voterDetails.birthdate)}</p>
                            <p><strong>Gender:</strong> ${voterDetails.gender}</p>
                            <p><strong>Email:</strong> ${voterDetails.email}</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Phone:</strong> ${voterDetails.phone}</p>
                            <p><strong>Address:</strong> ${voterDetails.address}</p>
                            <p><strong>Region:</strong> ${voterDetails.region}</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>Political Party:</strong> ${voterDetails.party}</p>
                            <p><strong>Role:</strong> ${voterDetails.role}</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Registered:</strong> ${formatDate(voterDetails.registered)}</p>
                            <p><strong>Last Voted:</strong> ${voterDetails.lastVoted ? formatDate(voterDetails.lastVoted) : 'Never'}</p>
                        </div>
                    </div>
                </div>
            </div>
        `;

        // Show the modal
        document.getElementById('voterDetailsModal').classList.add('show');
        document.getElementById('voterDetailsModal').style.display = 'block';
        document.body.classList.add('modal-open');
    }

    function updateResults(electionId) {
        // In a real app, you would fetch results for the selected election
        console.log('Updating results for election:', electionId);
        
        // Update the results table and chart based on electionId
        const resultsTable = document.querySelector('.results-table tbody');
        if (electionId === "1") {
            // Student Council Election results
            resultsTable.innerHTML = `
                <tr>
                    <td>President</td>
                    <td>
                        <div class="candidate-info">
                            <img src="https://randomuser.me/api/portraits/men/32.jpg" alt="Candidate photo">
                            <span>Michael Chen</span>
                        </div>
                    </td>
                    <td>342</td>
                    <td>43.7%</td>
                    <td>+53 votes</td>
                </tr>
                <tr>
                    <td>Vice President</td>
                    <td>
                        <div class="candidate-info">
                            <img src="https://randomuser.me/api/portraits/women/28.jpg" alt="Candidate photo">
                            <span>Sarah Williams</span>
                        </div>
                    </td>
                    <td>289</td>
                    <td>36.9%</td>
                    <td>+134 votes</td>
                </tr>
                <tr>
                    <td>Treasurer</td>
                    <td>
                        <div class="candidate-info">
                            <img src="https://randomuser.me/api/portraits/women/42.jpg" alt="Candidate photo">
                            <span>Lisa Thompson</span>
                        </div>
                    </td>
                    <td>412</td>
                    <td>52.6%</td>
                    <td>+287 votes</td>
                </tr>
            `;
        } else if (electionId === "2") {
            // HOA Board Election results
            resultsTable.innerHTML = `
                <tr>
                    <td>President</td>
                    <td>
                        <div class="candidate-info">
                            <img src="https://randomuser.me/api/portraits/men/45.jpg" alt="Candidate photo">
                            <span>Robert Johnson</span>
                        </div>
                    </td>
                    <td>215</td>
                    <td>51.2%</td>
                    <td>+87 votes</td>
                </tr>
                <tr>
                    <td>Secretary</td>
                    <td>
                        <div class="candidate-info">
                            <img src="https://randomuser.me/api/portraits/women/33.jpg" alt="Candidate photo">
                            <span>Emily Davis</span>
                        </div>
                    </td>
                    <td>198</td>
                    <td>47.1%</td>
                    <td>+156 votes</td>
                </tr>
            `;
        }
        
        // Update the summary cards
        document.querySelector('.summary-value').textContent = electionId === "1" ? "782" : "420";
        document.querySelector('.summary-meta').textContent = electionId === "1" ? "62% participation" : "47% participation";
    }

    // ======================
    // Initialize Dashboard
    // ======================
    initDashboard();
});