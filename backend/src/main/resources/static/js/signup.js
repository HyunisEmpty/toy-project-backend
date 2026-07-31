async function signup() {

    const username = document.getElementById("username").value.trim();

    const password = document.getElementById("password").value.trim();

    const passwordCheck = document.getElementById("passwordCheck").value.trim();

    const message = document.getElementById("message");

    message.innerText = "";

    if (username.length < 4 || username.length > 20) {

        message.innerText = "아이디는 4~20자입니다.";

        return;

    }

    if (password.length < 8) {

        message.innerText = "비밀번호는 8자 이상입니다.";

        return;

    }

    if (password !== passwordCheck) {

        message.innerText = "비밀번호가 일치하지 않습니다.";

        return;

    }

    try {

        await api("/api/auth/signup", "POST", {

            username,
            password

        });

        alert("회원가입이 완료되었습니다.");

        location.href = "/login.html";

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