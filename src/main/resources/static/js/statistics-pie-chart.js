google.charts.load("current", {packages: ["corechart"]});
google.charts.setOnLoadCallback(drawUserChart);
function drawUserChart() {
    var data = google.visualization.arrayToDataTable([
        ['Users', 'Users Count'],
        ['Regular', 57],
        ['Admin', 33],
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
    var data = google.visualization.arrayToDataTable([
        ['Tasks', 'Tasks Count'],
        ['Personal', 57],
        ['Open', 33],
    ]);

    var options = {
        title: 'Task Type',
        pieHole: 0.4,

    };

    var chart = new google.visualization.PieChart(document.getElementById('tasks-types-donutchart'));
    chart.draw(data, options);
}

$(window).resize(function(){
    drawUserChart();
    drawTaskChart();
});