const expandButton = document.getElementById('expand-completed-tasks');
const completedTasks = document.getElementById('completed-tasks');
expandButton.addEventListener('click', () => {
    if (expandButton.textContent.includes('Show')) {
        expandButton.textContent = "Close Completed Tasks"
        completedTasks.classList.toggle('hidden');
    } else {
        expandButton.textContent = "Show Completed Tasks"
        completedTasks.classList.add('hidden');
    }
});