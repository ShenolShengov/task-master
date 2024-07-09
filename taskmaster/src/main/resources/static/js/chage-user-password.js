let changePasswordButtons = document.getElementsByClassName('change-password-button');


for (let i = 0; i < changePasswordButtons.length; i++) {
    let element = changePasswordButtons[i];

    element.addEventListener('click', () => {
        getElementById("changePasswordLabel").textContent = 'Change password - ' + element.dataset.username;
        getElementById("changePasswordUserId").value = element.id;
        getElementById('changePasswordSection').classList.toggle('hidden', false);
        getElementById('editSection').classList.add('hidden');
    });
}
function getElementById(id) {
    return document.getElementById(id);
}
