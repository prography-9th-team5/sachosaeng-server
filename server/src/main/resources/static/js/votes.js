function openAddModal() {
    document.getElementById("modal").style.display = "block";
}

function closeAddModal() {
    document.getElementById("modal").style.display = "none";
}


function openDetailModal(element) {
    fetch(`/admin/votes/${element.getAttribute('data-id')}`)
        .then(response => response.json())
        .then(data => {
            const detailContent = document.getElementById('detailContent');
            const vote = data.data;

            let htmlContent = `
                <h2>투표 상세보기</h2>
                <p><strong>투표 토픽:</strong> ${vote.title}</p>
                <p><strong>카테고리:</strong> ${vote.categories.map(category => category.name).join(', ')}</p>
                <p><strong>복수선택:</strong> ${vote.isMultipleChoiceAllowed}</p>
                <p><strong>투표 옵션:</strong></p>
                <ul>
                    ${vote.voteOptions.map(option => `<li>${option.content}</li>`).join('')}
                </ul>
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
