import { BehaviorSubject } from 'rxjs';
const axios = require('axios');
import config from '../configuration/api/config.js';
import { HandleResponse } from '../helpers/HandleResponse.js';

// TODO: Данный код проводит авторизацию с запоминанием токена авторизации. В моём случае авторизация происходит через Basic Authorization,
//  поэтому нужно либо переписать так чтобы он запоминал толлько пароль и логин пользователя временно или переделать api на использование JWT токена.
// Сервис позволяющий провести авторизацию в API

const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));

export const authenticationService = {
    login,
    logout,
    currentUser: currentUserSubject.asObservable(),
    get currentUserValue () { return currentUserSubject.value }
};

function login(username, password) {
    // TODO: Переписанный код на axios. Нужно тестить.
    axios
    ({
        method: "POST",
        url:`${config.apiURL}/authenticate`,
        headers: { 'Content-Type': 'application/json' },
        data:
        {
            username: username,
            password: password
        }
    }) // TODO: response.data содержит весь body нужно конкретно указать токен. Типа response.data.token
    .then(function (response)
        {
            const data = HandleResponse(response)
            // Сохраняем пользователя локально для последующих взаимодействий с API
            localStorage.setItem('currentUser', JSON.stringify(data.data));
            currentUserSubject.next(data.data);

            return data.data;
        }
    )
    // Старый код. Используется fetch()
    // const requestOptions = {
    //     method: 'POST',
    //     headers: { 'Content-Type': 'application/json' },
    //     body: JSON.stringify({ username, password })
    // };
    //
    // return fetch(`${config.apiURL}/users/authenticate`, requestOptions)
    //     .then(handleResponse)
    //     .then(user => {
    //         // store user details and jwt token in local storage to keep user logged in between page refreshes
    //         localStorage.setItem('currentUser', JSON.stringify(user));
    //         currentUserSubject.next(user);
    //
    //         return user;
    //     });
}

function logout() {
    // Удаляем пользователя после его выхода
    localStorage.removeItem('currentUser');
    currentUserSubject.next(null);
}