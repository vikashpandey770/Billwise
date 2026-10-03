<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BillWise Login</title>

<style>
body {
    font-family: Arial, sans-serif;
    background: #f4f6f8;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.login-box {
    width: 350px;
    background: white;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.15);
}

h2 {
    text-align: center;
    margin-bottom: 25px;
}

input {
    width: 100%;
    padding: 12px;
    margin: 8px 0;
    box-sizing: border-box;
}

button {
    width: 100%;
    padding: 12px;
    margin-top: 15px;
    cursor: pointer;
}

#message {
    text-align: center;
    margin-top: 15px;
}
</style>
</head>

<body>

<div class="login-box">

    <h2>BillWise Login</h2>

    <input type="email"
           id="email"
           placeholder="Email">

    <input type="password"
           id="password"
           placeholder="Password">

    <button onclick="login()">Login</button>

    <div id="message"></div>

</div>

<script>

function login() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch("/auth/login", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            email: email,
            password: password
        })

    })

    .then(response => {

        if (!response.ok) {
            throw new Error("Login failed");
        }

        return response.json();
    })

    .then(data => {

        // JWT token save
        localStorage.setItem("token", data.token);

        // Dashboard open
        window.location.href = "/dashboard";
    })

    .catch(error => {

        document.getElementById("message").innerText =
            "Invalid email or password";

        console.error(error);
    });
}

</script>

</body>
</html>