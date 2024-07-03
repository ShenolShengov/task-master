let date = new Date();

let day = String(date.getDate()).padStart(2, '0');
let month = String(date.getMonth()).padStart(2, '0');
let year = date.getFullYear();
document.getElementById('dueDate').value = `${year}-${month}-${day}`;
// 2024-07-10
