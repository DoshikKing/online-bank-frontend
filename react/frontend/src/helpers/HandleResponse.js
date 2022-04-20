import { authenticationService } from '../service/authenticationService.js';

export function HandleResponse(response) {
    // TODO: Переписанный код проверки ответов от API под axios. Нужно тестить
    if (response.status !== 401 && response.status !== 403){
        authenticationService.logout();
        location.reload(true);
        const error = (response.status && response.data);
        return Promise.reject(error);
    }
    return response;
}