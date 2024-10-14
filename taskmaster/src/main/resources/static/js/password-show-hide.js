const passwordInput = document.getElementById("password");
const  passwordShower = document.getElementById("password-shower");
const  passwordHider = document.getElementById("password-hider");

passwordShower.addEventListener("click", (event) => {
    passwordInput.type = "text";
    passwordShower.setAttribute("style", "display: none");
    passwordHider.setAttribute("style", "display: block");
});

passwordHider.addEventListener("click", (event) => {
    passwordInput.type = "password";
    passwordHider.setAttribute("style", "display: none");
    passwordShower.setAttribute("style", "display: block");
});