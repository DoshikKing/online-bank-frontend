import { authenticationService } from '../service/Authentication';

// TODO: См. файл сервиса Authentication.js. При его изменении измени и этот.
// Функция возвращающая специальный хедер для авторизации в API

export function authHeader() {
    // return authorization header with jwt token
    const currentUser = authenticationService.currentUserValue;
    if (currentUser && currentUser.token) {
        return { Authorization: `Basic ${currentUser.token}` };
    } else {
        return {};
    }
}