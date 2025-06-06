// DOM Elements
const sidebar = document.getElementById('sidebar');
const sidebarToggle = document.getElementById('sidebarToggle');
const sections = document.querySelectorAll('.section');
const menuItems = document.querySelectorAll('.menu-item');

// Toggle Sidebar
sidebarToggle.addEventListener('click', () => {
    sidebar.classList.toggle('active');
});

// Navigation between sections
menuItems.forEach(item => {
    item.addEventListener('click', (e) => {
        e.preventDefault();

        // Remove active class from all menu items
        menuItems.forEach(i => i.classList.remove('active'));

        // Add active class to clicked menu item
        item.classList.add('active');

        // Hide all sections
        sections.forEach(section => {
            section.style.display = 'none';
        });

        // Show the selected section
        const target = item.getAttribute('href');
        document.querySelector(target).style.display = 'block';
    });
});

// Initialize Charts
document.addEventListener('DOMContentLoaded', function() {
    // Bar Chart
    const barCtx = document.getElementById('resultsBarChart').getContext('2d');
    const barChart = new Chart(barCtx, {
        type: 'bar',
        data: {
            labels: ['Jane Smith', 'John Doe', 'Alex Johnson', 'Maria Garcia'],
            datasets: [{
                label: 'Votes',
                data: [1024, 818, 450, 320],
                backgroundColor: [
                    '#4a6bff',
                    '#6c63ff',
                    '#4cc9f0',
                    '#a78bfa'
                ],
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // Pie Chart
    const pieCtx = document.getElementById('resultsPieChart').getContext('2d');
    const pieChart = new Chart(pieCtx, {
        type: 'pie',
        data: {
            labels: ['Jane Smith', 'John Doe', 'Alex Johnson', 'Maria Garcia'],
            datasets: [{
                data: [1024, 818, 450, 320],
                backgroundColor: [
                    '#4a6bff',
                    '#6c63ff',
                    '#4cc9f0',
                    '#a78bfa'
                ],
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'right'
                }
            }
        }
    });
});

// Tab Switching
const tabs = document.querySelectorAll('.tab');
tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        // Remove active class from all tabs and contents
        document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));

        // Add active class to clicked tab and corresponding content
        tab.classList.add('active');
        const tabId = tab.getAttribute('data-tab');
        document.getElementById(tabId).classList.add('active');
    });
});

// Modal Handling
function setupModal(openBtnId, modalId) {
    const openBtn = document.getElementById(openBtnId);
    const modal = document.getElementById(modalId);
    const closeBtn = modal.querySelector('.close');

    openBtn.addEventListener('click', () => {
        modal.classList.add('active');
    });

    closeBtn.addEventListener('click', () => {
        modal.classList.remove('active');
    });

    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.classList.remove('active');
        }
    });
}

// Initialize all modals
setupModal('createElectionBtn', 'createElectionModal');
setupModal('addVoterBtn', 'addVoterModal');
setupModal('addCandidateBtn', 'addCandidateModal');
setupModal('generateReportBtn', 'generateReportModal');

// Show elections section by default
document.querySelector('#elections').style.display = 'block';