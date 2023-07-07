var viewall_api_url = "http://localhost:8080/api/v1/property/viewalllistings";

async function getAPI(url){
    const response = await fetch(url);
    var data = await response.json();
    console.log(data);
    show(data);
}


getAPI(viewall_api_url);

function show(data){
    let tab = 
        `<tr> 
            <th>MLS</th>
            <th>Price</th>
            <th>Address</th>
            <th>Property Type</th>
            <th>Bed</th>
            <th>Bath</th>
            <th>Year Built</th>
            <th>Seller Email</th>
        </tr>`;

    for (let r in data){
        tab += `
            <tr>
                <td>${data[r].mls}</td>
                <td>${data[r].price}</td>
                <td>${data[r].address}</td>
                <td>${data[r].propertyType}</td>
                <td>${data[r].bedrooms}</td>
                <td>${data[r].bathrooms}</td>
                <td>${data[r].yearBuilt}</td>
                <td>${data[r].listingUserID}</td>
            </tr>`;
    }

    document.getElementById("listings").innerHTML = tab;
}

const sortBtn = document.querySelector('.sort');
sortBtn.addEventListener('click', function(e){
    viewall_api_url = "http://localhost:8080/api/v1/property/viewalllistingssorted";
    getAPI(viewall_api_url);
});

const unsortBtn = document.querySelector('.unsort');
unsortBtn.addEventListener('click', function(e){
    viewall_api_url = "http://localhost:8080/api/v1/property/viewalllistings";
    getAPI(viewall_api_url);
});