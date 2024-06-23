function openAddModal() {
    document.getElementById("modal").style.display = "block";
}

function closeAddModal() {
    document.getElementById("modal").style.display = "none";
}

document.addEventListener("DOMContentLoaded", function() {
    fetch('/categories')
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

document.getElementById('voteForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(this);
    const voteOptions = Array.from(formData.getAll('voteOptions')).filter(option => option.trim() !== '');;
    const categories = Array.from(formData.getAll('categories'));
    const writer = formData.get('writer');

    const voteRequest = {
        title: formData.get('title'),
        voteOptions: voteOptions,
        categoryIds: categories.map(Number),
        isMultipleChoiceAllowed: formData.get('isMultipleChoiceAllowed') === 'on',
        adminName: writer
    };

    fetch('/admin/votes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(voteRequest),
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            location.reload();
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('Failed to create vote.');
        });
})
