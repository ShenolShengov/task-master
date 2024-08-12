let allDayCheckBox = document.getElementById("allDay");
let startTimeDiv = document.getElementById("start-time-div");
let endTimeDiv = document.getElementById("end-time-div");
const startTimeInput = document.getElementById("startTime");
const endTimeInput = document.getElementById("endTime");
if (allDayCheckBox.checked) {
    onChangeAllDay();
    startTimeInput.value = '';
    endTimeInput.value = '';
}

function onAddEditTask() {
    if (allDayCheckBox.checked) {
        startTimeInput.value = '00:00';
        endTimeInput.value = "23:59";
    }
}

function onChangeAllDay() {
    startTimeDiv.classList.toggle('hidden');
    endTimeDiv.classList.toggle('hidden');
}