import api from "./api";
import TokenService from "./token.service";

class AuthService {
    login(username, password) {
        return api.post("/auth/signin", {
            username,
            password
        })
        .then(response => {
            if (response.data.accessToken) {
                TokenService.setUser(response.data);
            }
            return response.data;
        });
    }

    logout() {
        let userId = TokenService.getUser().id
        TokenService.removeUser();
        return api.post("/auth/logout", {
            userId
        });
    }

    register(login, password, repeat_password, registration_code) {
        return api.post("/auth/signup", {
            login,
            password,
            repass: repeat_password,
            registration_code
        });
    }

    getCurrentUser() {
        return TokenService.getUser();
    }
}

export default new AuthService();