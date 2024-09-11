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
                <p><strong>투표자 수:</strong> ${vote.participantCount}</p>
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
    fetch('/api/v1/categories')
        .then(response => response.json())
        .then(data => {
            const categoryCheckboxes = document.getElementById('categoryCheckboxes');
            data.data.categories.forEach(category => {
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
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(errorData => {
                    // 에러 메시지를 추출하여 catch 블록으로 전달
                    throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
                });
            }
        })
        .then(data => {
            alert(data.message);
            location.reload();
        })
        .catch((error) => {
            console.error('Error:', error);
            alert(error);
        });
})

function openEditModal(element) {
    const voteId = element.getAttribute('data-id');

    fetch(`/admin/votes/${voteId}`)
        .then(response => response.json())
        .then(data => {
            const vote = data.data;

            document.getElementById('editVoteId').value = vote.voteId;
            document.getElementById('editTitle').value = vote.title;
            document.getElementById('editIsMultipleChoiceAllowed').checked = vote.isMultipleChoiceAllowed;
            document.getElementById('editWriter').value = vote.adminName;

            const editOptionsContainer = document.getElementById('editOptionsContainer');
            editOptionsContainer.innerHTML = '';
            vote.voteOptions.forEach(option => {
                const optionInput = document.createElement('input');
                optionInput.type = 'text';
                optionInput.name = 'voteOptions';
                optionInput.value = option.content;
                optionInput.placeholder = '투표 선택지는 최대 4개~';
                editOptionsContainer.appendChild(optionInput);
                editOptionsContainer.appendChild(document.createElement('br'));
            });

            const editCategoryCheckboxes = document.getElementById('editCategoryCheckboxes');
            editCategoryCheckboxes.innerHTML = '';
            fetch('/api/v1/categories')
                .then(response => response.json())
                .then(categoryData => {
                    categoryData.data.forEach(category => {
                        const categoryDiv = document.createElement('div');
                        categoryDiv.classList.add('category');

                        const checkbox = document.createElement('input');
                        checkbox.type = 'checkbox';
                        checkbox.id = `edit_category_${category.categoryId}`;
                        checkbox.name = 'categories';
                        checkbox.value = category.categoryId;
                        if (vote.categories.some(voteCategory => voteCategory.categoryId === category.categoryId)) {
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
                    console.error('Error:', error);
                    alert('Failed to fetch categories. Please try again later.');
                });

            // 참여자 수에 따른 경고 메시지 표시 및 입력 필드 비활성화
            const editWarningMessage = document.getElementById('editWarningMessage');
            const editVoteForm = document.getElementById('editVoteForm');
            if (vote.participantCount > 0) {
                editWarningMessage.style.display = 'block';
                Array.from(editVoteForm.elements).forEach(element => {
                    element.disabled = true;
                });
            } else {
                editWarningMessage.style.display = 'none';
                Array.from(editVoteForm.elements).forEach(element => {
                    element.disabled = false;
                });
            }

            document.getElementById("editModal").style.display = "block";
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to fetch vote details. Please try again later.');
        });
}

function closeEditModal() {
    document.getElementById("editModal").style.display = "none";
}

document.getElementById('editVoteForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(this);
    const voteId = formData.get('voteId');
    const voteOptions = Array.from(formData.getAll('voteOptions')).filter(option => option.trim() !== '');
    const categories = Array.from(formData.getAll('categories'));
    const writer = formData.get('writer');

    const voteRequest = {
        title: formData.get('title'),
        voteOptions: voteOptions,
        categoryIds: categories.map(Number),
        isMultipleChoiceAllowed: formData.get('isMultipleChoiceAllowed') === 'on',
        adminName: writer
    };

    fetch(`/admin/votes/${voteId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(voteRequest),
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(errorData => {
                    // 에러 메시지를 추출하여 catch 블록으로 전달
                    throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
                });
            }
        })
        .then(data => {
            alert(data.message);
            location.reload();
        })
        .catch((error) => {
            console.error('Error:', error);
            alert(error);
        });
});

function filterVotesByCategory() {
    // 선택된 카테고리의 값을 가져옵니다.
    const selectedCategory = document.getElementById('categorySelect').value;

    // 모든 투표 행을 가져옵니다.
    const voteRows = document.querySelectorAll('#votesTableBody tr');

    // 각 투표 행을 반복합니다.
    voteRows.forEach(row => {
        // 행의 데이터 속성에서 카테고리 ID 목록을 가져옵니다.
        const categoryIdsString = row.getAttribute('data-category-ids');

        // 데이터 속성에서 불필요한 문자를 제거하여 JSON 문자열로 변환합니다.
        const jsonString = '[' + categoryIdsString.replace('[', '').replace(']', '').replace(/'/g, '') + ']';

        // JSON 형식으로 파싱하여 카테고리 ID 목록을 가져옵니다.
        const categoryIds = JSON.parse(jsonString);

        // 선택된 카테고리 값과 데이터 속성 ID들을 출력해 확인합니다.
        console.log('Selected Category:', selectedCategory);
        console.log('Category IDs:', categoryIds);

        // 선택된 카테고리가 "all"이거나 카테고리 목록에 포함되어 있는지 확인합니다.
        if (selectedCategory === 'all' || categoryIds.includes(parseInt(selectedCategory))) {
            // 조건을 만족하면 행을 표시합니다.
            row.style.display = '';
        } else {
            // 조건을 만족하지 않으면 행을 숨깁니다.
            row.style.display = 'none';
        }
    });
}
