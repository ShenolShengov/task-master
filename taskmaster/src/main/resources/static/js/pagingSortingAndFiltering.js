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

function sortWithPrefix(prefix, properties, direction) {
    let url = getUrl();
    url.searchParams.set(prefix + 'sort', (properties + ',' + direction));
    reload(url);
}

function sort(properties, direction) {
    sortWithPrefix("", properties, direction);
}

function search(search) {
    let url = getUrl();
    if (!url.searchParams.has('search_query') || url.searchParams.get('search_query') !== search) {
        // url.href = url.href.split('?')[0];
        url.searchParams.delete('page');
    }
    url.searchParams.set("search_query", search);
    reload(url);
}

function getElementById(id) {
    return document.getElementById(id);
}


