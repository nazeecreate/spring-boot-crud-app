const url = "api/currentUser"

async function loadCurrentUser(){
    const response = await fetch(url)
    const user = await response.json()
    console.log("roles", user.roles)
    document.querySelector(".js-username").textContent = user.username
    for (const role of user.roles){
        const span = document.createElement("span")
        span.textContent = role.role + " "
        const element = document.querySelector(".js-roles")
        element.appendChild(span);
    }

    const table = document.querySelector(".js-tableBody")
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
    for (const role of user.roles){
        const span = document.createElement("span")
        span.textContent = role.role + " "
        roles.appendChild(span);
    }
}

loadCurrentUser()
