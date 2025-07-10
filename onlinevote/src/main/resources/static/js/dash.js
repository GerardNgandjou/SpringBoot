// ===== GLOBAL VARIABLES & CONFIG =====
const API_BASE_URL = 'https://your-api-domain.com/api/v1';
const AUTH_TOKEN = localStorage.getItem('authToken');

// Axios instance with auth header
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Authorization': `Bearer ${AUTH_TOKEN}`,
    'Content-Type': 'application/json'
  }
});

// Handle API errors
const handleApiError = (error) => {
  if (error.response) {
    console.error('API Error:', error.response.data);
    showToast(error.response.data.message || 'An error occurred', 'error');
  } else {
    console.error('Network Error:', error.message);
    showToast('Network error. Please try again.', 'error');
  }
};

// ===== DASHBOARD SECTION =====
const loadDashboardStats = async () => {
  try {
    const response = await api.get('/dashboard/stats');
    const stats = response.data.data;
    
    // Update dashboard cards
    document.querySelector('.card-value[text="#"]').textContent = stats.totalVoters.toLocaleString();
    document.querySelector('.card-value[text="${stats.activeElections}"]').textContent = stats.activeElections;
    document.querySelector('.card-value[text="#"]').textContent = stats.totalCandidates.toLocaleString();
    document.querySelector('.card-value[text="${stats.completedElections}"]').textContent = stats.completedElections;
    
    // Update change indicators
    document.querySelector('.card-change.positive span[text="#"]').textContent = `${stats.voterGrowth}% from last month`;
    document.querySelector('.card-change.positive span[text="${stats.newElectionsThisWeek} + \' new this week\'"]').textContent = `${stats.newElectionsThisWeek} new this week`;
    document.querySelector('.card-change.negative span[text="#"]').textContent = `${stats.withdrawnCandidates} withdrawn`;
    document.querySelector('.card-change.positive span[text="${stats.completedThisQuarter} + \' this quarter\'"]').textContent = `${stats.completedThisQuarter} this quarter`;
    
    // Load active elections
    loadActiveElections(stats.activeElectionsList);
    
    // Load recent activity
    loadRecentActivity(stats.recentActivities);
    
  } catch (error) {
    handleApiError(error);
  }
};

const loadActiveElections = async (elections) => {
  const container = document.getElementById('electionsContainer');
  container.innerHTML = '';
  
  elections.forEach(election => {
    const daysLeft = Math.ceil((new Date(election.endDate) - new Date()) / (1000 * 60 * 60 * 24));
    const statusClass = election.status === 'ACTIVE' ? 'ACTIVE' : 
                       election.status === 'UPCOMING' ? 'UPCOMING' : 'ENDED';
    
    container.innerHTML += `
      <div class="election-card">
        <div class="election-banner" style="background: linear-gradient(135deg, ${election.color1}, ${election.color2})">
          <i class="fas ${election.icon}"></i>
          <span class="election-status ${statusClass}">
            ${election.status} • ${daysLeft} days left
          </span>
        </div>
        <div class="election-details">
          <h4 class="election-title">${election.title}</h4>
          <div class="election-meta">
            <div class="election-date">
              <i class="far fa-calendar-alt"></i>
              <span>${new Date(election.startDate).toLocaleDateString()} - ${new Date(election.endDate).toLocaleDateString()}</span>
            </div>
            <div>
              <i class="fas fa-users"></i>
              <span>${election.candidateCount} candidates</span>
            </div>
          </div>
          <div class="election-actions">
            <a href="#results" class="btn btn-outline" data-election-id="${election.id}">
              <i class="fas fa-chart-bar"></i> Results
            </a>
            <a href="#elections" class="btn btn-primary" data-election-id="${election.id}">
              <i class="fas fa-cog"></i> Manage
            </a>
          </div>
        </div>
      </div>
    `;
  });
};

const loadRecentActivity = async (activities) => {
  const container = document.querySelector('.activity-timeline');
  container.innerHTML = '';
  
  activities.forEach(activity => {
    container.innerHTML += `
      <div class="timeline-item">
        <div class="timeline-icon ${activity.type}">
          <i class="fas ${activity.icon}"></i>
        </div>
        <div class="timeline-content">
          <p>${activity.message}</p>
          <small>${new Date(activity.timestamp).toLocaleString()}</small>
        </div>
      </div>
    `;
  });
};

// ===== ELECTIONS SECTION =====
const loadElections = async (status = 'all') => {
  try {
    const response = await api.get(`/elections?status=${status}`);
    const elections = response.data.data;
    const container = document.getElementById('electionsContainer');
    
    if (elections.length === 0) {
      container.innerHTML = `
        <div class="no-results">
          <i class="fas fa-calendar-times"></i>
          <p>No elections found</p>
          <a href="#elections" class="btn btn-primary" id="createElectionBtn">
            <i class="fas fa-plus"></i> Create Your First Election
          </a>
        </div>
      `;
      return;
    }
    
    container.innerHTML = '';
    elections.forEach(election => {
      container.innerHTML += `
        <article class="election-card">
          <!-- Same structure as in dashboard -->
        </article>
      `;
    });
    
  } catch (error) {
    handleApiError(error);
  }
};

const createElection = async (formData) => {
  try {
    const response = await api.post('/elections', formData);
    showToast('Election created successfully!', 'success');
    loadElections();
    return response.data.data;
  } catch (error) {
    handleApiError(error);
  }
};

// ===== CANDIDATES SECTION =====
const loadCandidates = async (filters = {}) => {
  try {
    const query = new URLSearchParams(filters).toString();
    const response = await api.get(`/candidates?${query}`);
    const candidates = response.data.data;
    const container = document.getElementById('candidateGrid');
    
    if (candidates.length === 0) {
      container.innerHTML = `
        <div id="noResults" class="text-center py-5">
          <i class="fas fa-user-slash fa-3x mb-3 text-muted"></i>
          <h3>No candidates found</h3>
          <p class="text-muted">Try adjusting your search or filters</p>
          <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addCandidateModal">
            <i class="fas fa-plus"></i> Add New Candidate
          </button>
        </div>
      `;
      return;
    }
    
    container.innerHTML = '';
    candidates.forEach(candidate => {
      container.innerHTML += `
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
                <button class="btn btn-sm btn-outline" data-candidate-id="${candidate.id}">
                  <i class="fas fa-eye"></i>
                </button>
                <button class="btn btn-sm btn-outline" data-candidate-id="${candidate.id}">
                  <i class="fas fa-edit"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      `;
    });
    
  } catch (error) {
    handleApiError(error);
  }
};

const addCandidate = async (formData) => {
  try {
    const response = await api.post('/candidates', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    showToast('Candidate added successfully!', 'success');
    loadCandidates();
    return response.data.data;
  } catch (error) {
    handleApiError(error);
  }
};

// ===== VOTERS SECTION =====
const loadVoters = async (filters = {}) => {
  try {
    const query = new URLSearchParams(filters).toString();
    const response = await api.get(`/voters?${query}`);
    const voters = response.data.data;
    const container = document.getElementById('voterGrid');
    
    if (voters.length === 0) {
      container.innerHTML = `
        <div id="noResults" class="text-center py-5">
          <i class="fas fa-user-slash fa-3x mb-3 text-muted"></i>
          <h3>No voters found</h3>
          <p class="text-muted">Try adjusting your search or filters</p>
        </div>
      `;
      return;
    }
    
    container.innerHTML = '';
    voters.forEach(voter => {
      container.innerHTML += `
        <div class="col">
          <div class="voter-card">
            <div class="voter-card-header">
              <img src="${voter.photoUrl || '/images/default-profile.png'}" 
                   alt="${voter.firstName} ${voter.lastName}" 
                   class="voter-avatar">
              <div class="voter-info">
                <h4>${voter.firstName} ${voter.lastName}</h4>
                <p>${voter.email}</p>
              </div>
            </div>
            <div class="voter-card-body">
              <div class="voter-detail-item">
                <span class="voter-detail-label">ID:</span>
                <span class="voter-detail-value">${voter.voterId}</span>
              </div>
              <div class="voter-detail-item">
                <span class="voter-detail-label">Region:</span>
                <span class="voter-detail-value">${voter.region}</span>
              </div>
              <div class="voter-detail-item">
                <span class="voter-detail-label">Registered:</span>
                <span class="voter-detail-value">${new Date(voter.registrationDate).toLocaleDateString()}</span>
              </div>
            </div>
            <div class="voter-card-footer">
              <span class="voter-role-badge role-${voter.role.toLowerCase()}">
                ${voter.role}
              </span>
              <button class="btn btn-sm btn-outline" data-voter-id="${voter.id}">
                <i class="fas fa-eye"></i>
              </button>
            </div>
          </div>
        </div>
      `;
    });
    
  } catch (error) {
    handleApiError(error);
  }
};

// ===== VOTE OFFICE SECTION =====
const loadVoteOffices = async (filters = {}) => {
  try {
    const query = new URLSearchParams(filters).toString();
    const response = await api.get(`/vote-offices?${query}`);
    const offices = response.data.data;
    const container = document.getElementById('officesContainer');
    
    if (offices.length === 0) {
      container.innerHTML = `
        <div class="empty-state">
          <i class="fas fa-building fa-3x mb-3"></i>
          <h3>No vote offices found</h3>
          <p>Create your first vote office to get started</p>
          <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addOfficeModal">
            <i class="fas fa-plus"></i> Add Office
          </button>
        </div>
      `;
      return;
    }
    
    container.innerHTML = '';
    offices.forEach(office => {
      container.innerHTML += `
        <div class="office-card">
          <div class="office-card-header">
            <h3>${office.name}</h3>
            <span class="office-region">${office.region}</span>
          </div>
          <div class="office-card-body">
            <div class="office-info-item">
              <div class="office-info-icon">
                <i class="fas fa-map-marker-alt"></i>
              </div>
              <div class="office-info-content">
                <h4>Address</h4>
                <p>${office.address}</p>
              </div>
            </div>
            <div class="office-info-item">
              <div class="office-info-icon">
                <i class="fas fa-phone"></i>
              </div>
              <div class="office-info-content">
                <h4>Phone</h4>
                <p>${office.phone || 'Not provided'}</p>
              </div>
            </div>
            <div class="office-info-item">
              <div class="office-info-icon">
                <i class="fas fa-clock"></i>
              </div>
              <div class="office-info-content">
                <h4>Hours</h4>
                <p class="office-hours">${office.hours || 'Mon-Fri 9:00 AM - 5:00 PM'}</p>
              </div>
            </div>
          </div>
          <div class="office-card-footer">
            <button class="btn btn-sm btn-outline" data-office-id="${office.id}">
              <i class="fas fa-edit"></i> Edit
            </button>
            <button class="btn btn-sm btn-primary" data-office-id="${office.id}">
              <i class="fas fa-map-marked-alt"></i> View on Map
            </button>
          </div>
        </div>
      `;
    });
    
  } catch (error) {
    handleApiError(error);
  }
};

const addVoteOffice = async (officeData) => {
  try {
    const response = await api.post('/vote-offices', officeData);
    showToast('Vote office added successfully!', 'success');
    loadVoteOffices();
    return response.data.data;
  } catch (error) {
    handleApiError(error);
  }
};

// ===== RESULTS SECTION =====
const loadElectionResults = async (electionId) => {
  try {
    const response = await api.get(`/elections/${electionId}/results`);
    const results = response.data.data;
    
    // Update summary cards
    document.querySelector('.summary-value').textContent = results.totalVotes.toLocaleString();
    document.querySelector('.summary-meta').textContent = `${results.participationRate}% participation`;
    
    // Update chart
    renderResultsChart(results.chartData);
    
    // Update results table
    const tableBody = document.querySelector('.results-table tbody');
    tableBody.innerHTML = '';
    
    results.positions.forEach(position => {
      tableBody.innerHTML += `
        <tr>
          <th scope="row">${position.name}</th>
          <td>
            <div class="candidate-info">
              <img src="${position.winnerImageUrl}" alt="Winner photo">
              <span>${position.winnerName}</span>
            </div>
          </td>
          <td>${position.winnerVotes}</td>
          <td>${position.winnerPercentage}%</td>
          <td>+${position.voteMargin} votes</td>
        </tr>
      `;
    });
    
  } catch (error) {
    handleApiError(error);
  }
};

const renderResultsChart = (chartData) => {
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
};

// ===== SETTINGS SECTION =====
const updateAccountSettings = async (formData) => {
  try {
    const response = await api.put('/settings/account', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    showToast('Account updated successfully!', 'success');
    return response.data.data;
  } catch (error) {
    handleApiError(error);
  }
};

const updateSecuritySettings = async (formData) => {
  try {
    await api.put('/settings/security', formData);
    showToast('Security settings updated successfully!', 'success');
    return true;
  } catch (error) {
    handleApiError(error);
  }
};

// ===== UTILITY FUNCTIONS =====
const showToast = (message, type = 'success') => {
  const toast = document.createElement('div');
  toast.className = `toast toast-${type}`;
  toast.innerHTML = `
    <div class="toast-message">${message}</div>
    <button class="toast-close">&times;</button>
  `;
  
  document.body.appendChild(toast);
  
  setTimeout(() => {
    toast.classList.add('show');
  }, 100);
  
  setTimeout(() => {
    toast.classList.remove('show');
    setTimeout(() => {
      toast.remove();
    }, 300);
  }, 5000);
  
  toast.querySelector('.toast-close').addEventListener('click', () => {
    toast.classList.remove('show');
    setTimeout(() => {
      toast.remove();
    }, 300);
  });
};

// ===== INITIALIZATION =====
document.addEventListener('DOMContentLoaded', () => {
  // Load initial data based on current hash
  const hash = window.location.hash || '#dashboard';
  
  switch (hash) {
    case '#dashboard':
      loadDashboardStats();
      break;
    case '#elections':
      loadElections();
      break;
    case '#candidates':
      loadCandidates();
      break;
    case '#voters':
      loadVoters();
      break;
    case '#voteoffice':
      loadVoteOffices();
      break;
    case '#results':
      // Load first election results by default
      loadElectionResults(1);
      break;
  }
  
  // Handle hash changes
  window.addEventListener('hashchange', () => {
    const newHash = window.location.hash;
    // Similar switch case as above
  });
  
  // Initialize modals
  const addCandidateModal = new bootstrap.Modal(document.getElementById('addCandidateModal'));
  const addOfficeModal = new bootstrap.Modal(document.getElementById('addOfficeModal'));
  
  // Form submissions
  document.getElementById('candidateForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    await addCandidate(formData);
    addCandidateModal.hide();
  });
  
  document.getElementById('officeForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    await addVoteOffice(data);
    addOfficeModal.hide();
  });
  
  // Filter handlers
  document.getElementById('candidateSearch').addEventListener('input', (e) => {
    loadCandidates({ search: e.target.value });
  });
  
  document.getElementById('searchInput').addEventListener('input', (e) => {
    loadVoters({ search: e.target.value });
  });
});

// ===== CANDIDATE DETAILS & EDITING =====
const viewCandidateDetails = async (candidateId) => {
  try {
    const response = await api.get(`/candidates/${candidateId}`);
    const candidate = response.data.data;
    
    // Show in modal or dedicated view
    const modal = new bootstrap.Modal(document.getElementById('candidateDetailModal'));
    document.getElementById('candidateDetailContent').innerHTML = `
      <div class="row">
        <div class="col-md-4 text-center">
          <img src="${candidate.photoUrl || '/images/default-candidate.jpg'}" 
               class="img-fluid rounded-circle mb-3" 
               alt="${candidate.firstName} ${candidate.lastName}"
               style="width: 200px; height: 200px; object-fit: cover;">
          <h3>${candidate.firstName} ${candidate.lastName}</h3>
          <span class="badge bg-primary">${candidate.party || 'Independent'}</span>
          <p class="mt-2">
            <i class="fas fa-map-marker-alt"></i> ${candidate.region}, ${candidate.department}
          </p>
        </div>
        <div class="col-md-8">
          <div class="card mb-3">
            <div class="card-header">
              <h4>Candidate Information</h4>
            </div>
            <div class="card-body">
              <div class="row mb-3">
                <div class="col-md-6">
                  <p><strong>Birthdate:</strong> ${new Date(candidate.birthDate).toLocaleDateString()}</p>
                </div>
                <div class="col-md-6">
                  <p><strong>Gender:</strong> ${candidate.gender || 'Not specified'}</p>
                </div>
              </div>
              <div class="row mb-3">
                <div class="col-md-6">
                  <p><strong>Election:</strong> ${candidate.election.title}</p>
                </div>
                <div class="col-md-6">
                  <p><strong>Position:</strong> ${candidate.position}</p>
                </div>
              </div>
              <h5 class="mt-4">Biography</h5>
              <p>${candidate.biography || 'No biography provided'}</p>
            </div>
          </div>
          
          <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
              <h4 class="mb-0">Campaign Statistics</h4>
              <span class="badge ${candidate.status === 'ACTIVE' ? 'bg-success' : 
                                candidate.status === 'PENDING' ? 'bg-warning' : 'bg-danger'}">
                ${candidate.status}
              </span>
            </div>
            <div class="card-body">
              <div class="row text-center">
                <div class="col-md-3">
                  <h5>${candidate.votes.toLocaleString()}</h5>
                  <p class="text-muted">Total Votes</p>
                </div>
                <div class="col-md-3">
                  <h5>${candidate.votePercentage}%</h5>
                  <p class="text-muted">Vote Share</p>
                </div>
                <div class="col-md-3">
                  <h5>${candidate.donations.toLocaleString()}</h5>
                  <p class="text-muted">Donations ($)</p>
                </div>
                <div class="col-md-3">
                  <h5>${candidate.eventsAttended}</h5>
                  <p class="text-muted">Events</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    `;
    modal.show();
    
  } catch (error) {
    handleApiError(error);
  }
};

const editCandidate = async (candidateId) => {
  try {
    // Fetch candidate data
    const response = await api.get(`/candidates/${candidateId}`);
    const candidate = response.data.data;
    
    // Populate form
    const form = document.getElementById('editCandidateForm');
    form.elements['firstName'].value = candidate.firstName;
    form.elements['lastName'].value = candidate.lastName;
    form.elements['birthDate'].value = candidate.birthDate.split('T')[0];
    form.elements['gender'].value = candidate.gender;
    form.elements['party'].value = candidate.party;
    form.elements['electionId'].value = candidate.election.id;
    form.elements['region'].value = candidate.region;
    form.elements['department'].value = candidate.department;
    form.elements['arron'].value = candidate.arron;
    form.elements['biography'].value = candidate.biography;
    
    // Show preview of existing photo
    const preview = document.getElementById('candidatePhotoPreview');
    preview.innerHTML = `
      <img src="${candidate.photoUrl}" 
           class="img-thumbnail" 
           style="max-height: 200px;"
           alt="Current photo">
      <div class="mt-2">
        <button type="button" class="btn btn-sm btn-outline-danger" id="removePhotoBtn">
          <i class="fas fa-trash"></i> Remove Photo
        </button>
      </div>
    `;
    
    // Show modal
    const modal = new bootstrap.Modal(document.getElementById('editCandidateModal'));
    modal.show();
    
    // Handle form submission
    form.onsubmit = async (e) => {
      e.preventDefault();
      const formData = new FormData(form);
      
      try {
        await api.put(`/candidates/${candidateId}`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        showToast('Candidate updated successfully!', 'success');
        loadCandidates();
        modal.hide();
      } catch (error) {
        handleApiError(error);
      }
    };
    
    // Handle photo removal
    document.getElementById('removePhotoBtn').addEventListener('click', () => {
      document.getElementById('removePhotoFlag').value = 'true';
      preview.innerHTML = '<div class="text-muted">Photo will be removed</div>';
    });
    
  } catch (error) {
    handleApiError(error);
  }
};

// ===== VOTER VERIFICATION =====
const verifyVoter = async (voterId) => {
  try {
    await api.post(`/voters/${voterId}/verify`);
    showToast('Voter verified successfully!', 'success');
    loadVoters();
  } catch (error) {
    handleApiError(error);
  }
};

const rejectVoter = async (voterId) => {
  try {
    await api.post(`/voters/${voterId}/reject`);
    showToast('Voter registration rejected', 'warning');
    loadVoters();
  } catch (error) {
    handleApiError(error);
  }
};

// ===== ELECTION MANAGEMENT =====
const startElection = async (electionId) => {
  try {
    await api.post(`/elections/${electionId}/start`);
    showToast('Election started successfully!', 'success');
    loadElections();
  } catch (error) {
    handleApiError(error);
  }
};

const endElection = async (electionId) => {
  try {
    await api.post(`/elections/${electionId}/end`);
    showToast('Election ended successfully!', 'success');
    loadElections();
  } catch (error) {
    handleApiError(error);
  }
};

const archiveElection = async (electionId) => {
  try {
    await api.post(`/elections/${electionId}/archive`);
    showToast('Election archived', 'info');
    loadElections();
  } catch (error) {
    handleApiError(error);
  }
};

// ===== REAL-TIME UPDATES =====
const setupRealTimeUpdates = () => {
  // Using Socket.io for real-time updates
  const socket = io(API_BASE_URL, {
    auth: {
      token: AUTH_TOKEN
    }
  });

  socket.on('new_vote', (data) => {
    showToast(`New vote recorded in ${data.electionTitle}`, 'info');
    if (window.location.hash === '#dashboard') {
      loadDashboardStats();
    }
    if (window.location.hash === '#results' && data.electionId === currentElectionId) {
      loadElectionResults(currentElectionId);
    }
  });

  socket.on('voter_registered', (data) => {
    showToast(`New voter registered: ${data.firstName} ${data.lastName}`, 'success');
    if (window.location.hash === '#dashboard' || window.location.hash === '#voters') {
      loadVoters();
    }
  });

  socket.on('election_status_changed', (data) => {
    showToast(`Election "${data.title}" status changed to ${data.status}`, 'warning');
    if (window.location.hash === '#dashboard' || window.location.hash === '#elections') {
      loadElections();
    }
  });

  socket.on('connect_error', (error) => {
    console.error('Socket connection error:', error);
  });
};

// ===== EXPORT FUNCTIONS =====
const exportResults = async (electionId, format) => {
  try {
    const response = await api.get(`/elections/${electionId}/export?format=${format}`, {
      responseType: 'blob'
    });
    
    // Create download link
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `election-results-${electionId}.${format}`);
    document.body.appendChild(link);
    link.click();
    link.remove();
    
    showToast(`Exported results as ${format.toUpperCase()}`, 'success');
  } catch (error) {
    handleApiError(error);
  }
};

// ===== EVENT LISTENERS =====
const setupEventListeners = () => {
  // Candidate actions
  document.addEventListener('click', (e) => {
    if (e.target.closest('[data-candidate-id]')) {
      const candidateId = e.target.closest('[data-candidate-id]').getAttribute('data-candidate-id');
      if (e.target.closest('.btn-outline')) {
        viewCandidateDetails(candidateId);
      } else if (e.target.closest('.btn-primary')) {
        editCandidate(candidateId);
      }
    }
  });
  
  // Voter actions
  document.addEventListener('click', (e) => {
    if (e.target.closest('[data-voter-id]')) {
      const voterId = e.target.closest('[data-voter-id]').getAttribute('data-voter-id');
      if (e.target.classList.contains('btn-verify')) {
        verifyVoter(voterId);
      } else if (e.target.classList.contains('btn-reject')) {
        rejectVoter(voterId);
      }
    }
  });
  
  // Election actions
  document.addEventListener('click', (e) => {
    if (e.target.closest('[data-election-id]')) {
      const electionId = e.target.closest('[data-election-id]').getAttribute('data-election-id');
      if (e.target.classList.contains('btn-start')) {
        startElection(electionId);
      } else if (e.target.classList.contains('btn-end')) {
        endElection(electionId);
      } else if (e.target.classList.contains('btn-archive')) {
        archiveElection(electionId);
      }
    }
  });
  
  // Export buttons
  document.getElementById('exportPdf').addEventListener('click', () => {
    exportResults(currentElectionId, 'pdf');
  });
  
  document.getElementById('exportCsv').addEventListener('click', () => {
    exportResults(currentElectionId, 'csv');
  });
  
  // Print results
  document.getElementById('printResults').addEventListener('click', () => {
    window.print();
  });
  
  // Filter changes
  document.getElementById('candidatePartyFilter').addEventListener('change', (e) => {
    loadCandidates({ party: e.target.value });
  });
  
  document.getElementById('regionFilter').addEventListener('change', (e) => {
    loadVoters({ region: e.target.value });
  });
};

// ===== INITIALIZATION =====
document.addEventListener('DOMContentLoaded', () => {
  // Initialize the app
  setupEventListeners();
  setupRealTimeUpdates();
  
  // Load initial data based on current section
  loadInitialData();
  
  // Handle navigation
  window.addEventListener('hashchange', handleRouteChange);
});

const loadInitialData = () => {
  const hash = window.location.hash || '#dashboard';
  
  switch (hash) {
    case '#dashboard':
      loadDashboardStats();
      break;
    case '#elections':
      loadElections();
      break;
    case '#candidates':
      loadCandidates();
      break;
    case '#voters':
      loadVoters();
      break;
    case '#voteoffice':
      loadVoteOffices();
      break;
    case '#results':
      // Load first election results by default
      const electionSelect = document.getElementById('electionSelect');
      if (electionSelect && electionSelect.options.length > 0) {
        currentElectionId = electionSelect.options[1].value;
        loadElectionResults(currentElectionId);
      }
      break;
  }
};

const handleRouteChange = () => {
  // Clear any existing data
  document.querySelectorAll('.content-section').forEach(section => {
    section.classList.remove('active');
  });
  
  // Show the active section
  const newHash = window.location.hash;
  const activeSection = document.querySelector(`${newHash}-content`);
  if (activeSection) {
    activeSection.classList.add('active');
    loadInitialData();
  }
};