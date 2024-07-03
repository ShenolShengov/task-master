let date = new Date();
date.setDate(date.getDate() - 1);

let day = String(date.getDate()).padStart(2, '0');
let month = String(date.getMonth()).padStart(2, '0');
let year = date.getFullYear();
document.getElementById('choose-date').value = `${year}-${month}-${day}`;