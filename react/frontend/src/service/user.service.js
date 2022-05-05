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

    executeTransaction(from, to, amount, comment, type){
        if (type === "card"){
            return api.post("pay/with_card",{
                debit_id: from,
                credit_id: to,
                amount,
                comment
            });
        } else {
            return api.post("pay/with_account",{
                debit_id: from,
                credit_id: to,
                amount,
                comment
            });
        }

    }
}

export default new UserService();