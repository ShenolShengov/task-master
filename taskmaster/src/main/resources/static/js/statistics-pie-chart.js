google.charts.load("current", {packages: ["corechart"]});
google.charts.setOnLoadCallback(drawUserChart);

function drawUserChart() {
    const adminUserCount = Number(document.getElementById('admin-users-count').textContent);
    const regularUserCount = Number(document.getElementById('regular-users-count').textContent);
    var data = google.visualization.arrayToDataTable([
        ['Users', 'Users Count'],
        ['Regular', regularUserCount],
        ['Admin', adminUserCount],
    ]);

    var options = {
        title: 'User roles',
        pieHole: 0.4,

    };

    var chart = new google.visualization.PieChart(document.getElementById('users-roles-donutchart'));
    chart.draw(data, options);
}

google.charts.load("current", {packages: ["corechart"]});
google.charts.setOnLoadCallback(drawTaskChart);

function drawTaskChart() {
    const taskCount = Number(document.getElementById('tasks-count').textContent);
    const questionsCount = Number(document.getElementById('questions-count').textContent);
    var data = google.visualization.arrayToDataTable([
        ['Tasks and questions', 'Count'],
        ['Tasks', taskCount],
        ['Questions', questionsCount],
    ]);

    var options = {
        title: 'Ratio between tasks and questions',
        pieHole: 0.4,

    };

    var chart = new google.visualization.PieChart(document.getElementById('tasks-types-donutchart'));
    chart.draw(data, options);
}

$(window).resize(function () {
    drawUserChart();
    drawTaskChart();
});