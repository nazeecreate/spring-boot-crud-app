const requestUrl = "api/"
const correntUserUrl = "api/currentUser"

fillUsersTable()
loadCurrentUser()

//Load current user
async function loadCurrentUser() {
    const response = await fetch(correntUserUrl)
    const user = await response.json()
    document.querySelector(".js-username").textContent = user.username
    for (const role of user.roles) {
        const span = document.createElement("span")
        span.textContent = role.role + " "
        const element = document.querySelector(".js-roles")
        element.appendChild(span);
    }
    const table = document.querySelector(".js-currentUserTable")
    const row = table.insertRow()
    const id = row.insertCell(0)
    id.innerHTML = user.id
    const firstName = row.insertCell(1)
    firstName.innerHTML = user.firstName
    const lastName = row.insertCell(2)
    lastName.innerHTML = user.lastName
    const age = row.insertCell(3)
    age.innerHTML = user.age
    const username = row.insertCell(4)
    username.innerHTML = user.username
    const roles = row.insertCell(5)
    for (const role of user.roles) {
        const span = document.createElement("span")
        span.textContent = role.role + " "
        roles.appendChild(span);
    }
}

//Fill Users table
async function fillUsersTable() {
    const response = await fetch(requestUrl)
    const users = await response.json()
    const table = document.querySelector(".js-usersTable")
    table.innerHTML = ""
    users.forEach(user => {
        const row = table.insertRow()
        const id = row.insertCell(0)
        id.innerHTML = user.id
        const firstName = row.insertCell(1)
        firstName.innerHTML = user.firstName
        const lastName = row.insertCell(2)
        lastName.innerHTML = user.lastName
        const age = row.insertCell(3)
        age.innerHTML = user.age
        const username = row.insertCell(4)
        username.innerHTML = user.username
        const roles = row.insertCell(5)
        for (const role of user.roles) {
            const span = document.createElement("span")
            span.textContent = role.role + " "
            roles.appendChild(span);
        }
        const edit = row.insertCell(6)
        edit.innerHTML =
            "<button type='button' id='edit-button' class='btn btn-primary' data-toggle='modal' data-target='#modal-edit'> Edit </button>"
        edit.onclick = editModalWindow
        const del = row.insertCell(7)
        del.innerHTML =
            "<button type='button' id='delete-button' class='btn btn-danger' data-toggle='modal' data-target='#modal-delete'> Delete </button>"
        del.onclick = deleteModalWindow
    });
}

//Display edit modal window data
async function editModalWindow() {
    const id = this.parentNode.firstChild.textContent
    const response = await fetch(requestUrl + id)
    const user = await response.json()
    document.querySelector("#idModal").value = user.id
    document.querySelector("#firstNameModal").value = user.firstName
    document.querySelector("#lastNameModal").value = user.lastName
    document.querySelector("#ageModal").value = user.age
    document.querySelector("#usernameModal").value = user.username
    document.querySelector("#passwordModal").value = ""
    const rolesBox = document.querySelector("#roleSelectModal")
    for (const option of rolesBox) {
        option.selected = false
    }
}

//Preventing page reload
function handleForm(event) {
    event.preventDefault();
}

const editForm = document.querySelector("#edit-form")
editForm.addEventListener('submit', handleForm);

const deleteForm = document.querySelector("#delete-form")
deleteForm.addEventListener('submit', handleForm);

const newUserForm = document.querySelector("#new-user-form")
newUserForm.addEventListener('submit', handleForm);

//Submit edit modal
const editSubmitButton = document.querySelector("#edit-submit-button")
editSubmitButton.onclick = () => {
    const checked = document.querySelectorAll("#roleSelectModal :checked")
    const rolesList = getSelectedRoles(checked)

    const body = {
        id: document.querySelector("#idModal").value,
        firstName: document.querySelector("#firstNameModal").value,
        lastName: document.querySelector("#lastNameModal").value,
        age: document.querySelector("#ageModal").value,
        username: document.querySelector("#usernameModal").value,
        password: document.querySelector("#passwordModal").value,
        roles: rolesList
    }
    sendUpdateRequest(body).then(() => {
            fillUsersTable()
        }
    )
}

function getSelectedRoles(checked) {
    const selected = [...checked].map(option => option.value)
    const rolesList = []
    for (const role of selected) {
        if (role === "ADMIN") {
            rolesList.push({
                    id: 2,
                    role: "ROLE_ADMIN"
                }
            )
        } else if (role === "USER") {
            rolesList.push({
                    id: 1,
                    role: "ROLE_USER"
                }
            )
        }
    }
    return rolesList
}

async function sendUpdateRequest(body) {
    const headers = {
        'Content-Type': 'application/json'
    }

    const response = await fetch(requestUrl, {
        method: 'PUT',
        body: JSON.stringify(body),
        headers: headers
    })
}

//Display delete modal window data
async function deleteModalWindow() {
    const id = this.parentNode.firstChild.textContent
    const response = await fetch(requestUrl + id)
    const user = await response.json()
    document.querySelector("#idModal2").value = user.id
    document.querySelector("#firstNameModal2").value = user.firstName
    document.querySelector("#lastNameModal2").value = user.lastName
    document.querySelector("#ageModal2").value = user.age
    document.querySelector("#usernameModal2").value = user.username
    const rolesBox = document.querySelector("#roleSelectModal2")
    rolesBox.innerHTML = ""
    for (const role of user.roles) {
        const option = document.createElement("option")
        option.textContent = role.role
        rolesBox.appendChild(option)
    }
}

//Sending delete response
const deleteSubmitButton = document.querySelector("#delete-submit-button")
deleteSubmitButton.onclick = () => {
    const id = document.querySelector("#idModal2").value
    sendDeleteRequest(id).then(() => {
            fillUsersTable()
        }
    )
}

async function sendDeleteRequest(id) {
    const headers = {
        'Content-Type': 'application/json'
    }

    const response = await fetch(requestUrl + id, {
        method: 'DELETE',
        headers: headers
    })
}

//Creating new user
const newUserSubmitButton = document.querySelector("#new-user-submit-button")
newUserSubmitButton.onclick = () => {
    const checked = document.querySelectorAll("#roleSelect :checked")
    const rolesList = getSelectedRoles(checked)
    const body = {
        firstName: document.querySelector("#firstName").value,
        lastName: document.querySelector("#lastName").value,
        age: document.querySelector("#age").value,
        username: document.querySelector("#username").value,
        password: document.querySelector("#password").value,
        roles: rolesList
    }
    sendSaveRequest(body).then(() => {
            fillUsersTable()

            //Changing active tab
            const usersTableLink = document.querySelector("#usersTableLink")
            usersTableLink.classList.add("active")
            const newUserLink = document.querySelector("#newUserLink")
            newUserLink.classList.remove("active")
            const usersTableTab = document.querySelector("#usersTable")
            usersTableTab.classList.add("active")
            const newUserTab = document.querySelector("#newUser")
            newUserTab.classList.remove("active")

            //Clearing new user form
            document.querySelector("#firstName").value = ""
            document.querySelector("#lastName").value = ""
            document.querySelector("#age").value = ""
            document.querySelector("#username").value = ""
            document.querySelector("#password").value = ""
            const rolesBox = document.querySelector("#roleSelect")
            for (const option of rolesBox) {
                option.selected = false
            }
        }
    )
}


async function sendSaveRequest(body) {
    const headers = {
        'Content-Type': 'application/json'
    }

    const response = await fetch(requestUrl, {
        method: 'POST',
        body: JSON.stringify(body),
        headers: headers
    })

    console.log("Send", body)
}



