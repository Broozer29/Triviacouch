document.querySelector("#startspel").addEventListener("click", function () {

    fetch("api/vragen", { method: 'GET' })
        .then(response => response.json())
        .then(function (myJson) { console.log(myJson); });
});



