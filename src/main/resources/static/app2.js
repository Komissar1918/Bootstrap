$(document).ready ( function () {
    const authUserBody = document.getElementById("user-admin-body");
    const authUserHeader = document.getElementById("auth-user");

    fetch('http://localhost:8080/user/all')
        .then(response => response.json())
        .then(cars => {
            console.log(cars)
            displayUser(cars);
        }).catch(error => console.error(error));


    function displayUser(person) {
        console.log(person)
        // authUserHeader.textContent = person.email + ' with roles ' + person.roles.map(role => role.name.replaceAll('ROLE_', '')).join(', ');
        authUserBody.innerHTML = '';

        for(let i = 0; i < person.length; i++){
            const car = document.createElement('tr');
            car.innerHTML = `
                <td>${person[i].id}</td>
                <td>${person[i].model}</td>
                <td>${person[i].yearOfRelease}</td>
               <td></td>`;
            authUserBody.appendChild(car);
        }
    }
});