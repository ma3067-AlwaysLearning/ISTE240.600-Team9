var apiUrl = "/api/fields";
var locationsApiUrl = "/api/locations";

window.onload = function () {
    loadLocationsForFieldForm();
    loadFields();

    document.getElementById("fieldForm").onsubmit = function (event) {
        event.preventDefault();
        saveField();
    };

    document.getElementById("searchInput").oninput = function () {
        searchFields();
    };
};

function loadLocationsForFieldForm() {
    fetch(locationsApiUrl)
        .then(handleResponse)
        .then(function (locations) {
            var select = document.getElementById("location");
            select.innerHTML = "<option value=''>Choose a registered location</option>";

            if (locations.length === 0) {
                select.innerHTML = "<option value=''>No locations registered</option>";
                return;
            }

            for (var i = 0; i < locations.length; i++) {
                var location = locations[i];

                var option = document.createElement("option");
                option.value = formatLocation(location);
                option.textContent = formatLocation(location);

                select.appendChild(option);
            }
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
        });
}

function formatLocation(location) {
    return location.locationName + " - " + location.locationArea + ", " + location.locationCity;
}

function loadFields() {
    setLoading(true);

    fetch(apiUrl)
        .then(handleResponse)
        .then(function (fields) {
            showFields(fields);
            setLoading(false);
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
            setLoading(false);
        });
}

function searchFields() {
    var name = document.getElementById("searchInput").value;

    if (name.trim() === "") {
        loadFields();
        return;
    }

    setLoading(true);

    fetch(apiUrl + "/search?name=" + encodeURIComponent(name))
        .then(handleResponse)
        .then(function (fields) {
            showFields(fields);
            setLoading(false);
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
            setLoading(false);
        });
}

function saveField() {
    if (!validateFieldForm()) {
        return;
    }

    var fieldId = document.getElementById("fieldId").value;

    var field = {
        fieldName: document.getElementById("name").value.trim(),
        fieldLocation: document.getElementById("location").value,
        hourlyRate: Number(document.getElementById("rate").value),
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
        .then(handleResponse)
        .then(function () {
            clearForm();
            loadFields();

            if (method === "POST") {
                showMessage("Field added successfully.", "success");
            } else {
                showMessage("Field updated successfully.", "success");
            }
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
        });
}

function validateFieldForm() {
    var name = document.getElementById("name").value.trim();
    var location = document.getElementById("location").value;
    var rate = Number(document.getElementById("rate").value);

    if (name === "") {
        showMessage("Field name is required.", "warning");
        return false;
    }

    if (location === "") {
        showMessage("Please choose a registered location.", "warning");
        return false;
    }

    if (rate <= 0) {
        showMessage("Hourly rate must be greater than 0.", "warning");
        return false;
    }

    return true;
}

function editField(id) {
    fetch(apiUrl + "/" + id)
        .then(handleResponse)
        .then(function (field) {
            document.getElementById("fieldId").value = field.fieldID;
            document.getElementById("name").value = field.fieldName;
            document.getElementById("rate").value = field.hourlyRate;
            document.getElementById("status").value = field.availabilityStatus;
            document.getElementById("formTitle").innerText = "Edit Field";

            setSelectValueOrAdd("location", field.fieldLocation);

            window.scrollTo({ top: 0, behavior: "smooth" });
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
        });
}

function setSelectValueOrAdd(selectId, value) {
    var select = document.getElementById(selectId);

    var exists = false;

    for (var i = 0; i < select.options.length; i++) {
        if (select.options[i].value === value) {
            exists = true;
        }
    }

    if (!exists && value !== null && value !== "") {
        var option = document.createElement("option");
        option.value = value;
        option.textContent = value;
        select.appendChild(option);
    }

    select.value = value;
}

function deleteField(id) {
    var answer = confirm("Are you sure you want to delete this field?");

    if (answer === false) {
        return;
    }

    fetch(apiUrl + "/" + id, {
        method: "DELETE"
    })
        .then(handleResponse)
        .then(function () {
            loadFields();
            showMessage("Field deleted successfully.", "success");
        })
        .catch(function (error) {
            showMessage(error.message, "danger");
        });
}

function showFields(fields) {
    var table = document.getElementById("fieldTable");
    table.innerHTML = "";

    if (fields.length === 0) {
        table.innerHTML = "<tr><td colspan='6' class='empty-state'>No fields found</td></tr>";
        return;
    }

    for (var i = 0; i < fields.length; i++) {
        var f = fields[i];

        var statusClass = f.availabilityStatus === "AVAILABLE"
            ? "badge-soft"
            : "badge text-bg-warning rounded-pill";

        table.innerHTML +=
            "<tr>" +
            "<td>" + f.fieldID + "</td>" +
            "<td>" + escapeHtml(f.fieldName) + "</td>" +
            "<td>" + escapeHtml(f.fieldLocation) + "</td>" +
            "<td>AED " + f.hourlyRate + "</td>" +
            "<td><span class='" + statusClass + "'>" + f.availabilityStatus + "</span></td>" +
            "<td>" +
            "<button class='btn btn-warning btn-sm me-2' onclick='editField(" + f.fieldID + ")'>Edit</button>" +
            "<button class='btn btn-danger btn-sm' onclick='deleteField(" + f.fieldID + ")'>Delete</button>" +
            "</td>" +
            "</tr>";
    }
}

function clearForm() {
    document.getElementById("fieldId").value = "";
    document.getElementById("name").value = "";
    document.getElementById("location").value = "";
    document.getElementById("rate").value = "";
    document.getElementById("status").value = "AVAILABLE";
    document.getElementById("formTitle").innerText = "Add Field";
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

function setLoading(isLoading) {
    var loading = document.getElementById("loading");

    if (!loading) {
        return;
    }

    if (isLoading) {
        loading.classList.remove("d-none");
    } else {
        loading.classList.add("d-none");
    }
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

function escapeHtml(value) {
    if (value === null || value === undefined) {
        return "";
    }

    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}