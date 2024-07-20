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
