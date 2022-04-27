import {config} from '../configuration/api/config.js';

const axios = require('axios');


// Сервис позволяющий провести авторизацию в API
export const authenticationService = {
    login,
    logout
};

function login(username, password) {
    const data = window.btoa(username + ':' + password);
    return axios
    ({
        method: "POST",
        url:`${config.apiURL}/login`,
        headers: { 'Content-Type': 'application/json', "Accept": 'application/json', 'Authorization': `Basic ${data}`}
    })
    .then(function (response)
        {
            if(response.status === 200){
                if(!(response.data === "OK")) {
                    localStorage.setItem('currentUser', JSON.stringify( '{token: ' + data + '}'));
                }
                return "GOOD";
            }
            return "BAD";
        }
    )
}

function logout() {
    // Удаляем пользователя после его выхода
    localStorage.removeItem('currentUser');
}