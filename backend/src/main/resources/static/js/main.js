window.onload = async () => {

    await loadUser();

    await loadMemos();

};

async function loadUser() {

    try {

        const user = await api("/api/users/me", "GET");

        document.getElementById("welcome").innerText =
            user.username + "님 환영합니다.";

    } catch (e) {

        alert(e.message);

    }

}

async function loadMemos() {

    try {

        const memos = await api("/api/memos", "GET");

        const memoList = document.getElementById("memoList");

        memoList.innerHTML = "";

        if (memos.length === 0) {

            memoList.innerHTML = "<p>작성한 메모가 없습니다.</p>";

            return;

        }

        memos.forEach(memo => {

            const card = document.createElement("div");

            card.className = "memo-card";

            card.innerHTML = `
                <h3>${memo.title}</h3>
                <p>${memo.content}</p>
                <small>${memo.createdAt}</small>
            `;

            memoList.appendChild(card);

        });

    } catch (e) {

        alert(e.message);

    }

}

async function createMemo() {

    const title = document.getElementById("title").value.trim();

    const content = document.getElementById("content").value.trim();

    if (title === "") {

        alert("제목을 입력하세요.");

        return;

    }

    if (content === "") {

        alert("내용을 입력하세요.");

        return;

    }

    try {

        await api("/api/memos", "POST", {

            title,

            content

        });

        document.getElementById("title").value = "";

        document.getElementById("content").value = "";

        await loadMemos();

    }

    catch (e) {

        alert(e.message);

    }

}

window.onload = async () => {

    if (!getToken()) {

        location.href = "/login.html";

        return;
    }

    await loadUser();

    await loadMemos();

};