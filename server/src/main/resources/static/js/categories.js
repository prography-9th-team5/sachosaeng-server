function addCategory() {
    var name = document.getElementById("name").value;
    var data = { "name": name };

    fetch('/categories', {
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
