let mapOptions = {
    center:[42.63606,23.36979],
    zoom: 20
}

var map = L.map('map', mapOptions);
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

let marker = new L.Marker([42.63606,23.36979]);
marker.addTo(map);