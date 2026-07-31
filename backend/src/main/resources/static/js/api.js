const BASE_URL = "http://localhost:8080";

function getToken(){
    return localStorage.getItem("accessToken");
}

function saveToken(token){
    localStorage.setItem("accessToken",token);
}

function logout(){

    localStorage.removeItem("accessToken");

    location.href="/login.html";

}

async function api(url,method,data){

    const options={
        method:method,
        headers:{
            "Content-Type":"application/json"
        }
    }

    const token=getToken();

    if(token){

        options.headers["Authorization"]="Bearer "+token;

    }

    if(data){

        options.body=JSON.stringify(data);

    }

    const response=await fetch(BASE_URL+url,options);

    if(response.status===401){

        alert("로그인이 필요합니다.");

        logout();

        return;
    }

    const json=await response.json();

    if(!response.ok){

        throw json;

    }

    return json;

}