var mls = sessionStorage.getItem("mls")
var hasData = true;

var viewall_api_url = "http://localhost:8080/api/v1/property/viewalisting?mls=" + mls;

async function getAPI(url){
    const response = await fetch(url);
    var data = await response.json();
    console.log(data);
    if (data.error != null) {
        hasData = false;
    }
    console.log(hasData);
    show(data, hasData);
}


getAPI(viewall_api_url);

function show(data, hasData){
    var title = document.getElementsByClassName('title')
    if (hasData) {
        title[0].textContent = "Details for property #" + mls
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

        tab += `
            <tr>
                <td>${data.mls}</td>
                <td>${data.price}</td>
                <td>${data.address}</td>
                <td>${data.propertyType}</td>
                <td>${data.bedrooms}</td>
                <td>${data.bathrooms}</td>
                <td>${data.yearBuilt}</td>
                <td>${data.listingUserID}</td>
            </tr>`;

        document.getElementById("listings").innerHTML = tab;

        const addBtn = document.createElement("button");
        addBtn.textContent = "Add to Favorites";
        addBtn.classList = "add";
        const delBtn = document.createElement("button");
        delBtn.textContent = "Remove from Favorites";
        delBtn.classList = "remove";

        const body = document.querySelector('body');
        const status = document.createElement("h4");
        body.appendChild(addBtn);
        body.appendChild(delBtn);
        body.appendChild(status);

        addBtn.addEventListener('click', function(e){
            var add_to_fav = "http://localhost:8080/api/v1/user/addtofavourites?mls=" + mls;
            fetch(add_to_fav);
            status.textContent = "Property added successfully";
        })

        delBtn.addEventListener('click', function(e){
            var remove_fav = "http://localhost:8080/api/v1/user/removefromfavourites?mls=" + mls;
            fetch(remove_fav);
            status.textContent = "Property removed successfully";
        })

    }
    else{
        title[0].textContent = "This property does not exist!";
    }
}

