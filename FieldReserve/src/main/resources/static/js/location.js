const locationAPI = "/api/locations";

function validateForm() {
    let name = $("#name").val().trim();
    let area = $("#area").val().trim();
    let city = $("#city").val().trim();

    if (name === "" || area === "" || city === "") {
        alert("All fields are required!");
        return false;
    }

    return true;
}

function loadLocations() {
    $.get(locationAPI, function (data) {
        displayLocations(data);
    });
}

function displayLocations(data) {
    let locationTable = "";

    for (let i = 0; i < data.length; i++) {
        let locationItem = data[i];

        locationTable += `<tr>
            <td>${locationItem.locationID}</td>
            <td>${locationItem.locationName}</td>
            <td>${locationItem.locationArea}</td>
            <td>${locationItem.locationCity}</td>
            <td>
                <button class="btn btn-warning btn-sm me-1"
                    onclick="fillEditForm(${locationItem.locationID}, '${locationItem.locationName}', '${locationItem.locationArea}', '${locationItem.locationCity}')">
                    Edit
                </button>
                <button class="btn btn-danger btn-sm"
                    onclick="deleteLocation(${locationItem.locationID})">
                    Delete
                </button>
            </td>
        </tr>`;
    }

    $("#locationTable").html(locationTable);
}

function deleteLocation(id) {
    $.ajax({
        url: locationAPI + "/" + id,
        type: "DELETE",
        success: function () {
            location.reload();
        }
    });
}

function fillEditForm(id, name, area, city) {
    $("#editId").val(id);
    $("#name").val(name);
    $("#area").val(area);
    $("#city").val(city);

    $("#addBtn").hide();
    $("#locationText").text("Update Location");
    $("#updateBtn").show();
}

$(document).ready(function () {
    $("#updateBtn").hide();

    loadLocations();

    $("#addBtn").click(function () {
        if (!validateForm()) return;

        $.ajax({
            url: locationAPI,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                locationName: $("#name").val(),
                locationArea: $("#area").val(),
                locationCity: $("#city").val()
            }),
            success: function () {
                alert("Location Added Successfully!");
                location.reload();
            }
        });
    });

    $("#updateBtn").click(function () {
        const id = $("#editId").val();

        if (!validateForm()) return;

        $.ajax({
            url: locationAPI + "/" + id,
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify({
                locationName: $("#name").val(),
                locationArea: $("#area").val(),
                locationCity: $("#city").val()
            }),
            success: function () {
                alert("Location updated successfully.");
                location.reload();
            }
        });
    });

    $("#searchCity").change(function () {
        let city = $("#searchCity").val();

        if (city === "") {
            loadLocations();
            return;
        }

        $.get(locationAPI + "/citySearch?city=" + encodeURIComponent(city), function (data) {
            displayLocations(data);
        });
    });

    $("#search").click(function () {
        let id = $("#searchID").val();

        if (id === "" || id <= 0) {
            alert("Please enter an ID greater than 0");
            return;
        }

        $.get(locationAPI + "/" + id, function (data) {
            displayLocations([data]);
        });
    });
});