<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>BillWise - Customer Management</title>

<style>

* {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: #f4f6f8;
}

/* HEADER */

.header {
    background: #222;
    color: white;
    padding: 18px 30px;

    display: flex;
    justify-content: space-between;
    align-items: center;
}

.header h2 {
    margin: 0;
}

.header-buttons button {
    padding: 10px 18px;
    margin-left: 8px;

    border: none;
    border-radius: 5px;

    cursor: pointer;
    color: white;
}

.dashboard-btn {
    background: #3498db;
}

.logout-btn {
    background: #e74c3c;
}

/* CONTAINER */

.container {
    width: 90%;
    margin: 30px auto;
}

/* FORM */

.form-box {
    background: white;

    padding: 25px;

    border-radius: 10px;

    margin-bottom: 30px;

    box-shadow: 0 3px 10px rgba(0,0,0,0.10);
}

.form-box h2 {
    margin-top: 0;
}

input {
    padding: 12px;

    margin-right: 10px;

    width: 280px;

    border: 1px solid #ccc;

    border-radius: 5px;

    font-size: 15px;
}

button {
    padding: 10px 18px;

    border: none;

    border-radius: 5px;

    cursor: pointer;
}

.add-btn {
    background: green;
    color: white;
}

.add-btn:hover {
    background: darkgreen;
}

/* MESSAGE */

#message {
    margin-top: 15px;

    font-weight: bold;
}

/* TABLE */

table {
    width: 100%;

    border-collapse: collapse;

    background: white;
}

th {
    background: #222;
    color: white;

    padding: 14px;
}

td {
    padding: 14px;

    border: 1px solid #ddd;

    text-align: center;
}

tr:hover {
    background: #f5f5f5;
}

/* BUTTONS */

.edit-btn {
    background: orange;
    color: white;

    margin-right: 5px;
}

.delete-btn {
    background: red;
    color: white;
}

.edit-btn:hover {
    background: #e67e00;
}

.delete-btn:hover {
    background: darkred;
}

/* LOADING */

.loading {
    text-align: center;

    font-weight: bold;

    padding: 20px;
}

/* EMPTY */

.empty {
    text-align: center;

    color: #777;

    padding: 20px;
}

/* RESPONSIVE */

@media screen and (max-width: 768px) {

    .container {
        width: 95%;
    }

    input {
        width: 100%;

        margin-bottom: 10px;
    }

    .add-btn {
        width: 100%;
    }

    table {
        font-size: 13px;
    }

    th,
    td {
        padding: 8px;
    }

}

</style>

</head>


<body>


<!-- HEADER -->

<div class="header">

    <h2>BillWise - Customer Management</h2>

    <div class="header-buttons">

        <button
            class="dashboard-btn"
            onclick="goDashboard()">
            Dashboard
        </button>

        <button
            class="logout-btn"
            onclick="logout()">
            Logout
        </button>

    </div>

</div>


<!-- MAIN CONTAINER -->

<div class="container">


    <!-- ADD CUSTOMER -->

    <div class="form-box">

        <h2>Add Customer</h2>

        <br>

        <input
            type="text"
            id="name"
            placeholder="Enter Customer Name">

        <input
            type="email"
            id="email"
            placeholder="Enter Customer Email">

        <button
            class="add-btn"
            onclick="addCustomer()">

            Add Customer

        </button>


        <div id="message"></div>

    </div>



    <!-- CUSTOMER LIST -->

    <div class="form-box">

        <h2>Customer List</h2>

        <br>

        <table>

            <thead>

                <tr>

                    <th>ID</th>

                    <th>Name</th>

                    <th>Email</th>

                    <th>Action</th>

                </tr>

            </thead>


            <tbody id="customerTable">

                <tr>

                    <td
                        colspan="4"
                        class="loading">

                        Loading customers...

                    </td>

                </tr>

            </tbody>

        </table>

    </div>


</div>



<script>


/* =========================================
   GET JWT TOKEN
========================================= */

function getToken() {

    return localStorage.getItem("token");

}



/* =========================================
   CHECK LOGIN
========================================= */

function checkLogin() {

    const token = getToken();

    if (!token) {

        alert("Please login first");

        window.location.href = "/login";

        return false;

    }

    return true;

}



/* =========================================
   ADD CUSTOMER
========================================= */

function addCustomer() {


    const name =
        document.getElementById("name").value.trim();


    const email =
        document.getElementById("email").value.trim();


    const message =
        document.getElementById("message");


    const token = getToken();


    /* VALIDATION */

    if (!name || !email) {

        message.innerText =
            "Please enter customer name and email";

        message.style.color = "red";

        return;

    }


    if (!token) {

        message.innerText =
            "Login expired. Please login again.";

        message.style.color = "red";

        return;

    }


    /* API CALL */

    fetch("/customers", {

        method: "POST",

        headers: {

            "Content-Type": "application/json",

            "Authorization":
                "Bearer " + token

        },

        body: JSON.stringify({

            name: name,

            email: email

        })

    })


    .then(response => {

        console.log(
            "Add Customer Status:",
            response.status
        );


        if (!response.ok) {

            throw new Error(
                "Customer add failed: "
                + response.status
            );

        }


        return response.json();

    })


    .then(data => {


        console.log(
            "Customer Added:",
            data
        );


        message.innerText =
            "Customer added successfully";


        message.style.color = "green";


        /* CLEAR FORM */

        document.getElementById("name").value = "";

        document.getElementById("email").value = "";


        /* REFRESH TABLE */

        loadCustomers();

    })


    .catch(error => {

        console.error(
            "Add Customer Error:",
            error
        );


        message.innerText =
            "Customer add failed";

        message.style.color = "red";

    });

}



/* =========================================
   LOAD ALL CUSTOMERS
========================================= */

function loadCustomers() {


    const token = getToken();


    const table =
        document.getElementById("customerTable");


    /* TOKEN CHECK */

    if (!token) {

        table.innerHTML = `

            <tr>

                <td
                    colspan="4"
                    class="empty">

                    Please login first

                </td>

            </tr>

        `;

        return;

    }


    /* LOADING */

    table.innerHTML = `

        <tr>

            <td
                colspan="4"
                class="loading">

                Loading customers...

            </td>

        </tr>

    `;


    /* GET API */

    fetch("/customers", {

        method: "GET",

        headers: {

            "Authorization":
                "Bearer " + token

        }

    })


    .then(response => {


        console.log(
            "Customer API Status:",
            response.status
        );


        if (!response.ok) {

            throw new Error(
                "GET /customers failed: "
                + response.status
            );

        }


        return response.json();

    })


    .then(customers => {


        console.log(
            "Customers received:",
            customers
        );


        table.innerHTML = "";


        /* NO DATA */

        if (
            !customers ||
            customers.length === 0
        ) {


            table.innerHTML = `

                <tr>

                    <td
                        colspan="4"
                        class="empty">

                        No customers found

                    </td>

                </tr>

            `;


            return;

        }



        /* DISPLAY DATA */

        customers.forEach(customer => {


            const row =
                document.createElement("tr");


            /* ID */

            const idCell =
                document.createElement("td");

            idCell.innerText =
                customer.id;


            /* NAME */

            const nameCell =
                document.createElement("td");

            nameCell.innerText =
                customer.name;


            /* EMAIL */

            const emailCell =
                document.createElement("td");

            emailCell.innerText =
                customer.email;


            /* ACTION */

            const actionCell =
                document.createElement("td");


            /* EDIT BUTTON */

            const editButton =
                document.createElement("button");


            editButton.innerText =
                "Edit";


            editButton.className =
                "edit-btn";


            editButton.onclick =
                function() {

                    editCustomer(
                        customer.id,
                        customer.name,
                        customer.email
                    );

                };


            /* DELETE BUTTON */

            const deleteButton =
                document.createElement("button");


            deleteButton.innerText =
                "Delete";


            deleteButton.className =
                "delete-btn";


            deleteButton.onclick =
                function() {

                    deleteCustomer(
                        customer.id
                    );

                };


            /* ADD BUTTONS */

            actionCell.appendChild(
                editButton
            );


            actionCell.appendChild(
                deleteButton
            );


            /* ADD CELLS */

            row.appendChild(
                idCell
            );


            row.appendChild(
                nameCell
            );


            row.appendChild(
                emailCell
            );


            row.appendChild(
                actionCell
            );


            /* ADD ROW */

            table.appendChild(
                row
            );

        });

    })


    .catch(error => {


        console.error(
            "Customer Load Error:",
            error
        );


        table.innerHTML = `

            <tr>

                <td
                    colspan="4"
                    class="empty">

                    Error loading customers

                </td>

            </tr>

        `;

    });

}



/* =========================================
   DELETE CUSTOMER
========================================= */

function deleteCustomer(id) {


    const token = getToken();


    if (!token) {

        alert(
            "Login expired. Please login again."
        );

        return;

    }


    const confirmDelete =
        confirm(
            "Are you sure you want to delete this customer?"
        );


    if (!confirmDelete) {

        return;

    }


    fetch(
        "/customers/" + id,
        {

            method: "DELETE",

            headers: {

                "Authorization":
                    "Bearer " + token

            }

        }
    )


    .then(response => {


        console.log(
            "Delete Status:",
            response.status
        );


        if (!response.ok) {

            throw new Error(
                "Delete failed"
            );

        }


        return response.text();

    })


    .then(data => {


        alert(
            "Customer deleted successfully"
        );


        loadCustomers();

    })


    .catch(error => {


        console.error(
            "Delete Error:",
            error
        );


        alert(
            "Customer delete failed"
        );

    });

}



/* =========================================
   EDIT CUSTOMER
========================================= */

function editCustomer(
    id,
    oldName,
    oldEmail
) {


    const newName =
        prompt(
            "Enter customer name:",
            oldName
        );


    if (newName === null) {

        return;

    }


    const newEmail =
        prompt(
            "Enter customer email:",
            oldEmail
        );


    if (newEmail === null) {

        return;

    }


    if (
        newName.trim() === "" ||
        newEmail.trim() === ""
    ) {

        alert(
            "Name and email cannot be empty"
        );

        return;

    }


    const token = getToken();


    if (!token) {

        alert(
            "Login expired. Please login again."
        );

        return;

    }


    fetch(
        "/customers/" + id,
        {

            method: "PUT",

            headers: {

                "Content-Type":
                    "application/json",

                "Authorization":
                    "Bearer " + token

            },


            body: JSON.stringify({

                name:
                    newName.trim(),

                email:
                    newEmail.trim()

            })

        }
    )


    .then(response => {


        console.log(
            "Update Status:",
            response.status
        );


        if (!response.ok) {

            throw new Error(
                "Update failed"
            );

        }


        return response.json();

    })


    .then(data => {


        alert(
            "Customer updated successfully"
        );


        loadCustomers();

    })


    .catch(error => {


        console.error(
            "Update Error:",
            error
        );


        alert(
            "Customer update failed"
        );

    });

}



/* =========================================
   DASHBOARD
========================================= */

function goDashboard() {

    window.location.href =
        "/dashboard";

}



/* =========================================
   LOGOUT
========================================= */

function logout() {


    localStorage.removeItem("token");


    window.location.href =
        "/login";

}



/* =========================================
   PAGE LOAD
========================================= */

window.onload = function() {

    if (checkLogin()) {

        loadCustomers();

    }

};


</script>


</body>

</html>