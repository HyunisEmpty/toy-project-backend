async function login() {

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    const message = document.getElementById("message");

    message.innerText = "";

    if (username === "" || password === "") {

        message.innerText = "아이디와 비밀번호를 입력해주세요.";

        return;
    }

    try {

        const result = await api("/api/auth/login", "POST", {

            username,
            password

        });

        saveToken(result.accessToken);

        location.href = "/main.html";

    } catch (e) {

        message.innerText = e.message;

    }

}

window.onload = () => {

    const token = getToken();

    if (token) {
        location.href = "/main.html";
    }

};