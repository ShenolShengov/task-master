let editButtons = document.getElementsByClassName('edit-button');

function getElementById(id) {
    return document.getElementById(id);
}

for (let i = 0; i < editButtons.length; i++) {
    let element = editButtons[i];

    element.addEventListener('click', () => {
        fetch("http://localhost:8080/users/api/" + element.id)
            .then(e => e.json())
            .then(json => {
                getElementById("id").value = json.id;
                getElementById('username').value = json.username;
                getElementById('fullName').value = json.fullName;
                getElementById('email').value = json.email;
                getElementById('age').value = json.age;
                getElementById('editSection').classList.toggle('hidden', false);
            })
            .catch(error => console.log('An error occurred' + error));
    })
}

