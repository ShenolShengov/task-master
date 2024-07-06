
let todayFormatted = getDateFormatted(new Date());
let yesterday = new Date();
yesterday.setDate(yesterday.getDate() - 1);
let yesterdayFormatted = getDateFormatted(yesterday);

Array.from(document.getElementsByClassName('min-today'))
    .forEach(e => e.min = todayFormatted);

Array.from(document.getElementsByClassName('max-today'))
    .forEach(e => e.max = todayFormatted);

Array.from(document.getElementsByClassName('min-yesterday'))
    .forEach(e => e.min = yesterdayFormatted);

Array.from(document.getElementsByClassName('max-yesterday'))
    .forEach(e => e.max = yesterdayFormatted);

Array.from(document.getElementsByClassName('today'))
    .forEach(e => e.value = todayFormatted);

Array.from(document.getElementsByClassName('yesterday'))
    .forEach(e => e.value = yesterdayFormatted);



function addLeadingZero(value) {
    return String(value).padStart(2, '0');
}
function getDateFormatted(date) {
    return `${date.getFullYear()}-${addLeadingZero(date.getMonth() + 1)}-${addLeadingZero(date.getDate())}`;
}
