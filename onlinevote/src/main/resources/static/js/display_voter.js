document.addEventListener('DOMContentLoaded', function() {
    let currentPage = 1;
    const itemsPerPage = 10;
    let allVoters = [];
    let filteredVoters = [];

    // Fetch voters from API
    fetch('/voters/api')
        .then(response => response.json())
        .then(data => {
            allVoters = data;
            filteredVoters = [...allVoters];
            renderTable(currentPage);
            renderPagination();
        })
        .catch(error => console.error('Error fetching voters:', error));

    // Apply filters
    document.getElementById('applyFilters').addEventListener('click', function() {
        const regionFilter = document.getElementById('regionFilter').value;
        const partyFilter = document.getElementById('partyFilter').value;
        const genderFilter = document.getElementById('genderFilter').value;

        filteredVoters = allVoters.filter(voter => {
            return (regionFilter === '' || voter.region === regionFilter) &&
                   (partyFilter === '' || voter.party === partyFilter) &&
                   (genderFilter === '' || voter.gender === genderFilter);
        });

        currentPage = 1;
        renderTable(currentPage);
        renderPagination();
        
        // Close modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('filterModal'));
        modal.hide();
    });

    // Render table with pagination
    function renderTable(page) {
        const startIndex = (page - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;
        const votersToDisplay = filteredVoters.slice(startIndex, endIndex);
        
        const tableBody = document.getElementById('votersTableBody');
        tableBody.innerHTML = '';

        votersToDisplay.forEach(voter => {
            const row = document.createElement('tr');
            
            row.innerHTML = `
                <td>${voter.lastname}, ${voter.firstname}</td>
                <td>${new Date(voter.birthdate).toLocaleDateString()}</td>
                <td>${voter.gender}</td>
                <td>${voter.location}</td>
                <td>${voter.region}</td>
                <td><span class="badge bg-primary">${voter.party || 'None'}</span></td>
                <td><span class="badge bg-success">${voter.role}</span></td>
                <td>
                    <button class="btn btn-sm btn-outline-primary action-btn view-details" data-id="${voter.id}">
                        <i class="bi bi-eye-fill"></i> View
                    </button>
                </td>
            `;
            
            tableBody.appendChild(row);
        });

        // Add event listeners to view buttons
        document.querySelectorAll('.view-details').forEach(button => {
            button.addEventListener('click', function() {
                const voterId = this.getAttribute('data-id');
                showVoterDetails(voterId);
            });
        });
    }

    // Render pagination controls
    function renderPagination() {
        const totalPages = Math.ceil(filteredVoters.length / itemsPerPage);
        const pagination = document.getElementById('pagination');
        pagination.innerHTML = '';

        // Previous button
        const prevLi = document.createElement('li');
        prevLi.className = `page-item ${currentPage === 1 ? 'disabled' : ''}`;
        prevLi.innerHTML = `<a class="page-link" href="#">Previous</a>`;
        prevLi.addEventListener('click', (e) => {
            e.preventDefault();
            if (currentPage > 1) {
                currentPage--;
                renderTable(currentPage);
                renderPagination();
            }
        });
        pagination.appendChild(prevLi);

        // Page numbers
        for (let i = 1; i <= totalPages; i++) {
            const li = document.createElement('li');
            li.className = `page-item ${i === currentPage ? 'active' : ''}`;
            li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
            li.addEventListener('click', (e) => {
                e.preventDefault();
                currentPage = i;
                renderTable(currentPage);
                renderPagination();
            });
            pagination.appendChild(li);
        }

        // Next button
        const nextLi = document.createElement('li');
        nextLi.className = `page-item ${currentPage === totalPages ? 'disabled' : ''}`;
        nextLi.innerHTML = `<a class="page-link" href="#">Next</a>`;
        nextLi.addEventListener('click', (e) => {
            e.preventDefault();
            if (currentPage < totalPages) {
                currentPage++;
                renderTable(currentPage);
                renderPagination();
            }
        });
        pagination.appendChild(nextLi);
    }

    // Show voter details in modal
    function showVoterDetails(voterId) {
        const voter = allVoters.find(v => v.id == voterId);
        if (!voter) return;

        const modalContent = document.getElementById('voterDetailsContent');
        modalContent.innerHTML = `
            <div class="row mb-4">
                <div class="col-md-3 text-center">
                    <div class="bg-light rounded-circle d-flex align-items-center justify-content-center" style="width: 120px; height: 120px; margin: 0 auto;">
                        <i class="bi bi-person-fill" style="font-size: 3rem; color: #6c757d;"></i>
                    </div>
                    <h5 class="mt-3">${voter.firstname} ${voter.lastname}</h5>
                    <span class="badge ${voter.role === 'ADMIN' ? 'bg-danger' : 'bg-success'}">${voter.role}</span>
                </div>
                <div class="col-md-9">
                    <div class="row">
                        <div class="col-md-6">
                            <p class="mb-2"><span class="detail-label">Birth Date:</span> <span class="detail-value">${new Date(voter.birthdate).toLocaleDateString()}</span></p>
                            <p class="mb-2"><span class="detail-label">Gender:</span> <span class="detail-value">${voter.gender}</span></p>
                            <p class="mb-2"><span class="detail-label">Place of Birth:</span> <span class="detail-value">${voter.placeofbirth}</span></p>
                        </div>
                        <div class="col-md-6">
                            <p class="mb-2"><span class="detail-label">Location:</span> <span class="detail-value">${voter.location}</span></p>
                            <p class="mb-2"><span class="detail-label">Voter Number:</span> <span class="detail-value">${voter.number}</span></p>
                            <p class="mb-2"><span class="detail-label">Political Party:</span> <span class="detail-value badge bg-primary">${voter.party || 'None'}</span></p>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-md-4">
                            <p class="mb-2"><span class="detail-label">Region:</span> <span class="detail-value">${voter.region}</span></p>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-2"><span class="detail-label">Department:</span> <span class="detail-value">${voter.department}</span></p>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-2"><span class="detail-label">Arrondissement:</span> <span class="detail-value">${voter.arron}</span></p>
                        </div>
                    </div>
                </div>
            </div>
        `;

        const modal = new bootstrap.Modal(document.getElementById('voterDetailsModal'));
        modal.show();
    }
});