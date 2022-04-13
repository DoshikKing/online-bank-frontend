import { authenticationService } from '../service/Authentication.js';

export function HandleResponse(response) {
    // TODO: Переписанный код проверки ответов от API под axios. Нужно тестить
    if (response.status !== 401 && response.status !== 403){
        authenticationService.logout();
        location.reload(true);
        const error = (response.status && response.data);
        return Promise.reject(error);
    }
    return response;

    // Старый код
    // return response.text().then(text => {
    //     const data = text && JSON.parse(text);
    //     if (!response.ok) {
    //         if ([401, 403].indexOf(response.status) !== -1) {
    //             // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
    //             authenticationService.logout();
    //             location.reload(true);
    //         }
    //
    //         const error = (data && data.message) || response.statusText;
    //         return Promise.reject(error);
    //     }
    //
    //     return data;
    // });
}