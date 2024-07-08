const deleteUserButtons = document.getElementsByClassName("delete-button");


for (let i = 0; i < deleteUserButtons.length; i++) {
    let element = deleteUserButtons[i];
    element.addEventListener('click', () => {
        let currentUserSection = getElementById(element.id + "-section");
        let allUsers = getElementById("users");
        allUsers.removeChild(currentUserSection);
        fetch('http://localhost:8080/users/api/' + element.id, {method: 'DELETE'})
            .catch(error => console.log('An error occurred' + error));
    });
}
function getElementById(id) {
    return document.getElementById(id);
}