function openAddModal() {
    document.getElementById("modal").style.display = "block";
}

function closeAddModal() {
    document.getElementById("modal").style.display = "none";
}

document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/v1/categories')
        .then(response => response.json())
        .then(data => {
            const categoryCheckboxes = document.getElementById('categoryCheckboxes');
            data.data.forEach(category => {
                const categoryDiv = document.createElement('div');
                categoryDiv.classList.add('category');

                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.id = `category_${category.categoryId}`;
                checkbox.name = 'categories';
                checkbox.value = category.categoryId;

                const label = document.createElement('label');
                label.htmlFor = `category_${category.categoryId}`;
                label.textContent = category.name;

                categoryDiv.appendChild(checkbox);
                categoryDiv.appendChild(label);
                categoryCheckboxes.appendChild(categoryDiv);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to fetch categories. Please try again later.');
        });
});

document.getElementById('informationForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const formData = new FormData(this);
    const categories = Array.from(formData.getAll('categories'));

    const requestBody = {
        title: formData.get('title'),
        content: formData.get('content'),
        categoryIds: categories.map(Number),
        referenceName: formData.get('referenceName'),
        referenceUrl: formData.get('referenceUrl')
    };

    fetch('/admin/information', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message); // Show success message or handle response accordingly
            location.reload();// Optionally, you can redirect or update the UI
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
});


function openDetailModal(element) {
    fetch(`/admin/information/${element.getAttribute('data-id')}`)
        .then(response => response.json())
        .then(data => {
            const detailContent = document.getElementById('detailContent');
            const information = data.data;
            const formattedContent = information.content.replace(/\n/g, '<br>');

            let htmlContent = `
                <h2>콘텐츠 상세보기</h2>
                <p><strong>제목:</strong> ${information.title}</p>
                <p><strong>카테고리:</strong> ${information.categories.map(category => category.name).join(', ')}</p>
                <p><strong>콘텐츠 내용:</strong> ${formattedContent}</p>
                <p><strong>출처:</strong> ${information.referenceName} </p>
                <p><strong>출처 url:</strong> ${information.referenceUrl} </p>
            `;
            detailContent.innerHTML = htmlContent;

            document.getElementById("detailModal").style.display = "block";
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to fetch vote details. Please try again later.');
        });
}

function closeDetailModal() {
    document.getElementById("detailModal").style.display = "none";
}

function openEditModal(button) {
    const informationId = button.getAttribute('data-id');

    // Fetch the current data for the selected information
    fetch(`/admin/information/${informationId}`)
        .then(response => response.json())
        .then(data => {
            const information = data.data;

            // Populate the edit form with current data
            document.getElementById('editInformationId').value = informationId;
            document.getElementById('editTitle').value = information.title;
            document.getElementById('editContent').value = information.content;
            document.getElementById('editReferenceName').value = information.referenceName;
            document.getElementById('editReferenceUrl').value = information.referenceUrl;

            // Load categories and mark them
            console.log('Selected categories for editing:', information.categories);
            const selectedCategories = information.categories || [];
            console.log('Selected categories for editing:', selectedCategories);
            loadEditCategories(selectedCategories);

            // Open the modal
            document.getElementById("editModal").style.display = "block";
        })
        .catch(error => {
            console.error('Error fetching information:', error);
            alert('Failed to fetch information details. Please try again later.' + error);
        });
}

// Close the edit modal
function closeEditModal() {
    document.getElementById("editModal").style.display = "none";
}

// Load and mark categories for editing
function loadEditCategories(selectedCategories) {
    fetch('/api/v1/categories')
        .then(response => response.json())
        .then(data => {
            const editCategoryCheckboxes = document.getElementById('editCategoryCheckboxes');
            editCategoryCheckboxes.innerHTML = ''; // Clear existing categories

            data.data.forEach(category => {
                const categoryDiv = document.createElement('div');
                categoryDiv.classList.add('category');

                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.id = `category_${category.categoryId}`;
                checkbox.name = 'categories';
                checkbox.value = category.categoryId;
                if (selectedCategories.some(selectedCategory => selectedCategory.categoryId === category.categoryId)) {
                    checkbox.checked = true;
                }

                const label = document.createElement('label');
                label.htmlFor = `edit_category_${category.categoryId}`;
                label.textContent = category.name;

                categoryDiv.appendChild(checkbox);
                categoryDiv.appendChild(label);
                editCategoryCheckboxes.appendChild(categoryDiv);
            });
        })
        .catch(error => {
            console.error('Error loading categories:', error);
            alert('Failed to load categories for editing. Please try again later.' + error);
        });
}

document.getElementById('editInformationForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const formData = new FormData(this);
    const categories = Array.from(formData.getAll('categories'));
    console.log(categories)

    const requestBody = {
        title: formData.get('title'),
        content: formData.get('content'),
        categoryIds: categories.map(Number),
        referenceName: formData.get('referenceName'),
        referenceUrl: formData.get('referenceUrl')
    };

    const informationId = formData.get('informationId');

    fetch(`/admin/information/${informationId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message); // Show success message or handle response accordingly
            location.reload(); // Optionally, you can redirect or update the UI
        })
        .catch(error => {
            console.error('Error updating information:', error);
            alert('An error occurred. Please try again later.');
        });
});
