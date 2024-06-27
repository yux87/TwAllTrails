async function initMap() {
  const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 8,
      center: { lat: 23.2339, lng: 120.9637 },
  });

  // Other initialization code...

  const infoWindow = new google.maps.InfoWindow({
    content: "",
    disableAutoPan: true,
  });

  const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  const response = await fetch('../../js/trail/trailsCoordinateAndDetail.json');
  const locations = await response.json();

  let markerDetailsMap = new Map(); // This makes it accessible in all functions


  const markersJson = locations.map((location, i) => {
    try {
      // Make sure the place_coordinate exists and is valid
      if (!location.place_coordinate || typeof location.place_coordinate.lat !== 'number' || typeof location.place_coordinate.lng !== 'number') {
        throw new Error('Invalid location data');
      }

      const label = labels[i % labels.length];
      const marker = new google.maps.Marker({
        position: location.place_coordinate,
        // label,  
      });



   // Associate marker with details
   markerDetailsMap.set(marker, locations[i]); // Assuming locations is the array of details corresponding to each marker





      //點擊marker顯示資訊
      marker.addListener("click",async () => {
        console.log("location:")
        console.log(location.place_name)

        //API call to fetch trails
        let url = "/dto/getTrailByTname.controller/"+location.place_name;
        try {
          const response = await axios.get(url); // Wait for the HTTP request to complete
          const trails = response.data; // Assign the response data to trails
          
          // Log the trails object to console for debugging
          // console.log(response.data[0].name)
          // console.log(response.data[0].id)
          console.log("trails:");
          console.log(trails);
      
          // const contentString = '<div><h3><a href="/toDetailPage?id='+ response.data[0].id +'">' + response.data[0].name + '</a></h3><p>' + location.place_coordinate.lat + '</p></div>';

          // const contentString = '<div><img src="'+trails[0].photo+'" width="40%" height="20%"/></div><div><h3><a href="/toDetailPage?id='+ trails[0].id +'">' + trails[0].name + '</a></h3><p>' + location.place_coordinate.lat + '</p></div>';
          const contentString = '<div><img src="'+trails[0].photo+'" width="100px" height="20%"/><h3><a href="/toDetailPage?id='+ trails[0].id +'">' + trails[0].name + '</a></h3><p>' + location.place_coordinate.lat + '</p></div>';
          infoWindow.setContent(contentString);


          // return trails; // Return the trails object
        } catch (error) {
          // Log any errors
          console.log("error", error);
      
          // Depending on your error handling strategy, you might want to rethrow the error or return a default value
          throw error; // Rethrow the error or return an appropriate value
        }


        // const contentString = '<div><h3>' + location.place_name + '</h3><p>' + location.place_coordinate.lat + '</p></div>';
        // const contentString = '<div><h3><a href="/trail.controller">' + location.place_name + '</a></h3><p>' + location.place_coordinate.lat + '</p></div>';
        


        // fetch('/123/1')
        // .then(response => {
        //   if (!response.ok) {
        //     throw new Error('Network response was not ok');
        //   }
        //   return response.json(); // 解析 JSON 数据
        // })
        // .then(data => {
        //   // 处理从服务器返回的数据
        //   console.log(data);
        // })
        // .catch(error => {
        //   console.error('There has been a problem with your fetch operation:', error);
        // });
      


        
        // infoWindow.setContent(contentString);

        // infoWindow.setContent(label);
        infoWindow.open(map, marker);
      });





      return marker;
    } catch (error) {
      console.error('Error creating marker for location:', location, error);
      return null; // Return null for markersJson that couldn't be created
    }
  }).filter(marker => marker !== null); // Filter out the null markersJson

  new MarkerClusterer(map, markersJson, { imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m' });








  function debounce(func, wait) {
    let timeout;

    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };

        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}







  // Here comes the new part
  const input = document.getElementById("place-input");
  const searchBox = new google.maps.places.SearchBox(input);
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

  map.addListener("bounds_changed", debounce(() => {

    //a. 搜尋框改變
    // searchBox.setBounds(map.getBounds());

    //b. 監聽 all the time
    // Debounce the execution to avoid excessive calls during continuous events like dragging
    // debounce(getMarkerDetailsInBounds, 3000)();



    // This code will only run after the user has stopped changing bounds for 1000ms
    //c.
    getMarkerDetailsInBounds();
  }, 1000)); // Adjust the 1000ms delay as needed

  let markers = []; // Re-declare markers array here if it's not already declared at a higher scope
  let markerDetails = {}; // details of markers within the current map bounds
                          // Assuming this object holds details for each marker, keyed by marker ID or some unique identifier



// Assuming these variables are defined in your wider scope
let currentIndex = 0; // Keeps track of the current index in the array of markers within bounds
const pageSize = 5; // Number of markers' details to load per click
let detailsWithinBounds = []; // Stores all details within bounds
let currentDetailsToShow = []; // Stores the current page of details to show

// Function to retrieve details of markers within the current map bounds
function getMarkerDetailsInBounds() {
  const bounds = map.getBounds();
  detailsWithinBounds = []; // Reset this array every time the function is called
  
  vueApp.callEmpty()


  markerDetailsMap.forEach((details, marker) => {
    if (bounds.contains(marker.getPosition())) {
      detailsWithinBounds.push(details);
    }
  });

  // Reset current index and displayed details because bounds might have changed
  currentIndex = 0;
  currentDetailsToShow = detailsWithinBounds.slice(0, pageSize); // Initially load details for the first set of markers
  displayDetails(currentDetailsToShow); // Function to display details on your UI

  // Log the initial load (optional)
  console.log("Initial load of marker details:", currentDetailsToShow);
}



// Function to load more details when "load more" is clicked
function loadMoreDetails() {
  currentIndex += pageSize; // Move the index to the next set of details
  const nextPageDetails = detailsWithinBounds.slice(currentIndex, currentIndex + pageSize);
  currentDetailsToShow = currentDetailsToShow.concat(nextPageDetails); // Add the new details to the currently displayed list
  displayDetails(nextPageDetails); // Update the UI with the new details

  // Log the update (optional)
  console.log("Loaded more marker details:", nextPageDetails);
}
// attach the function to the window object. This makes it globally accessible
window.loadMoreDetails = loadMoreDetails;




// Example display function (replace with your actual UI update logic)
function displayDetails(details) {
  // Your code to update the UI with the details
  // For example, appending details to a list, updating a map, etc.
  details.forEach(detail => {
    console.log("------------------   地點名為:", detail.place_name);
    vueApp.callFindATrail(detail.place_name);
  });
}

// Event listener for the "load more" button
// document.getElementById('loadMoreButton').addEventListener('click', loadMoreDetails);

  




// Example debounce function to limit how often your function is called
function debounce(func, wait, immediate) {
  var timeout;
  return function() {
      var context = this, args = arguments;
      var later = function() {
          timeout = null;
          if (!immediate) func.apply(context, args);
      };
      var callNow = immediate && !timeout;
      clearTimeout(timeout);
      timeout = setTimeout(later, wait);
      if (callNow) func.apply(context, args);
  };
}







  //update sidebar
  searchBox.addListener("places_changed", () => {
    const places = searchBox.getPlaces();
    // console.log(places)
    if (places.length === 0) {
      return;
    }
  
    // Clear out the old markers.
    markers.forEach((marker) => marker.setMap(null));
    markers = [];
    const bounds = new google.maps.LatLngBounds();
    places.forEach((place) => {
      if (!place.geometry || !place.geometry.location) {
        console.log("Returned place contains no geometry");
        return;
      }
      // Here, instead of directly working with markers,
      // you could call a function to fetch trail data near this location and update the sidebar
      console.log("places:")
      console.log(place.formatted_address)
      // updateSidebarWithPlaceInfo(place);
  
      if (place.geometry.viewport) {
        // Only geocodes have viewport.
        bounds.union(place.geometry.viewport);
      } else {
        bounds.extend(place.geometry.location);
      }
    });
    map.fitBounds(bounds);


    // After updating the map's bounds:
    getMarkerDetailsInBounds(); // This will log the details of markers within the new bounds
  });









  
  // Function to update the sidebar with place information
  function updateSidebarWithPlaceInfo(place) {
    // Fetch trail data related to the selected place
    // For demonstration, let's assume we have a function `fetchTrailData`
    fetchTrailData(place.geometry.location).then(trailData => {
      //顯示範例json (下方)
      // console.log(trailData)
      
      // Assuming `trailData` is an array of trail objects
      // Clear existing sidebar content
      const sidebar = document.querySelector('.sidebar');
      sidebar.innerHTML = ''; // Clear current content
  
      // Add new trail data to the sidebar
      trailData.forEach(trail => {
        const trailElement = document.createElement('div');
        trailElement.className = 'location-item';
        trailElement.innerHTML = `<h3>${trail.name}</h3><p>${trail.description}</p>`;
        sidebar.appendChild(trailElement);
      });
    }).catch(error => {
      console.error('Error updating sidebar:', error);
    });
  }
  
  // Example fetchTrailData function
  async function fetchTrailData(location) {
    // Your API call to fetch trails based on location
    // For demonstration purposes, this returns a Promise that resolves with dummy data
    return Promise.resolve([
      { name: 'Trail 1', description: 'A beautiful trail.' },
      { name: 'Trail 2', description: 'An exciting adventure.' }
      // More trails...
    ]);
  }
  
  
  // Rest of your marker and clustering logic...








}



window.initMap = initMap;
