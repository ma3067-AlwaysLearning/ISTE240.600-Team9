var apiUrl = "/api/users";

window.onload = function () {
    loadUsers();

    document.getElementById("userForm").onsubmit = function (event) {
        event.preventDefault();
        saveUser();
    };
};

function loadUsers() {
    document.getElementById("loading").classList.remove("d-none");

    fetch(apiUrl)
        .then(response => response.json())
        .then(users => {
            showUsers(users);
            document.getElementById("loading").classList.add("d-none");
        })
        .catch(error => {
            showMessage("Error loading users", "danger");
            document.getElementById("loading").classList.add("d-none");
        });
}

function searchUsers() {
    var email = document.getElementById("searchEmail").value;

    if (email.trim() === "") {
        showMessage("Please enter an email to search", "warning");
        return;
    }

    document.getElementById("loading").classList.remove("d-none");

    fetch(apiUrl + "/search?email=" + encodeURIComponent(email))
        .then(response => response.json())
        .then(users => {
            showUsers(users);
            document.getElementById("loading").classList.add("d-none");
        })
        .catch(error => {
            showMessage("Error searching users", "danger");
            document.getElementById("loading").classList.add("d-none");
        });
}

function saveUser() {
    var userId = document.getElementById("userId").value;

    var user = {
        fullName: document.getElementById("fullName").value,
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value,
        role: document.getElementById("role").value,
        password: document.getElementById("password").value
    };

    var url = apiUrl;
    var method = "POST";

    if (userId !== "") {
        url = apiUrl + "/" + userId;
        method = "PUT";
    }

    fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
        .then(response => response.json())
        .then(data => {
            clearForm();
            loadUsers();

            if (method === "POST") {
                showMessage("User added successfully", "success");
            } else {
                showMessage("User updated successfully", "success");
            }
        })
        .catch(error => {
            showMessage("Error saving user", "danger");
        });
}

function editUser(id) {
    fetch(apiUrl + "/" + id)
        .then(response => response.json())
        .then(user => {
            document.getElementById("userId").value = user.userId;
            document.getElementById("fullName").value = user.fullName;
            document.getElementById("email").value = user.email;
            document.getElementById("phone").value = user.phone;
            document.getElementById("role").value = user.role;
            document.getElementById("password").value = user.password;

            document.getElementById("formTitle").innerText = "Edit User";
            window.scrollTo({ top: 0, behavior: "smooth" });
        })
        .catch(error => {
            showMessage("Error loading user", "danger");
        });
}

function deleteUser(id) {
    var answer = confirm("Are you sure you want to delete this user?");

    if (answer === false) {
        return;
    }

    fetch(apiUrl + "/" + id, {
        method: "DELETE"
    })
        .then(response => response.text())
        .then(message => {
            loadUsers();
            showMessage("User deleted successfully", "success");
        })
        .catch(error => {
            showMessage("Error deleting user", "danger");
        });
}

function showUsers(users) {
    var table = document.getElementById("usersTableBody");
    table.innerHTML = "";

    if (users.length === 0) {
        table.innerHTML = "<tr><td colspan='7' class='empty-state'>No users found</td></tr>";
        return;
    }

    for (var i = 0; i < users.length; i++) {
        var user = users[i];

        table.innerHTML +=
            "<tr>" +
            "<td>" + user.userId + "</td>" +
            "<td>" + user.fullName + "</td>" +
            "<td>" + user.email + "</td>" +
            "<td>" + user.phone + "</td>" +
            "<td><span class='badge-soft'>" + user.role + "</span></td>" +
            "<td>" + user.createdAt + "</td>" +
            "<td>" +
            "<button class='btn btn-warning btn-sm me-2' onclick='editUser(" + user.userId + ")'>Edit</button>" +
            "<button class='btn btn-danger btn-sm' onclick='deleteUser(" + user.userId + ")'>Delete</button>" +
            "</td>" +
            "</tr>";
    }
}

function clearForm() {
    document.getElementById("userId").value = "";
    document.getElementById("fullName").value = "";
    document.getElementById("email").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("role").value = "Customer";
    document.getElementById("password").value = "";

    document.getElementById("formTitle").innerText = "Add User";
}

function showMessage(message, type) {
    document.getElementById("messageArea").innerHTML =
        "<div class='alert alert-" + type + "'>" +
        message +
        "</div>";
}