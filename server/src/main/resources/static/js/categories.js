function addCategory() {
    var name = document.getElementById("name").value;
    var data = { "name": name };

    fetch('/admin/categories', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                location.reload(); // 성공 시 페이지 새로고침
            } else {
                throw new Error('Failed to add category');
            }
        })
        .catch(error => {
            alert('카테고리 추가 실패');
            console.error('Error adding category:', error);
        });
}

function openEditModal(element) {
    var id = element.getAttribute('data-id');
    var name = element.getAttribute('data-name');
    var userTypes = element.getAttribute('data-user-types')
        .replace('[', '')
        .replace(']', '')
        .split(', ');

    document.getElementById("modal").style.display = "block";
    document.getElementById("editId").value = id;
    document.getElementById("editName").value = name;

    // 체크박스 초기화
    var checkboxes = document.getElementsByName('userTypes');
    checkboxes.forEach(checkbox => {
        checkbox.checked = false; // 모든 체크박스 초기화
    });

    // 기존 userType 체크
    userTypes.forEach(type => {
        console.log(type);
        var checkbox = document.getElementById(type);
        if (checkbox) {
            checkbox.checked = true;
        }
    });
}

function closeModal() {
    document.getElementById("modal").style.display = 'none';
}

function saveChanges() {
    var id = document.getElementById("editId").value;
    var name = document.getElementById("editName").value;
    var userTypes = [];

    // 선택된 userType 수집
    var checkboxes = document.getElementsByName('userTypes');
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            userTypes.push(checkbox.value);
        }
    });

    var data = {
        "name": name,
        "userTypes": userTypes
    };

    fetch('/admin/categories/' + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                closeModal();
                location.reload(); // 성공 시 페이지 새로고침
            } else {
                throw new Error('Failed to update category');
            }
        })
        .catch(error => {
            alert('카테고리 업데이트 실패');
            console.error('Error updating category:', error);
        });
}
