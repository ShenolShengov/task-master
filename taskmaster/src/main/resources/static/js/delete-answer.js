
function removeAnswerSection(id) {
    document.getElementById('answers')
        .removeChild(document.getElementById(id + '-answer-section'));
}

function updateAnswerCount() {
    let answersCountLabel = document.getElementById('answersCount');
    answersCountLabel.textContent = (Number(answersCountLabel.textContent.split(' ')[0]) - 1) + ' Answer/s';
}

function deleteAnswer(id) {
    let isDeleted = true;
    console.log(id);
    fetch('http://localhost:8080/answers/' + id,
        {
            method: 'DELETE',
            headers: getHeaderWithCsrf()
        })
        .catch(() => isDeleted = false);
    if (isDeleted) {
        removeAnswerSection(id);
        updateAnswerCount();
    }
}