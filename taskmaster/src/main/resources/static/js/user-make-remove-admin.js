const makeRemoveAdminButtons = document.getElementsByClassName("make-remove-admin");


for (let i = 0; i < makeRemoveAdminButtons.length; i++) {
    let element = makeRemoveAdminButtons[i];
    element.addEventListener('click', () => {
        let userRole = getElementById(element.id + '-role');
        if (element.textContent.includes('Make')) {
            fetch('http://localhost:8080/users/api/make-admin/' + element.id, {
                method: 'PATCH',
                headers: getHeaderWithCsrf()
            })
                .catch(error => console.log('An error occurred' + error));
            element.textContent = 'Remove Admin';
            userRole.textContent = 'Admin';
        } else {
            fetch('http://localhost:8080/users/api/remove-admin/' + element.id, {
                method: 'PATCH',
                headers: getHeaderWithCsrf()
            })
                .catch(error => console.log('An error occurred' + error));
            element.textContent = 'Make Admin';
            userRole.textContent = 'User';
        }
    });
}

function getElementById(id) {
    return document.getElementById(id);
}
