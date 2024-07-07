let switchTasksBtn = document.getElementById('switch-tasks');
let futureLabel = document.getElementById("future-label");
let prevLabel = document.getElementById('prev-label');
let dateWrapper = document.getElementById('date-wrapper');
switchTasksBtn.addEventListener('click', () => {
    if (switchTasksBtn.textContent.includes('Previous')) {
        futureLabel.classList.add('hidden');
        prevLabel.classList.toggle('hidden');
        dateWrapper.classList.toggle('hidden');
        switchTasksBtn.textContent = 'Switch to Future Tasks';
        prevLabel.scrollIntoView({block: "start", inline: "nearest",behavior: "smooth"});
    } else {
        futureLabel.classList.toggle('hidden');
        prevLabel.classList.add('hidden');
        dateWrapper.classList.add('hidden');
        switchTasksBtn.textContent = 'Switch to Previous Tasks';
        futureLabel.scrollIntoView({block: "start", inline: "nearest",behavior: "smooth"});
    }
    // window.scroll({
    //     top: 0,
    //     left: 0,
    //     behavior: 'smooth'
    // });
});

// document.getElementById('choose-date').addEventListener('change', () =>{
//     window.location.replace('http://localhost:8080/');
// });
