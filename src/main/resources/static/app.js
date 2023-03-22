$(document).ready ( function () {
    const authUserBody = document.getElementById("user-admin-body");
    const authUserHeader = document.getElementById("auth-user");
    //Константы для модального окна EDIT
    const editId = document.getElementById("edit-id");
    const editModel = document.getElementById("edit-model");
    const editYearOfRelease = document.getElementById("edit-yearOfRelease");
    let editModal = new bootstrap.Modal(document.getElementById('edit-modal'));

    //Константы для модального окна DELETE
    const deleteId = document.getElementById("delete-id");
    const deleteModel = document.getElementById("delete-model");
    const deleteYearOfRelease = document.getElementById("delete-yearOfRelease");
    let deleteModal = new bootstrap.Modal(document.getElementById('delete-modal'));

    //Константы для модального окна CREATE

    const newModel = document.getElementById("new-model");
    const newYearOfRelease = document.getElementById("new-yearOfRelease");
    let responseRoleData;
    const fromSelectRole = document.getElementById("edit-role")
    $.ajax({url: "http://localhost:8080/admin/roles", success: function(result){
            responseRoleData =  result;
            fromSelectRole.innerHTML = '';
            for (let i = 0; i < responseRoleData.length; i++) {
                fromSelectRole.append(new Option( responseRoleData[i].name, responseRoleData[i].id));
                // console.log(responseRoleData[i]);
            }
        }});
    let respRolesData = null;
    let formSelectCreate = document.getElementById("newRole");
    $.ajax({url: "http://localhost:8080/admin/roles", success: function(result){
            respRolesData =  result;
            formSelectCreate.innerHTML = '';
            for (let i = 0; i < respRolesData.length; i++) {
                formSelectCreate.append(new Option( respRolesData[i].name, respRolesData[i].id));
                console.log(respRolesData[i]);
            }
        }});
    fetch('http://localhost:8080/admin/all')
        .then(response => response.json())
        .then(car => {
            displayUsers(car);
            // console.log(users);
        })
        .catch(error => console.error(error));

    fetch('http://localhost:8080/user/all')
        .then(response => response.json())
        .then(car => {
            // console.log(user)
            displayUser(car);
        }).catch(error => console.error(error));


    $(document).on ("click", ".edit-btn", function () {
        const id = $(this).data("id")
        console.log(id);
        openEditModal(id);

    });

    function displayUsers(cars) {
        const usersListBody = document.querySelector(".tbodyAdminUserList")
        usersListBody.innerHTML = "";
        for(let i = 0; i < cars.length; i++) {
            const userEl = document.createElement("tr");
            const editBtn = document.createElement("td");
            const deleteBtn = document.createElement("td");

            userEl.innerHTML = `
                    <td><p>${cars[i].id}</td>
                    <td>${cars[i].model}</p></td>
                    <td><p>${cars[i].yearOfRelease}</p></td>`;
            editBtn.innerHTML = `<button data-id="${cars[i].id}" type="button" class="btn btn-primary edit-btn" >Edit</button>`;
            deleteBtn.innerHTML = `<button data-id="${cars[i].id}" type="button" class="btn btn-danger delete-btn">Delete</button>`;

            userEl.append(editBtn);
            userEl.append(deleteBtn);
            usersListBody.appendChild(userEl);
        }
    }

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
    let editPassword;
    function openEditModal(id) {
        // Найти пользователя по id
        fetch(`http://localhost:8080/admin/${id}`)
            .then(response => response.json())
            .then(async car => {
                // Заполнить поля модального окна данными пользователя
                editId.value = car.id;
                editModel.value = car.model;
                editYearOfRelease.value = car.yearOfRelease;
                // console.log(responseRoleData.length);
                editModal.show();
                $("#edit-submit-btn").data("id", editId.value);
            })
            .catch(error => console.error(error));
    }

    function editUser(id) {
        console.log(id);
        const model = editModel.value;
        const yearOfRelease = editYearOfRelease.value;
        // console.log(firstName, lastName, yearOfBirth, email, roles);
        // console.log(JSON.stringify({firstName, lastName, yearOfBirth, email, roles}))
        fetch(`http://localhost:8080/admin/edit/${id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            }, body: JSON.stringify({model, yearOfRelease})
        })
            .then(response => response.json())
            .then(updatedUser => {
                // console.log(`Updated user with id ${updatedUser.id}`);
                fetch('http://localhost:8080/admin/all')
                    .then(response => response.json())
                    .then(cars => {
                        displayUsers(cars);
                        displayUser(cars);
                        editModal.hide();
                    })
                    .catch(error => console.error(error));
            })
            .catch(error => console.log(error));
    }

    $(document).on ("click", "#edit-submit-btn", function () {
        const id = $(this).data("id")
        editUser(id);
    });


    function openDeleteModal(id) {
        // Найти пользователя по id
        fetch(`http://localhost:8080/admin/${id}`)
            .then(response => response.json())
            .then(car => {
                // Заполнить поля модального окна данными пользователя
                deleteId.value = car.id;
                deleteModel.value = car.model;
                deleteYearOfRelease.value = car.yearOfRelease;
                // Открыть модальное окно
                deleteModal.show();
                $("#delete-submit").data("id", id);
            })
            .catch(error => console.error(error));
    }

    $(document).on ("click", ".delete-btn", function () {
        const id = $(this).data("id")
        openDeleteModal(id);
    });

    function deleteUser(id) {
        fetch(`http://localhost:8080/admin/delete/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                console.log(`Deleted user with id ${id}`);
                fetch('http://localhost:8080/admin/all')
                    .then(response => response.json())
                    .then(cars => {
                        displayUsers(cars);
                        displayUser(cars);
                        deleteModal.hide();
                    })
                    .catch(error => console.error(error));
            })
            .catch(error => console.log(error));
    }

    $(document).on ("click", "#delete-submit", function () {
        const id = $(this).data("id")
        deleteUser(id);
    });

    function createUser() {
        const model = newModel.value;
        const yearOfRelease = newYearOfRelease.value;

        fetch('http://localhost:8080/admin/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({model, yearOfRelease})
        })
            .then(response => response.json())
            .then(car => {
                console.log(`Created car with id ${car.id}`);
                newModel.value = '';
                newYearOfRelease.value = '';

                fetch('http://localhost:8080/admin/all')
                    .then(response => response.json())
                    .then(cars => {
                        displayUsers(cars);
                        displayUser(cars);
                    })
                    .catch(error => console.error(error));
            })
            .catch(error => console.log(error));

    }


    $(document).on ("click", "#new-user-btn", function () {
        const id = $(this).data("id")
        createUser();
    });


});