let editButtons = document.getElementsByClassName('edit-button');
let usernameInput = document.getElementById('username');
let fullNameInput = document.getElementById('fullName');
let emailInput = document.getElementById('email');
let ageInput = document.getElementById('age');
for (let i = 0; i < editButtons.length; i++) {
    let element = editButtons[i];
    // fetch('http://localhost:8080/api/uses/' + element.id);
    editButtons[i].addEventListener('click', () => {
        document.getElementById('editSection').classList.toggle('hidden', false);
        usernameInput.value = "Shenol10";
        fullNameInput.value = "Shenol Shengov";
        emailInput.value = "shenolshengov41@gmail.com";
        ageInput.value = '20';
    })
}