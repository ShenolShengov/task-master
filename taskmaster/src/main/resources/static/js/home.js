
function searchTaskByDueDate(dueDate) {
    reloadUrlWith("task_due_date", dueDate)
}

function searchQuestionByCreatedTime(createTime) {
    reloadUrlWith('question_created_time', createTime)
}

function reloadUrlWith(paramName, paramValue) {
    let url = getUrl();
    url.searchParams.set(paramName, paramValue);
    reload(url);
}
