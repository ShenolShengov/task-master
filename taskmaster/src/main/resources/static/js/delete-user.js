const deleteUserButtons = document.getElementsByClassName("delete-button");


for (let i = 0; i < deleteUserButtons.length; i++) {
    let element = deleteUserButtons[i];
    element.addEventListener('click', () => {
        if (confirm("Are you sure you want to delete this user?")) {
            let currentUserSection = getElementById(element.id + "-section");
            let allUsers = getElementById("users");
            allUsers.removeChild(currentUserSection);
            let foundedUserLabel = getElementById('founded-user-label');
            foundedUserLabel.textContent = (Number(foundedUserLabel.textContent.split(' ')[0]) - 1) + ' users';
            fetch('http://localhost:8080/users/api/' + element.id, {
                method: 'DELETE',
                headers: getHeaderWithCsrf()
            })
                .catch(error => console.log('An error occurred' + error));
        }
    });
}

function getElementById(id) {
    return document.getElementById(id);
}