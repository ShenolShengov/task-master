const profilePictureInput = document.getElementById('profilePicture');
const profilePicturePreview = document.getElementById('profilePicturePreview');
const profilePictureLabel = document.getElementById('profilePicturePreviewLabel');

if (!profilePicturePreview.src.includes('default-profile-picture')) {
    handleResumeInput(profilePicturePreview.src);
}
profilePictureInput.onchange = () => {
    if (profilePictureInput.files[0]) {
        profilePicturePreview.src = URL.createObjectURL(profilePictureInput.files[0]);
        profilePictureLabel.textContent = 'Change picture';
    }
};

function setDefaultPicture() {
    profilePicturePreview.src = '/images/default-profile-picture.png';
    profilePictureLabel.textContent = 'Upload from device';
    profilePictureInput.type = 'text';
    profilePictureInput.type = 'file';
}

async function handleResumeInput(remoteResumeURL) {
    const designFile = await createFile(remoteResumeURL);
    const input = document.querySelector('input[type="file"]');
    const dt = new DataTransfer();
    dt.items.add(designFile);
    input.files = dt.files;
    const event = new Event("change", {
        bubbles: !0,
    });
    input.dispatchEvent(event);
}

async function createFile(url) {
    let response = await fetch(url);
    let data = await response.blob();
    let metadata = {
        type: "image/png",
    };
    return new File([data], "current-picture.png", metadata);
}