const sBtn = document.querySelector('.btn-primary');

sBtn.onclick = function(){
    var mls = document.getElementById("mls").value;
    console.log(mls);
    sessionStorage.setItem("mls", mls);
    location.href = "http://localhost:8080/listing_data"
}

