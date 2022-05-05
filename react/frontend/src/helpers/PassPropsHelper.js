import {useLocation} from "react-router-dom";
import Abstract from "../component/main/abstract.component";
import Transaction from "../component/main/transaction.component";

export default function PassPropsHelper() {
    let location = useLocation();
    let state = { id: location.state.id, type: location.state.type};
    return new Abstract(state);
}

export function PassPropsHelperTransactions() {
    let location = useLocation();
    let state = { id: location.state.id, type: location.state.type};
    return new Transaction(state);
}