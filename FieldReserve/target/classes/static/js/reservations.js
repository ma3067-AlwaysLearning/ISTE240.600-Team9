var apiUrl = "/api/reservations";

// Reservations are automatically loaded once the window is loaded
window.onload = function () {
    loadReservations();

    // Handles form submission (create/update reservation)
    document.getElementById("reservationForm").onsubmit = function (event) {
        event.preventDefault();
        saveReservation();
    };

    // An event listener that responds to pressing a key on the keyboard for "Enter"
    document.getElementById("searchStatus").addEventListener("keypress", function (e) {
        if (e.key === "Enter") {
            searchReservations();
        }
    });
};


// Used to retrieve all existing reservations and display them in the table
function loadReservations() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(reservations => {
            showReservations(reservations);
        })
        .catch(error => {
            console.error("Error loading reservations:", error);
        });
}


// Function used to create or update a reservation
function saveReservation() {

    var reservationId = document.getElementById("reservationId")?.value;

    // Create reservation object from form inputs
    var reservation = {
        userId: document.getElementById("userId").value,
        fieldId: document.getElementById("fieldId").value,
        reservationDate: document.getElementById("reservationDate").value,
        startTime: document.getElementById("startTime").value,
        endTime: document.getElementById("endTime").value,
        reservationStatus: document.getElementById("reservationStatus").value,
        paymentStatus: document.getElementById("paymentStatus").value === "true"
    };

    var url = apiUrl;
    var method = "POST";

    // If reservationId exists → update instead of create
    if (reservationId && reservationId !== "") {
        url = apiUrl + "/" + reservationId;
        method = "PUT";
        reservation.reservationId = reservationId;
    }

    fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(reservation)
    })
        .then(response => response.json())
        .then(() => {
            clearForm();
            loadReservations(); // refresh table immediately after save
        })
        .catch(error => {
            console.error("Error saving reservation:", error);
        });
}


// Function used to search reservations by status (REQUIRED FEATURE)
function searchReservations() {

    var status = document.getElementById("searchStatus").value;

    if (status.trim() === "") {
        alert("Please enter a reservation status to search");
        return;
    }

    // Calls backend search endpoint
    fetch(apiUrl + "/search?status=" + encodeURIComponent(status))
        .then(response => response.json())
        .then(reservations => {
            showReservations(reservations);
        })
        .catch(error => {
            console.error("Error searching reservations:", error);
        });
}


// Function used for editing a reservation
function editReservation(id) {
    fetch(apiUrl + "/" + id)
        .then(response => response.json())
        .then(reservation => {

            document.getElementById("userId").value = reservation.userId;
            document.getElementById("fieldId").value = reservation.fieldId;
            document.getElementById("reservationDate").value = reservation.reservationDate;
            document.getElementById("startTime").value = reservation.startTime;
            document.getElementById("endTime").value = reservation.endTime;
            document.getElementById("reservationStatus").value = reservation.reservationStatus;
            document.getElementById("paymentStatus").value = reservation.paymentStatus;

            // Create hidden ID field if not present
            if (!document.getElementById("reservationId")) {
                let hidden = document.createElement("input");
                hidden.type = "hidden";
                hidden.id = "reservationId";
                document.getElementById("reservationForm").appendChild(hidden);
            }

            document.getElementById("reservationId").value = reservation.reservationId;
        })
        .catch(error => {
            console.error("Error loading reservation:", error);
        });
}


// Function used to delete a reservation
function deleteReservation(id) {

    var answer = confirm("Are you sure you want to delete this reservation?");

    if (!answer) return;

    fetch(apiUrl + "/" + id, {
        method: "DELETE"
    })
        .then(() => {
            loadReservations(); // refresh table after deletion
        })
        .catch(error => {
            console.error("Error deleting reservation:", error);
        });
}


// Function used to display reservations in the HTML table
function showReservations(reservations) {

    var table = document.getElementById("reservationTableBody");
    table.innerHTML = "";

    if (reservations.length === 0) {
        table.innerHTML = "<tr><td colspan='8' class='text-center'>No reservations found</td></tr>";
        return;
    }

    for (var i = 0; i < reservations.length; i++) {

        var r = reservations[i];

        table.innerHTML +=
            "<tr>" +
            "<td>" + r.reservationId + "</td>" +
            "<td>" + r.reservationDate + "</td>" +
            "<td>" + r.startTime + "</td>" +
            "<td>" + r.endTime + "</td>" +
            "<td>" + r.totalCost + "</td>" +
            "<td>" + r.reservationStatus + "</td>" +
            "<td>" + r.paymentStatus + "</td>" +
            "<td>" +
            "<button class='btn btn-warning btn-sm me-2' onclick='editReservation(" + r.reservationId + ")'>Edit</button>" +
            "<button class='btn btn-danger btn-sm' onclick='deleteReservation(" + r.reservationId + ")'>Delete</button>" +
            "</td>" +
            "</tr>";
    }
}


// Clears the form after submission or update
function clearForm() {

    document.getElementById("userId").value = "";
    document.getElementById("fieldId").value = "";
    document.getElementById("reservationDate").value = "";
    document.getElementById("startTime").value = "";
    document.getElementById("endTime").value = "";
    document.getElementById("reservationStatus").value = "";
    document.getElementById("paymentStatus").value = "false";

    var idField = document.getElementById("reservationId");
    if (idField) idField.remove();
}