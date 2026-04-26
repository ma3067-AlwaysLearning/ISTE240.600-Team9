var apiUrl = "/api/reservations";
var usersApiUrl = "/api/users";
var fieldsApiUrl = "/api/fields";

window.onload = function () {
    loadReservationDropdowns();
    loadReservations();

    document.getElementById("reservationForm").onsubmit = function (event) {
        event.preventDefault();
        saveReservation();
    };
};

function loadReservationDropdowns() {
    Promise.all([
        fetch(usersApiUrl).then(handleResponse),
        fetch(fieldsApiUrl).then(handleResponse)
    ])
        .then(function (results) {
            var users = results[0];
            var fields = results[1];

            fillUsersDropdown(users);
            fillFieldsDropdown(fields);
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
        });
}

function fillUsersDropdown(users) {
    var select = document.getElementById("userId");
    select.innerHTML = "<option value=''>Choose a registered user</option>";

    if (users.length === 0) {
        select.innerHTML = "<option value=''>No users registered</option>";
        return;
    }

    for (var i = 0; i < users.length; i++) {
        var user = users[i];

        var option = document.createElement("option");
        option.value = user.userId;
        option.textContent = user.userId + " - " + user.fullName + " (" + user.email + ")";

        select.appendChild(option);
    }
}

function fillFieldsDropdown(fields) {
    var select = document.getElementById("fieldId");
    select.innerHTML = "<option value=''>Choose a registered field</option>";

    if (fields.length === 0) {
        select.innerHTML = "<option value=''>No fields registered</option>";
        return;
    }

    for (var i = 0; i < fields.length; i++) {
        var field = fields[i];

        var option = document.createElement("option");
        option.value = field.fieldID;
        option.textContent =
            field.fieldID + " - " +
            field.fieldName + " | " +
            field.fieldLocation + " | AED " +
            field.hourlyRate + "/hour";

        select.appendChild(option);
    }
}

function loadReservations() {
    fetch(apiUrl)
        .then(handleResponse)
        .then(function (reservations) {
            showReservations(reservations);
            showMessage("", "");
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
        });
}

function saveReservation() {
    if (!validateReservationForm()) {
        return;
    }

    var reservationId = document.getElementById("reservationId").value;

    var reservation = {
        userId: Number(document.getElementById("userId").value),
        fieldId: Number(document.getElementById("fieldId").value),
        reservationDate: document.getElementById("reservationDate").value,
        startTime: document.getElementById("startTime").value,
        endTime: document.getElementById("endTime").value,
        reservationStatus: document.getElementById("reservationStatus").value,
        paymentStatus: document.getElementById("paymentStatus").value === "true"
    };

    var url = apiUrl;
    var method = "POST";

    if (reservationId !== "") {
        url = apiUrl + "/" + reservationId;
        method = "PUT";
    }

    fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(reservation)
    })
        .then(handleResponse)
        .then(function () {
            clearForm();
            loadReservations();

            if (method === "POST") {
                showMessage("Reservation created successfully.", "success");
            } else {
                showMessage("Reservation updated successfully.", "success");
            }
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
        });
}

function validateReservationForm() {
    var userId = document.getElementById("userId").value;
    var fieldId = document.getElementById("fieldId").value;
    var date = document.getElementById("reservationDate").value;
    var startTime = document.getElementById("startTime").value;
    var endTime = document.getElementById("endTime").value;

    if (userId === "") {
        showMessage("Please choose a registered user.", "warning");
        return false;
    }

    if (fieldId === "") {
        showMessage("Please choose a registered field.", "warning");
        return false;
    }

    if (date === "") {
        showMessage("Please choose a reservation date.", "warning");
        return false;
    }

    if (startTime === "" || endTime === "") {
        showMessage("Please choose start time and end time.", "warning");
        return false;
    }

    if (endTime <= startTime) {
        showMessage("End time must be after start time.", "warning");
        return false;
    }

    return true;
}

function searchReservations() {
    var status = document.getElementById("searchStatus").value;

    if (status === "") {
        showMessage("Please choose a status to search.", "warning");
        return;
    }

    fetch(apiUrl + "/search?status=" + encodeURIComponent(status))
        .then(handleResponse)
        .then(function (reservations) {
            showReservations(reservations);
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
        });
}

function editReservation(id) {
    fetch(apiUrl + "/" + id)
        .then(handleResponse)
        .then(function (reservation) {
            document.getElementById("reservationId").value = reservation.reservationId;
            document.getElementById("userId").value = reservation.userId;
            document.getElementById("fieldId").value = reservation.fieldId;
            document.getElementById("reservationDate").value = reservation.reservationDate;
            document.getElementById("startTime").value = reservation.startTime;
            document.getElementById("endTime").value = reservation.endTime;
            document.getElementById("reservationStatus").value = reservation.reservationStatus;
            document.getElementById("paymentStatus").value = String(reservation.paymentStatus);

            document.getElementById("formTitle").innerText = "Edit Reservation";

            window.scrollTo({ top: 0, behavior: "smooth" });
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
        });
}

function deleteReservation(id) {
    var answer = confirm("Are you sure you want to delete this reservation?");

    if (!answer) {
        return;
    }

    fetch(apiUrl + "/" + id, {
        method: "DELETE"
    })
        .then(handleResponse)
        .then(function () {
            loadReservations();
            showMessage("Reservation deleted successfully.", "success");
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
        });
}

function showReservations(reservations) {
    var table = document.getElementById("reservationTableBody");
    table.innerHTML = "";

    if (reservations.length === 0) {
        table.innerHTML = "<tr><td colspan='10' class='empty-state'>No reservations found</td></tr>";
        return;
    }

    for (var i = 0; i < reservations.length; i++) {
        var r = reservations[i];

        var paymentLabel = r.paymentStatus ? "Paid" : "Unpaid";
        var paymentClass = r.paymentStatus ? "badge-soft" : "badge text-bg-warning rounded-pill";

        table.innerHTML +=
            "<tr>" +
            "<td>" + r.reservationId + "</td>" +
            "<td>" + r.userId + "</td>" +
            "<td>" + r.fieldId + "</td>" +
            "<td>" + r.reservationDate + "</td>" +
            "<td>" + r.startTime + "</td>" +
            "<td>" + r.endTime + "</td>" +
            "<td>AED " + r.totalCost + "</td>" +
            "<td><span class='badge-soft'>" + r.reservationStatus + "</span></td>" +
            "<td><span class='" + paymentClass + "'>" + paymentLabel + "</span></td>" +
            "<td>" +
            "<button class='btn btn-warning btn-sm me-2' onclick='editReservation(" + r.reservationId + ")'>Edit</button>" +
            "<button class='btn btn-danger btn-sm' onclick='deleteReservation(" + r.reservationId + ")'>Delete</button>" +
            "</td>" +
            "</tr>";
    }
}

function clearForm() {
    document.getElementById("reservationId").value = "";
    document.getElementById("userId").value = "";
    document.getElementById("fieldId").value = "";
    document.getElementById("reservationDate").value = "";
    document.getElementById("startTime").value = "";
    document.getElementById("endTime").value = "";
    document.getElementById("reservationStatus").value = "PENDING";
    document.getElementById("paymentStatus").value = "false";
    document.getElementById("formTitle").innerText = "Create Reservation";
}

function showMessage(message, type) {
    var area = document.getElementById("messageArea");

    if (message === "") {
        area.innerHTML = "";
        return;
    }

    area.innerHTML =
        "<div class='alert alert-" + type + "'>" +
        message +
        "</div>";
}

function handleResponse(response) {
    return response.text().then(function (text) {
        if (!response.ok) {
            throw new Error(text || "Something went wrong.");
        }

        if (text === "") {
            return null;
        }

        try {
            return JSON.parse(text);
        } catch (error) {
            return text;
        }
    });
}