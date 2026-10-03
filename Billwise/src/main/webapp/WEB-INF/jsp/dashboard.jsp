<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>BillWise Dashboard</title>

<style>

* {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: #f4f6f8;
}

/* ================= SIDEBAR ================= */

.sidebar {
    position: fixed;
    left: 0;
    top: 0;
    width: 230px;
    height: 100vh;
    background: #222;
    color: white;
    padding: 25px 15px;
}

.logo {
    text-align: center;
    margin-bottom: 35px;
}

.logo h2 {
    margin: 0;
}

.logo p {
    font-size: 12px;
    color: #aaa;
}

.menu a {
    display: block;
    color: white;
    text-decoration: none;
    padding: 14px 15px;
    margin: 6px 0;
    border-radius: 6px;
}

.menu a:hover {
    background: #444;
}

.menu .active {
    background: #555;
}

/* ================= MAIN ================= */

.main {
    margin-left: 230px;
    min-height: 100vh;
}

/* ================= HEADER ================= */

.header {
    background: white;
    padding: 18px 30px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #ddd;
}

.header h2 {
    margin: 0;
}

.admin-info {
    display: flex;
    align-items: center;
    gap: 15px;
}

.logout-btn {
    background: #dc3545;
    color: white;
    border: none;
    padding: 10px 18px;
    border-radius: 5px;
    cursor: pointer;
}

.logout-btn:hover {
    background: #bb2d3b;
}

/* ================= CONTAINER ================= */

.container {
    padding: 30px;
}

.page-title {
    margin-bottom: 25px;
}

.page-title h2 {
    margin-bottom: 5px;
}

.page-title p {
    color: #777;
}

/* ================= CARDS ================= */

.cards {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
}

.card {
    background: white;
    padding: 22px;
    border-radius: 10px;
    box-shadow: 0 3px 10px rgba(0,0,0,0.08);
}

.card h3 {
    margin: 0 0 15px 0;
    font-size: 15px;
    color: #666;
}

.number {
    font-size: 30px;
    font-weight: bold;
}

/* ================= QUICK ACTION ================= */

.section-title {
    margin-top: 35px;
    margin-bottom: 15px;
}

.quick-actions {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 15px;
}

.action-btn {
    background: white;
    border: none;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 3px 10px rgba(0,0,0,0.08);
    cursor: pointer;
    font-size: 15px;
}

.action-btn:hover {
    background: #f0f0f0;
}

/* ================= REMINDER ================= */

.reminder-box {
    background: white;
    padding: 25px;
    border-radius: 10px;
    margin-top: 25px;
    box-shadow: 0 3px 10px rgba(0,0,0,0.08);
}

.reminder-row {
    display: flex;
    justify-content: space-between;
    padding: 12px 0;
    border-bottom: 1px solid #eee;
}

.reminder-row:last-child {
    border-bottom: none;
}

.success {
    color: green;
    font-weight: bold;
}

.failed {
    color: red;
    font-weight: bold;
}

/* ================= LOADING ================= */

#loading {
    color: #777;
    margin-bottom: 15px;
}

#errorMessage {
    color: red;
    display: none;
}

/* ================= RESPONSIVE ================= */

@media (max-width: 1000px) {

    .cards {
        grid-template-columns: repeat(2, 1fr);
    }

    .quick-actions {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 700px) {

    .sidebar {
        position: relative;
        width: 100%;
        height: auto;
    }

    .main {
        margin-left: 0;
    }

    .cards {
        grid-template-columns: 1fr;
    }

    .quick-actions {
        grid-template-columns: 1fr;
    }

    .header {
        flex-direction: column;
        gap: 15px;
    }

}

</style>

</head>

<body>


<!-- ================= SIDEBAR ================= -->

<div class="sidebar">

    <div class="logo">

        <h2>BillWise</h2>

        <p>Billing Management System</p>

    </div>


    <div class="menu">

        <a href="/dashboard" class="active">
            Dashboard
        </a>

        <a href="#">
            Customers
        </a>

        <a href="#">
            Invoices
        </a>

        <a href="#">
            Payments
        </a>

        <a href="#">
            Reminders
        </a>

        <a href="#">
            Scheduled Jobs
        </a>

        <a href="#">
            Reports
        </a>

    </div>

</div>


<!-- ================= MAIN ================= -->

<div class="main">


    <!-- HEADER -->

    <div class="header">

        <h2>Dashboard</h2>

        <div class="admin-info">

            <span>Admin</span>

            <button class="logout-btn"
                    onclick="logout()">
                Logout
            </button>

        </div>

    </div>


    <!-- CONTENT -->

    <div class="container">


        <div class="page-title">

            <h2>Welcome to BillWise 👋</h2>

            <p>
                Here's what's happening with your billing system.
            </p>

        </div>


        <div id="loading">
            Loading dashboard data...
        </div>

        <div id="errorMessage">
            Unable to load dashboard data.
        </div>


        <!-- ================= SUMMARY CARDS ================= -->

        <div class="cards">


            <div class="card">

                <h3>Total Customers</h3>

                <div class="number"
                     id="totalCustomers">
                    0
                </div>

            </div>


            <div class="card">

                <h3>Total Invoices</h3>

                <div class="number"
                     id="totalInvoices">
                    0
                </div>

            </div>


            <div class="card">

                <h3>Paid Invoices</h3>

                <div class="number"
                     id="paidInvoices">
                    0
                </div>

            </div>


            <div class="card">

                <h3>Pending Invoices</h3>

                <div class="number"
                     id="pendingInvoices">
                    0
                </div>

            </div>


            <div class="card">

                <h3>Overdue Invoices</h3>

                <div class="number"
                     id="overdueInvoices">
                    0
                </div>

            </div>


            <div class="card">

                <h3>Total Reminders</h3>

                <div class="number"
                     id="totalReminderAttempts">
                    0
                </div>

            </div>


            <div class="card">

                <h3>Successful Reminders</h3>

                <div class="number"
                     id="successfulReminders">
                    0
                </div>

            </div>


            <div class="card">

                <h3>Failed Reminders</h3>

                <div class="number"
                     id="failedReminders">
                    0
                </div>

            </div>


        </div>


        <!-- ================= QUICK ACTIONS ================= -->

        <h3 class="section-title">
            Quick Actions
        </h3>


        <div class="quick-actions">


            <button class="action-btn"
                    onclick="openCustomers()">

                👥 Add Customer

            </button>


            <button class="action-btn"
                    onclick="openInvoices()">

                🧾 Create Invoice

            </button>


            <button class="action-btn"
                    onclick="openPayments()">

                💰 Payments

            </button>


            <button class="action-btn"
                    onclick="openJobs()">

                ⚙ Scheduled Jobs

            </button>


        </div>


        <!-- ================= REMINDER PERFORMANCE ================= -->

        <h3 class="section-title">
            Reminder Performance
        </h3>


        <div class="reminder-box">


            <div class="reminder-row">

                <span>
                    Total Reminder Attempts
                </span>

                <strong id="reminderTotal">
                    0
                </strong>

            </div>


            <div class="reminder-row">

                <span>
                    Successful Reminders
                </span>

                <span class="success"
                      id="reminderSuccess">
                    0
                </span>

            </div>


            <div class="reminder-row">

                <span>
                    Failed Reminders
                </span>

                <span class="failed"
                      id="reminderFailed">
                    0
                </span>

            </div>


        </div>


    </div>

</div>


<!-- ================= JAVASCRIPT ================= -->

<script>


function loadDashboard() {


    const token = localStorage.getItem("token");


    // JWT nahi hai
    if (!token) {

        window.location.href = "/login";

        return;
    }


    fetch("/dashboard/summary", {

        method: "GET",

        headers: {

            "Authorization": "Bearer " + token

        }

    })


    .then(response => {


        if (!response.ok) {

            if (response.status === 401 ||
                response.status === 403) {

                localStorage.removeItem("token");

                window.location.href = "/login";

                return;

            }

            throw new Error(
                "Dashboard API failed"
            );

        }


        return response.json();

    })


    .then(data => {


        if (!data) {
            return;
        }


        /* SUMMARY CARDS */


        document.getElementById(
            "totalCustomers"
        ).innerText =
            data.totalCustomers ?? 0;


        document.getElementById(
            "totalInvoices"
        ).innerText =
            data.totalInvoices ?? 0;


        document.getElementById(
            "paidInvoices"
        ).innerText =
            data.paidInvoices ?? 0;


        document.getElementById(
            "pendingInvoices"
        ).innerText =
            data.pendingInvoices ?? 0;


        document.getElementById(
            "overdueInvoices"
        ).innerText =
            data.overdueInvoices ?? 0;


        document.getElementById(
            "totalReminderAttempts"
        ).innerText =
            data.totalReminderAttempts ?? 0;


        document.getElementById(
            "successfulReminders"
        ).innerText =
            data.successfulReminders ?? 0;


        document.getElementById(
            "failedReminders"
        ).innerText =
            data.failedReminders ?? 0;


        /* REMINDER SECTION */


        document.getElementById(
            "reminderTotal"
        ).innerText =
            data.totalReminderAttempts ?? 0;


        document.getElementById(
            "reminderSuccess"
        ).innerText =
            data.successfulReminders ?? 0;


        document.getElementById(
            "reminderFailed"
        ).innerText =
            data.failedReminders ?? 0;


        /* LOADING HIDE */


        document.getElementById(
            "loading"
        ).style.display = "none";


    })


    .catch(error => {


        console.error(error);


        document.getElementById(
            "loading"
        ).style.display = "none";


        document.getElementById(
            "errorMessage"
        ).style.display = "block";


    });

}


/* ================= LOGOUT ================= */


function logout() {

    localStorage.removeItem("token");

    window.location.href = "/login";

}


/* ================= QUICK ACTIONS ================= */

function openCustomers() {

    alert("Customer module coming next");

}


function openInvoices() {

    alert("Invoice module coming next");

}


function openPayments() {

    alert("Payment module coming next");

}


function openJobs() {

    window.location.href = "/jobs";

}


/* ================= START ================= */

loadDashboard();

</script>


</body>

</html>