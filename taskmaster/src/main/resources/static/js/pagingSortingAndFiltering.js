function getUrl() {
    return new URL(window.location);
}

function reload(url) {
    window.location.replace(url.href);
}

function nextPage() {
    let url = getUrl();
    let newPage = url.searchParams.get('page') == null ? 2 : String(Number(url.searchParams.get('page')) + 1);
    url.searchParams.set("page", newPage);
    reload(url);
}

function prevPage() {
    let url = getUrl();
    let newPage = url.searchParams.get('page') == null ? 0 : String(Number(url.searchParams.get('page')) - 1);
    url.searchParams.set("page", newPage);
    reload(url);
}

// function sortProperties(by) {
//     let url = getUrl();
//     if (by === 'none') {
//         url.searchParams.delete('sort_properties');
//     } else {
//         url.searchParams.set('sort_properties', by);
//     }
//     reload(url);
// }


function sort(properties, direction) {
    let url = getUrl();
    url.searchParams.set('sort', (properties + ',' + direction));
    reload(url);
}

// function sortDirection(direction) {
//     let url = getUrl();
//     url.searchParams.set('sort_direction', direction);
//     reload(url);
// }

function search(search) {
    let url = getUrl();
    if (!url.href.includes('/search')) {
        url.href = 'http://localhost:8080/users/search';
    }
    url.searchParams.set("search_query", search);
    reload(url);
}

