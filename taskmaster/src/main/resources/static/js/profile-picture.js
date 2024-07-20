const profilePictureInput = document.getElementById('profilePicture');
const profilePicturePreview = document.getElementById('profilePicturePreview');
const profilePictureLabel = document.getElementById('profilePicturePreviewLabel');
profilePictureInput.onchange = () => {
    if (profilePictureInput.files[0]) {
        profilePicturePreview.src = URL.createObjectURL(profilePictureInput.files[0]);
        profilePictureLabel.textContent = 'Change picture';
    }
};

function setDefaultPicture() {
    profilePicturePreview.src = '/images/default-profile-picture.png';
    profilePictureLabel.textContent = 'Choose picture from device';
}