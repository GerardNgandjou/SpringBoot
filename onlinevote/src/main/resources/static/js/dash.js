/**
 * VoteNow Dashboard - Main Application Controller
 * 
 * Features:
 * - Modular architecture with ES6 classes
 * - Comprehensive state management
 * - Responsive sidebar navigation
 * - Dynamic content loading
 * - Notification system
 * - Election management
 * - Candidate management
 * - Voter management
 * - Vote office management
 * - Results visualization
 * - Settings management
 */

class VoteNowDashboard {
  constructor() {
    this.state = {
      currentSection: 'dashboard',
      elections: [],
      candidates: [],
      voters: [],
      offices: [],
      notifications: [],
      currentUser: {
        name: 'Super Admin',
        role: 'Admin',
        avatar: '#'
      },
      filters: {
        elections: 'all',
        candidates: {},
        voters: {},
        offices: {}
      },
      pagination: {
        currentPage: 1,
        itemsPerPage: 10
      }
    };

    this.init();
  }

  async init() {
    try {
      // Check authentication
    //   if (!this.checkAuth()) {
    //     window.location.href = '/login';
    //     return;
    //   }

      // Initialize UI components
      this.initUI();
      
      // Load initial data
      await this.loadInitialData();
      
      // Set up event listeners
      this.setupEventListeners();
      
      // Initial render
      this.renderDashboard();

    } catch (error) {
      console.error('Initialization failed:', error);
      this.showError('Failed to initialize dashboard. Please refresh the page.');
    }
  }

  // Authentication check
  checkAuth() {
    // In a real app, this would check for valid auth tokens
    return document.cookie.includes('session_token');
  }

  // UI Initialization
  initUI() {
    // Initialize sidebar toggle
    this.sidebarToggle = document.querySelector('.sidebar-toggle');
    this.sidebar = document.querySelector('.sidebar');
    
    // Initialize notification system
    this.notificationSystem = new NotificationSystem(this);
    
    // Initialize modals
    this.modalManager = new ModalManager();
    
    // Initialize data tables
    this.dataTableManager = new DataTableManager();
    
    // Set active user
    this.updateUserProfile();
  }

  // Data Loading
  async loadInitialData() {
    try {
      const [elections, candidates, voters, offices, notifications] = await Promise.all([
        this.fetchData('/api/elections'),
        this.fetchData('/api/candidates'),
        this.fetchData('/api/voters'),
        this.fetchData('/api/offices'),
        this.fetchData('/api/notifications')
      ]);

      this.state = {
        ...this.state,
        elections,
        candidates,
        voters,
        offices,
        notifications
      };

    } catch (error) {
      console.error('Error loading initial data:', error);
      throw error;
    }
  }

  async fetchData(endpoint) {
    try {
      const response = await fetch(endpoint);
      if (!response.ok) throw new Error(`Failed to fetch ${endpoint}`);
      return await response.json();
    } catch (error) {
      console.error(`Error fetching ${endpoint}:`, error);
      return [];
    }
  }

  // Event Handling
  setupEventListeners() {
    // Sidebar navigation
    document.querySelectorAll('.nav-link').forEach(link => {
      link.addEventListener('click', (e) => this.handleNavigation(e));
    });

    // Sidebar toggle
    this.sidebarToggle.addEventListener('click', () => {
      this.sidebar.classList.toggle('collapsed');
    });

    // Logout buttons
    document.getElementById('logout-btn').addEventListener('click', (e) => {
      e.preventDefault();
      this.modalManager.showModal('logoutModal');
    });

    document.getElementById('logout-dropdown-btn').addEventListener('click', (e) => {
      e.preventDefault();
      this.modalManager.showModal('logoutModal');
    });

    // Confirm logout
    document.getElementById('confirmLogout').addEventListener('click', () => {
      this.logout();
    });

    // Create election button
    document.getElementById('createElectionBtn').addEventListener('click', (e) => {
      e.preventDefault();
      this.modalManager.showModal('createElectionModal');
    });

    // Election filter buttons
    document.querySelectorAll('.filter-btn').forEach(btn => {
      btn.addEventListener('click', () => this.filterElections(btn.dataset.status));
    });

    // Search functionality
    document.getElementById('searchInput').addEventListener('input', (e) => {
      this.handleSearch(e.target.value);
    });

    // Add more event listeners as needed...
  }

  // Navigation
  handleNavigation(event) {
    event.preventDefault();
    const section = event.currentTarget.dataset.section;
    this.showSection(section);
  }

  showSection(section) {
    // Update active nav item
    document.querySelectorAll('.nav-link').forEach(link => {
      link.classList.remove('active');
      if (link.dataset.section === section) {
        link.classList.add('active');
      }
    });

    // Hide all content sections
    document.querySelectorAll('.content-section').forEach(section => {
      section.classList.remove('active');
    });

    // Show selected section
    const sectionElement = document.getElementById(`${section}-content`);
    if (sectionElement) {
      sectionElement.classList.add('active');
      this.state.currentSection = section;

      // Load section-specific content
      switch(section) {
        case 'dashboard':
          this.renderDashboard();
          break;
        case 'elections':
          this.renderElections();
          break;
        case 'candidates':
          this.renderCandidates();
          break;
        case 'voters':
          this.renderVoters();
          break;
        case 'voteoffice':
          this.renderOffices();
          break;
        case 'results':
          this.renderResults();
          break;
        case 'settings':
          this.renderSettings();
          break;
      }
    }
  }

  // Rendering Methods
  renderDashboard() {
    this.renderSummaryCards();
    this.renderActiveElections();
    this.renderRecentActivity();
  }

  renderSummaryCards() {
    // Update card values from state
    document.querySelector('.card.voters .card-value').textContent = 
      this.state.voters.length.toLocaleString();
    
    document.querySelector('.card.elections .card-value').textContent = 
      this.state.elections.filter(e => e.status === 'active').length;
    
    document.querySelector('.card.candidates .card-value').textContent = 
      this.state.candidates.length;
    
    document.querySelector('.card.completed .card-value').textContent = 
      this.state.elections.filter(e => e.status === 'ended').length;
  }

  renderActiveElections() {
    const activeElections = this.state.elections.filter(e => e.status === 'active');
    const container = document.querySelector('.elections-grid');
    
    if (activeElections.length === 0) {
      container.innerHTML = '<p class="no-results">No active elections</p>';
      return;
    }

    container.innerHTML = activeElections.map(election => `
      <div class="election-card">
        <div class="election-banner" style="background: ${this.getRandomGradient()}">
          <i class="fas ${this.getElectionIcon(election.type)}"></i>
          <span class="election-status active">Active • ${this.getTimeRemaining(election.endDate)}</span>
        </div>
        <div class="election-details">
          <h3 class="election-title">${election.name}</h3>
          <div class="election-meta">
            <div class="election-date">
              <i class="far fa-calendar-alt"></i> ${this.formatDateRange(election.startDate, election.endDate)}
            </div>
            <div><i class="fas fa-users"></i> ${election.candidates.length} candidates</div>
          </div>
          <div class="progress-container">
            <div class="progress-label">
              <span>Participation (${election.voters.length} voters)</span>
              <span>${election.participationRate}%</span>
            </div>
            <div class="progress-bar">
              <div class="progress-fill" style="width: ${election.participationRate}%"></div>
            </div>
          </div>
          <div class="election-stats">
            <div class="stat-item">
              <i class="fas fa-check-circle"></i>
              <span>${election.verifiedVoters} Verified</span>
            </div>
            <div class="stat-item">
              <i class="fas fa-user-clock"></i>
              <span>${election.pendingVoters} Pending</span>
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
      </div>
    `).join('');
  }

  renderRecentActivity() {
    const recentActivity = this.state.notifications.slice(0, 3);
    const container = document.querySelector('.activity-timeline');
    
    container.innerHTML = recentActivity.map(activity => `
      <div class="timeline-item">
        <div class="timeline-icon ${activity.type}">
          <i class="fas ${this.getActivityIcon(activity.type)}"></i>
        </div>
        <div class="timeline-content">
          <p>${activity.message}</p>
          <small>${this.formatTimeAgo(activity.timestamp)}</small>
        </div>
      </div>
    `).join('');
  }

  renderElections(filteredElections = this.state.elections) {
    const container = document.getElementById('electionsContainer');
    
    if (filteredElections.length === 0) {
      container.innerHTML = '<div class="no-results">No elections found matching your criteria.</div>';
      return;
    }

    container.innerHTML = filteredElections.map(election => this.createElectionCard(election)).join('');
  }

  createElectionCard(election) {
    const statusClass = `status-${election.status}`;
    const statusText = election.status.charAt(0).toUpperCase() + election.status.slice(1);
    const startDate = new Date(election.startDate).toLocaleDateString();
    const endDate = new Date(election.endDate).toLocaleDateString();

    return `
      <div class="election-card" aria-labelledby="election-${election.id}-title">
        <div class="card-header">
          <span class="status-badge ${statusClass}">${statusText}</span>
          <h3 id="election-${election.id}-title" class="card-title">${election.name}</h3>
        </div>
        <div class="card-body">
          <p class="card-description">${election.description}</p>
          <div class="card-dates">
            <div class="date-item">
              <i class="fas fa-calendar-alt" aria-hidden="true"></i>
              <span>Starts: ${startDate}</span>
            </div>
            <div class="date-item">
              <i class="fas fa-calendar-check" aria-hidden="true"></i>
              <span>Ends: ${endDate}</span>
            </div>
          </div>
        </div>
        <div class="card-footer">
          <div class="participants">
            <i class="fas fa-users" aria-hidden="true"></i>
            <span>${election.participants} participants</span>
          </div>
          ${this.createElectionActionButton(election)}
        </div>
      </div>
    `;
  }

  // ... Similar render methods for candidates, voters, offices, results, and settings ...

  // Filtering and Searching
  filterElections(status) {
    // Update active filter button
    document.querySelectorAll('.filter-btn').forEach(btn => {
      btn.classList.remove('active');
      if (btn.dataset.status === status) {
        btn.classList.add('active');
      }
    });

    // Filter elections
    let filteredElections;
    if (status === 'all') {
      filteredElections = this.state.elections;
    } else {
      filteredElections = this.state.elections.filter(election => election.status === status);
    }

    this.renderElections(filteredElections);
  }

  handleSearch(query) {
    const normalizedQuery = query.toLowerCase().trim();
    
    switch(this.state.currentSection) {
      case 'elections':
        const filtered = this.state.elections.filter(election => 
          election.name.toLowerCase().includes(normalizedQuery) ||
          election.description.toLowerCase().includes(normalizedQuery)
        );
        this.renderElections(filtered);
        break;
        
      case 'candidates':
        // Filter candidates
        break;
        
      case 'voters':
        // Filter voters
        break;
        
      case 'voteoffice':
        // Filter offices
        break;
    }
  }

  // User Management
  updateUserProfile() {
    const user = this.state.currentUser;
    const profileImg = document.querySelector('.profile-img');
    const userName = document.querySelector('.user-info h4');
    const userRole = document.querySelector('.user-info p');
    
    if (profileImg) profileImg.src = user.avatar;
    if (userName) userName.textContent = user.name;
    if (userRole) userRole.textContent = user.role;
  }

//   logout() {
//     // In a real app, this would make an API call to invalidate the session
//     document.cookie = 'session_token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
//     window.location.href = '/login';
//   }

  // Utility Methods
  formatDate(dateString) {
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  }

  formatTimeAgo(timestamp) {
    const seconds = Math.floor((new Date() - new Date(timestamp)) / 1000);
    
    const intervals = {
      year: 31536000,
      month: 2592000,
      week: 604800,
      day: 86400,
      hour: 3600,
      minute: 60
    };
    
    for (const [unit, secondsInUnit] of Object.entries(intervals)) {
      const interval = Math.floor(seconds / secondsInUnit);
      if (interval >= 1) {
        return interval === 1 ? `${interval} ${unit} ago` : `${interval} ${unit}s ago`;
      }
    }
    
    return 'Just now';
  }

  getRandomGradient() {
    const gradients = [
      'linear-gradient(135deg, #4f46e5, #7c3aed)',
      'linear-gradient(135deg, #f72585, #b5179e)',
      'linear-gradient(135deg, #3f37c9, #4361ee)',
      'linear-gradient(135deg, #4895ef, #4cc9f0)'
    ];
    return gradients[Math.floor(Math.random() * gradients.length)];
  }

  getElectionIcon(type) {
    const icons = {
      student: 'fa-graduation-cap',
      hoa: 'fa-home',
      corporate: 'fa-building',
      default: 'fa-vote-yea'
    };
    return icons[type] || icons.default;
  }

  getActivityIcon(type) {
    const icons = {
      success: 'fa-check-circle',
      info: 'fa-info-circle',
      warning: 'fa-exclamation-triangle',
      error: 'fa-times-circle'
    };
    return icons[type] || 'fa-info-circle';
  }

  showError(message) {
    const errorContainer = document.getElementById('error-container') || document.createElement('div');
    errorContainer.id = 'error-container';
    errorContainer.style.position = 'fixed';
    errorContainer.style.top = '20px';
    errorContainer.style.right = '20px';
    errorContainer.style.zIndex = '1000';
    
    errorContainer.innerHTML = `
      <div class="alert alert-danger" role="alert">
        <i class="fas fa-exclamation-triangle"></i> ${message}
      </div>
    `;
    
    document.body.appendChild(errorContainer);
    
    setTimeout(() => {
      errorContainer.style.opacity = '0';
      setTimeout(() => errorContainer.remove(), 300);
    }, 5000);
  }
}

// Notification System
class NotificationSystem {
  constructor(dashboard) {
    this.dashboard = dashboard;
    this.notificationBell = document.querySelector('.notification-bell');
    this.notificationDropdown = document.querySelector('.notification-dropdown');
    this.setup();
  }

  setup() {
    this.notificationBell.addEventListener('click', (e) => {
      e.stopPropagation();
      this.toggleNotifications();
    });

    document.addEventListener('click', () => {
      this.notificationDropdown.classList.remove('active');
    });

    // Mark all as read
    document.querySelector('.mark-all-read')?.addEventListener('click', () => {
      this.markAllAsRead();
    });
  }

  toggleNotifications() {
    this.notificationDropdown.classList.toggle('active');
  }

  markAllAsRead() {
    this.dashboard.state.notifications.forEach(n => n.read = true);
    this.updateNotificationCount(0);
    this.renderNotifications();
  }

  updateNotificationCount(count) {
    const counter = document.querySelector('.notification-count');
    if (counter) {
      counter.textContent = count;
      counter.style.display = count > 0 ? 'flex' : 'none';
    }
  }

  renderNotifications() {
    const unreadCount = this.dashboard.state.notifications.filter(n => !n.read).length;
    this.updateNotificationCount(unreadCount);
    
    if (this.notificationDropdown) {
      this.notificationDropdown.innerHTML = `
        <div class="notification-header">
          <h4>Notifications</h4>
          <button class="mark-all-read">Mark all as read</button>
        </div>
        ${this.dashboard.state.notifications.slice(0, 5).map(n => `
          <div class="notification-item ${n.read ? '' : 'unread'}">
            <div class="notification-icon">
              <i class="fas ${this.dashboard.getActivityIcon(n.type)}"></i>
            </div>
            <div class="notification-content">
              <p>${n.message}</p>
              <small>${this.dashboard.formatTimeAgo(n.timestamp)}</small>
            </div>
          </div>
        `).join('')}
      `;
    }
  }
}

// Modal Manager
class ModalManager {
  constructor() {
    this.modals = {};
    this.setupModals();
  }

  setupModals() {
    document.querySelectorAll('.modal').forEach(modal => {
      const id = modal.id;
      this.modals[id] = modal;
      
      // Close buttons
      modal.querySelectorAll('.modal-close, .modal-cancel').forEach(btn => {
        btn.addEventListener('click', () => this.hideModal(id));
      });
      
      // Prevent click inside modal from closing it
      modal.querySelector('.modal-content')?.addEventListener('click', (e) => {
        e.stopPropagation();
      });
    });
    
    // Close modal when clicking outside
    document.addEventListener('click', (e) => {
      if (e.target.classList.contains('modal')) {
        this.hideModal(e.target.id);
      }
    });
    
    // Close with Escape key
    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape') {
        this.hideAllModals();
      }
    });
  }

  showModal(id) {
    if (this.modals[id]) {
      this.modals[id].style.display = 'flex';
      setTimeout(() => {
        this.modals[id].classList.add('active');
      }, 10);
    }
  }

  hideModal(id) {
    if (this.modals[id]) {
      this.modals[id].classList.remove('active');
      setTimeout(() => {
        this.modals[id].style.display = 'none';
      }, 300);
    }
  }

  hideAllModals() {
    Object.keys(this.modals).forEach(id => this.hideModal(id));
  }
}

// Data Table Manager
class DataTableManager {
  constructor() {
    this.sortDirections = {};
  }

  initTable(tableId, config) {
    const table = document.getElementById(tableId);
    if (!table) return;

    // Add sorting to headers
    table.querySelectorAll('th[data-sort]').forEach(header => {
      header.style.cursor = 'pointer';
      header.addEventListener('click', () => {
        this.sortTable(table, header.dataset.sort, header.dataset.sortType || 'string');
      });
    });

    // Initialize pagination if needed
    if (config.pagination) {
      this.setupPagination(tableId, config);
    }
  }

  sortTable(table, key, type = 'string') {
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));
    
    // Determine sort direction
    if (!this.sortDirections[key]) {
      this.sortDirections[key] = 'asc';
    } else {
      this.sortDirections[key] = this.sortDirections[key] === 'asc' ? 'desc' : 'asc';
    }
    
    const direction = this.sortDirections[key];
    
    rows.sort((a, b) => {
      let aValue = a.querySelector(`td[data-${key}]`)?.getAttribute(`data-${key}`) || 
                  a.querySelector(`td:nth-child(${parseInt(key) + 1})`)?.textContent;
      let bValue = b.querySelector(`td[data-${key}]`)?.getAttribute(`data-${key}`) || 
                  b.querySelector(`td:nth-child(${parseInt(key) + 1})`)?.textContent;
      
      if (type === 'number') {
        aValue = parseFloat(aValue);
        bValue = parseFloat(bValue);
      } else if (type === 'date') {
        aValue = new Date(aValue);
        bValue = new Date(bValue);
      }
      
      if (direction === 'asc') {
        return aValue > bValue ? 1 : -1;
      } else {
        return aValue < bValue ? 1 : -1;
      }
    });
    
    // Re-append sorted rows
    rows.forEach(row => tbody.appendChild(row));
    
    // Update sort indicators
    table.querySelectorAll('th').forEach(th => {
      th.classList.remove('sort-asc', 'sort-desc');
    });
    
    const header = table.querySelector(`th[data-sort="${key}"]`);
    if (header) {
      header.classList.add(`sort-${direction}`);
    }
  }

  setupPagination(tableId, config) {
    // Implementation would depend on your specific pagination needs
  }
}

// Initialize the dashboard when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
  const dashboard = new VoteNowDashboard();
  
  // Make dashboard available globally for debugging (remove in production)
  window.dashboard = dashboard;
});