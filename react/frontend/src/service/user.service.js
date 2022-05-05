import api from './api';

class UserService {
    getListOfCards() {
        return api.get("/list/cards");
    }

    getListOfAccounts() {
        return api.get("/list/accounts");
    }

    getCardAbstract(path) {
        return api.get("abstract/card/" + path);
    }

    getAccountAbstract(path) {
        return api.get("abstract/account/" + path);
    }

    getPublicContent() {
        return api.get('/test/all');
    }

    getUserBoard() {
        return api.get('/test/user');
    }

    getModeratorBoard() {
        return api.get('/test/mod');
    }

    getAdminBoard() {
        return api.get('/test/admin');
    }
}

export default new UserService();