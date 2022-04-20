// Функция возвращающая специальный хедер для авторизации в API
export function authHeader() {
    // Получаем токен для авторизации из хранилища
    let user = JSON.parse(localStorage.getItem("currentUser"));
    if (user && user.token) {
        return { Authorization: `Basic ${user.token}` };
    } else {
        return {};
    }
}