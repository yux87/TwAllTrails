// Include Axios via a script tag in your HTML or install via npm in a more complex project setup
function fetchLikedTrail(trailId, callback, errorCallback) {
    let url = `/countTrailLikes/${trailId}`;
    axios.get(url)
        .then(response => callback(response.data))
        .catch(error => errorCallback(error));
}

// Since we're including this script directly into our HTML, we attach it to the window object
window.fetchLikedTrail = fetchLikedTrail;
