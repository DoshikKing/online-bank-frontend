import {useParams} from "react-router-dom";
import Abstract from "../component/main/abstract.component";

export default function AbstractHelper(props) {
    const { id } = useParams();
    const { type } = useParams();
    let state = { id: id, type: type};
    return new Abstract(state);
}