let allDayCheckBox = document.getElementById("allDay");
let startTimeDiv = document.getElementById("start-time-div");
let endTimeDiv = document.getElementById("end-time-div");
if (allDayCheckBox.checked) {
    onChangeAllDay();
}

function onAddTask() {
    if (allDayCheckBox.checked) {
        document.getElementById("startTime").value = '00:00';
        document.getElementById("endTime").value = "23:59";
    }
}

function onChangeAllDay() {
    startTimeDiv.classList.toggle('hidden');
    endTimeDiv.classList.toggle('hidden');
}