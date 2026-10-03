<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BillWise - Invoice Management</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            margin: 0;
            padding: 0;
        }

        .header {
            background: #343a40;
            color: white;
            padding: 18px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header h2 {
            margin: 0;
        }

        .buttons button {
            margin-left: 8px;
            padding: 9px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .dashboard {
            background: #007bff;
            color: white;
        }

        .logout {
            background: #dc3545;
            color: white;
        }

        .container {
            width: 92%;
            margin: 25px auto;
        }

        .card {
            background: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            margin-bottom: 25px;
        }

        input, select, textarea {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            margin-bottom: 15px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            padding: 10px 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .save {
            background: #28a745;
            color: white;
        }

        .clear {
            background: #6c757d;
            color: white;
        }

        .edit {
            background: #ffc107;
            color: black;
        }

        .delete {
            background: #dc3545;
            color: white;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }

        th {
            background: #343a40;
            color: white;
        }

        .status-filter {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .status-filter select {
            width: 250px;
            margin: 0;
        }
    </style>
</head>

<body>

<div class="header">

    <h2>BillWise - Invoice Management</h2>

    <div class="buttons">
        <button class="dashboard" onclick="goDashboard()">
            Dashboard
        </button>

        <button class="logout" onclick="logout()">
            Logout
        </button>
    </div>

</div>


<div class="container">

    <!-- ADD / UPDATE INVOICE -->

    <div class="card">

        <h3>Add / Update Invoice</h3>

        <input type="hidden" id="invoiceId">

        <label>Invoice Number</label>
        <input type="text"
               id="invoiceNumber"
               placeholder="Enter invoice number">


        <label>Customer ID</label>
        <input type="number"
               id="customerId"
               placeholder="Enter customer ID">


        <label>Invoice Date</label>
        <input type="date"
               id="invoiceDate">


        <label>Due Date</label>
        <input type="date"
               id="dueDate">


        <label>Amount</label>
        <input type="number"
               id="amount"
               step="0.01"
               placeholder="Enter amount">


        <label>Status</label>

        <select id="status">

            <option value="NEW">NEW</option>
            <option value="PENDING">PENDING</option>
            <option value="DUE">DUE</option>
            <option value="OVERDUE">OVERDUE</option>
            <option value="PAID">PAID</option>

        </select>


        <label>Description</label>

        <textarea
            id="description"
            rows="4"
            placeholder="Enter description"></textarea>


        <button class="save" onclick="saveInvoice()">
            Save Invoice
        </button>

        <button class="clear" onclick="clearForm()">
            Clear
        </button>

    </div>


    <!-- STATUS FILTER -->

    <div class="card">

        <h3>Filter Invoices</h3>

        <div class="status-filter">

            <select id="statusFilter">

                <option value="">All Invoices</option>
                <option value="NEW">NEW</option>
                <option value="PENDING">PENDING</option>
                <option value="DUE">DUE</option>
                <option value="OVERDUE">OVERDUE</option>
                <option value="PAID">PAID</option>

            </select>

            <button class="dashboard"
                    onclick="filterInvoices()">
                Filter
            </button>

            <button class="clear"
                    onclick="loadInvoices()">
                Show All
            </button>

        </div>

    </div>


    <!-- INVOICE LIST -->

    <div class="card">

        <h3>Invoice List</h3>

        <table>

            <thead>

            <tr>
                <th>ID</th>
                <th>Invoice Number</th>
                <th>Customer ID</th>
                <th>Invoice Date</th>
                <th>Due Date</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>

            </thead>

            <tbody id="invoiceTable">

            </tbody>

        </table>

    </div>

</div>


<script>

    function getToken() {

        return localStorage.getItem("token");

    }


    // =========================
    // SAVE / UPDATE
    // =========================

    async function saveInvoice() {

        const token = getToken();

        if (!token) {

            alert("Please login first");

            window.location.href = "/login";

            return;
        }


        const id =
            document.getElementById("invoiceId").value;


        const invoice = {

            invoiceNumber:
                document.getElementById("invoiceNumber").value,

            customer: {

                id:
                    Number(document.getElementById("customerId").value)

            },

            invoiceDate:
                document.getElementById("invoiceDate").value,

            dueDate:
                document.getElementById("dueDate").value,

            amount:
                Number(document.getElementById("amount").value),

            status:
                document.getElementById("status").value,

            description:
                document.getElementById("description").value
        };


        let url = "/invoices";
        let method = "POST";


        if (id) {

            url = "/invoices/" + id;
            method = "PUT";

        }


        try {

            const response = await fetch(url, {

                method: method,

                headers: {

                    "Content-Type": "application/json",

                    "Authorization":
                        "Bearer " + token
                },

                body: JSON.stringify(invoice)

            });


            if (!response.ok) {

                const errorText = await response.text();

                console.log("Invoice Error:", errorText);

                alert(
                    "Invoice save failed. Status: "
                    + response.status
                );

                return;
            }


            const data = await response.json();

            console.log("Invoice saved:", data);

            alert(
                id
                ? "Invoice updated successfully"
                : "Invoice added successfully"
            );


            clearForm();

            loadInvoices();

        }
        catch (error) {

            console.error(error);

            alert("Error while saving invoice");

        }

    }


    // =========================
    // LOAD ALL INVOICES
    // =========================

    async function loadInvoices() {

        const token = getToken();

        if (!token) {

            window.location.href = "/login";

            return;

        }


        try {

            const response = await fetch("/invoices", {

                method: "GET",

                headers: {

                    "Authorization":
                        "Bearer " + token

                }

            });


            if (!response.ok) {

                alert(
                    "Unable to load invoices. Status: "
                    + response.status
                );

                return;
            }


            const invoices = await response.json();


            console.log("Invoices received:", invoices);


            displayInvoices(invoices);

        }
        catch (error) {

            console.error(error);

            alert("Error loading invoices");

        }

    }


    // =========================
    // FILTER
    // =========================

    async function filterInvoices() {

        const token = getToken();

        const status =
            document.getElementById("statusFilter").value;


        if (!status) {

            loadInvoices();

            return;

        }


        try {

            const response =
                await fetch(
                    "/invoices/status/" + status,
                    {

                        method: "GET",

                        headers: {

                            "Authorization":
                                "Bearer " + token

                        }

                    }
                );


            if (!response.ok) {

                alert(
                    "Filter failed. Status: "
                    + response.status
                );

                return;
            }


            const invoices =
                await response.json();


            displayInvoices(invoices);

        }
        catch (error) {

            console.error(error);

            alert("Error filtering invoices");

        }

    }


    // =========================
    // DISPLAY
    // =========================

    function displayInvoices(invoices) {

        const table =
            document.getElementById("invoiceTable");


        table.innerHTML = "";


        invoices.forEach(invoice => {

            const row =
                document.createElement("tr");


            const idCell =
                document.createElement("td");

            idCell.textContent =
                invoice.id;


            const numberCell =
                document.createElement("td");

            numberCell.textContent =
                invoice.invoiceNumber;


            const customerCell =
                document.createElement("td");

            customerCell.textContent =
                invoice.customer
                ? invoice.customer.id
                : "";


            const invoiceDateCell =
                document.createElement("td");

            invoiceDateCell.textContent =
                invoice.invoiceDate || "";


            const dueDateCell =
                document.createElement("td");

            dueDateCell.textContent =
                invoice.dueDate || "";


            const amountCell =
                document.createElement("td");

            amountCell.textContent =
                invoice.amount;


            const statusCell =
                document.createElement("td");

            statusCell.textContent =
                invoice.status;


            const descriptionCell =
                document.createElement("td");

            descriptionCell.textContent =
                invoice.description || "";


            const actionCell =
                document.createElement("td");


            const editButton =
                document.createElement("button");

            editButton.textContent = "Edit";

            editButton.className = "edit";


            editButton.onclick =
                function () {

                    editInvoice(invoice);

                };


            const deleteButton =
                document.createElement("button");

            deleteButton.textContent = "Delete";

            deleteButton.className = "delete";


            deleteButton.onclick =
                function () {

                    deleteInvoice(invoice.id);

                };


            actionCell.appendChild(editButton);

            actionCell.appendChild(
                document.createTextNode(" ")
            );

            actionCell.appendChild(deleteButton);


            row.appendChild(idCell);
            row.appendChild(numberCell);
            row.appendChild(customerCell);
            row.appendChild(invoiceDateCell);
            row.appendChild(dueDateCell);
            row.appendChild(amountCell);
            row.appendChild(statusCell);
            row.appendChild(descriptionCell);
            row.appendChild(actionCell);


            table.appendChild(row);

        });

    }


    // =========================
    // EDIT
    // =========================

    function editInvoice(invoice) {

        document.getElementById("invoiceId").value =
            invoice.id;


        document.getElementById("invoiceNumber").value =
            invoice.invoiceNumber;


        document.getElementById("customerId").value =
            invoice.customer
            ? invoice.customer.id
            : "";


        document.getElementById("invoiceDate").value =
            invoice.invoiceDate || "";


        document.getElementById("dueDate").value =
            invoice.dueDate || "";


        document.getElementById("amount").value =
            invoice.amount;


        document.getElementById("status").value =
            invoice.status;


        document.getElementById("description").value =
            invoice.description || "";


        window.scrollTo({

            top: 0,

            behavior: "smooth"

        });

    }


    // =========================
    // DELETE
    // =========================

    async function deleteInvoice(id) {

        if (!confirm(
            "Are you sure you want to delete this invoice?"
        )) {

            return;

        }


        const token = getToken();


        try {

            const response =
                await fetch(
                    "/invoices/" + id,
                    {

                        method: "DELETE",

                        headers: {

                            "Authorization":
                                "Bearer " + token

                        }

                    }
                );


            if (!response.ok) {

                alert(
                    "Delete failed. Status: "
                    + response.status
                );

                return;

            }


            alert("Invoice deleted successfully");


            loadInvoices();

        }
        catch (error) {

            console.error(error);

            alert("Error deleting invoice");

        }

    }


    // =========================
    // CLEAR FORM
    // =========================

    function clearForm() {

        document.getElementById("invoiceId").value = "";

        document.getElementById("invoiceNumber").value = "";

        document.getElementById("customerId").value = "";

        document.getElementById("invoiceDate").value = "";

        document.getElementById("dueDate").value = "";

        document.getElementById("amount").value = "";

        document.getElementById("status").value = "NEW";

        document.getElementById("description").value = "";

    }


    // =========================
    // DASHBOARD
    // =========================

    function goDashboard() {

        window.location.href = "/dashboard";

    }


    // =========================
    // LOGOUT
    // =========================

    function logout() {

        localStorage.removeItem("token");

        window.location.href = "/login";

    }


    // =========================
    // PAGE LOAD
    // =========================

    window.onload = function () {

        const token = getToken();


        if (!token) {

            window.location.href = "/login";

            return;

        }


        loadInvoices();

    };

</script>

</body>
</html>