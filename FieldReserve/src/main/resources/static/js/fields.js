var apiUrl = "/api/fields";

window.onload = function () {
    loadFields();
    document.getElementById("fieldForm").onsubmit = function (event) {
        event.preventDefault();
        saveField();
    };
    document.getElementById("searchInput").oninput = function () {
        searchFields();
    };
};

//load all fields
function loadFields() {
    document.getElementById("loading").classList.remove("d-none");

    fetch(apiUrl)
        .then(response => response.json())
        .then(fields => {
            showFields(fields);
            document.getElementById("loading").classList.add("d-none");
        })
        .catch(error => {
            showMessage("Error loading fields", "danger");
            document.getElementById("loading").classList.add("d-none");
        });
}

//search fields
function searchFields() {
    var name = document.getElementById("searchInput").value;

    if (name.trim() === "") {
        loadFields();
        return;
    }

    document.getElementById("loading").classList.remove("d-none");

    fetch(apiUrl + "/search?name=" + encodeURIComponent(name))
        .then(response => response.json())
        .then(fields => {
            showFields(fields);
            document.getElementById("loading").classList.add("d-none");
        })
        .catch(error => {
            showMessage("Error searching fields", "danger");
            document.getElementById("loading").classList.add("d-none");
        });
}

//save field (add / update)
function saveField() {
    var fieldId = document.getElementById("fieldId").value;

    var field = {
        fieldName: document.getElementById("name").value,
        fieldLocation: document.getElementById("location").value,
        hourlyRate: document.getElementById("rate").value,
        availabilityStatus: document.getElementById("status").value
    };

    var url = apiUrl;
    var method = "POST";

    if (fieldId !== "") {
        url = apiUrl + "/" + fieldId;
        method = "PUT";
    }

    fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(field)
    })
        .then(response => response.json())
        .then(data => {
            clearForm();
            loadFields();

            if (method === "post") {
                showMessage("field added successfully", "success");
            } else {
                showMessage("field updated successfully", "success");
            }
        })
        .catch(error => {
            showMessage("error saving field", "danger");
        });
}

//edit field
function editField(id) {
    fetch(apiUrl + "/" + id)
        .then(response => response.json())
        .then(field => {
            document.getElementById("fieldId").value = field.fieldID;
            document.getElementById("name").value = field.fieldName;
            document.getElementById("location").value = field.fieldLocation;
            document.getElementById("rate").value = field.hourlyRate;
            document.getElementById("status").value = field.availabilityStatus;

            document.getElementById("formTitle").innerText = "edit field";
        })
        .catch(error => {
            showMessage("error loading field", "danger");
        });
}

//delete field
function deleteField(id) {
    var answer = confirm("are you sure you want to delete this field?");

    if (answer === false) {
        return;
    }

    fetch(apiUrl + "/" + id, {
        method: "DELETE"
    })
        .then(response => response.text())
        .then(message => {
            loadFields();
            showMessage("field deleted successfully", "success");
        })
        .catch(error => {
            showMessage("error deleting field", "danger");
        });
}

//show fields
function showFields(fields) {
    var table = document.getElementById("fieldTable");
    table.innerHTML = "";

    if (fields.length === 0) {
        table.innerHTML = "<tr><td colspan='6' class='text-center'>no fields found</td></tr>";
        return;
    }

    for (var i = 0; i < fields.length; i++) {
        var f = fields[i];

        table.innerHTML +=
            "<tr>" +
            "<td>" + f.fieldID + "</td>" +
            "<td>" + f.fieldName + "</td>" +
            "<td>" + f.fieldLocation + "</td>" +
            "<td>" + f.hourlyRate + "</td>" +
            "<td>" + f.availabilityStatus + "</td>" +
            "<td>" +
            "<button class='btn btn-warning btn-sm me-2' onclick='editField(" + f.fieldID + ")'>Edit</button>" +
            "<button class='btn btn-danger btn-sm' onclick='deleteField(" + f.fieldID + ")'>Delete</button>" +
            "</td>" +
            "</tr>";
    }
}

//clear form
function clearForm() {
    document.getElementById("fieldId").value = "";
    document.getElementById("name").value = "";
    document.getElementById("location").value = "";
    document.getElementById("rate").value = "";
    document.getElementById("status").value = "Available";

    document.getElementById("formTitle").innerText = "add field";
}

//show message
function showMessage(message, type) {
    document.getElementById("messageArea").innerHTML =
        "<div class='alert alert-" + type + "'>" +
        message +
        "</div>";
}