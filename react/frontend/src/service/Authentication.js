import { BehaviorSubject } from 'rxjs';
const axios = require('axios');
import config from '../configuration/api/config.js';
import { HandleResponse } from '../helpers/HandleResponse.js';

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
        method: "post",
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
            const data = HandleResponse(response.data)
            // Сохраняем пользователя локально для последующих взаимодействий с API
            localStorage.setItem('currentUser', JSON.stringify(data));
            currentUserSubject.next(data);

            return data;
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