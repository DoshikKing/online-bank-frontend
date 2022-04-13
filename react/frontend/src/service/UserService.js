import {config} from '../configuration/api/config.js';
import { HandleResponse } from '../helpers/HandleResponse';
import { authHeader as Authorization } from "../helpers/Authorization";
const axios = require('axios');

export const userService = {
    getAllCards: getListOfCards,
    getAllAccounts: getListOfAccounts,
    getAbstract,
    executeCardTransaction,
    executeAccountTransaction,
    //getAll,
    getById
};

function getListOfCards() {
    axios
    ({
        method: "GET",
        url:`${config.apiURL}/getListOfCards`,
        headers: { 'Authorization': Authorization }
    })
    .then(function (response){
        return HandleResponse(response)
    })
}

function getListOfAccounts() {
    axios
    ({
        method: "GET",
        url:`${config.apiURL}/getListOfAccounts`,
        headers: { 'Authorization': Authorization }
    })
    .then(function (response){
        return HandleResponse(response)
    })
}

function getAbstract(id) {
    axios
    ({
        method: "POST",
        url:`${config.apiURL}/getAbstract`,
        headers: { 'Authorization': Authorization },
        data:
        {
            id: id
        }
    })
    .then(function (response){
        return HandleResponse(response)
    })
}

function executeCardTransaction(debit_id, credit_id) {
    // TODO: Написать работу с выполнением транзакций
}

function executeAccountTransaction(debit_id, credit_id) {
    // TODO: Написать работу с выполнением транзакций
}

// function getAll() {
//
//     // Старый код на fetch()
//     // const requestOptions = { method: 'GET', headers: authHeader() };
//     // return fetch(`${config.apiURL}/users`, requestOptions).then(handleResponse);
// }

function getById(id) {

    // Старый код на fetch()
    // const requestOptions = { method: 'GET', headers: authHeader() };
    // return fetch(`${config.apiURL}/users/${id}`, requestOptions).then(handleResponse);
}