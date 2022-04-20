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
        mode: "cors",
        withCredentials: true,
        url:`${config.apiURL}/login`,
        headers: { 'Content-Type': 'application/json', 'Authorization': `Basic ${data}`,
            'Access-Control-Allow-Origin': 'http://localhost:8080',
            'Access-Control-Allow-Headers': 'origin, content-type, accept, authorization'}
    })
    .then(function (response)
        {
            if(!(response.data === "OK")) {
                localStorage.setItem('currentUser', JSON.stringify( '{token: ' + data + '}'));
            }
            return localStorage.getItem('currentUser');
        }
    )
}

function logout() {
    // Удаляем пользователя после его выхода
    localStorage.removeItem('currentUser');
}