function getHeaderWithCsrf() {
    let headers = new Headers();
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;

    if (csrfHeaderName === null || csrfToken === null) {
        throw new Error('Csrf is not found!');
    }
    console.log(csrfHeaderName)
    console.log(csrfToken)
    headers.append(csrfHeaderName, csrfToken);
    return headers;
}