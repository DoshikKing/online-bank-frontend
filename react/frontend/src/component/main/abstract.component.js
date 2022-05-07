import React, {Component} from "react";

import UserService from "../../service/user.service";
import EventBus from "../../common/EventBus";

export default class Abstract extends Component {
    constructor(props) {
        super(props);
        this.path = props.id;
        this.type = props.type;

        this.state = {
            transactions: []
        };
    }

    componentDidMount() {
        console.log(this.type);
        if (this.type === "card"){
            UserService.getCardAbstract(this.path).then(
                response => {
                    this.setState({
                        transactions: response.data
                    });
                },
                error => {
                    this.setState({
                        cards:
                            (error.response && error.response.data) ||
                            error.message ||
                            error.toString()
                    });
                    if (error.response && error.response.status === 403) {
                        EventBus.dispatch("logout");
                    }
                }
            );
        } else {
            UserService.getAccountAbstract(this.path).then(
                response => {
                    this.setState({
                        transactions: response.data
                    });
                },
                error => {
                    this.setState({
                        cards:
                            (error.response && error.response.data) ||
                            error.message ||
                            error.toString()
                    });
                    if (error.response && error.response.status === 403) {
                        EventBus.dispatch("logout");
                    }
                }
            );
        }
    }

    TransactionDataDisplay() {
        return this.state.transactions.map(
            (info) => {
                return (
                    <tr>
                        <td>{info.code}</td>
                        <td>{info.summ}</td>
                        <td>{info.comment}</td>
                        <td>{info.transactionTime}</td>
                    </tr>
                )
            }
        );
    }

    render() {
        return (
            <div className="container">
                <div className="table-responsive back">
                    <table className="table table-striped table-sm">
                        <thead>
                        <tr>
                            <th scope="col">Номер</th>
                            <th scope="col">Сумма</th>
                            <th scope="col">Комментарий</th>
                            <th scope="col">Время транзакции</th>
                        </tr>
                        </thead>
                        {this.TransactionDataDisplay()}
                    </table>
                </div>
            </div>
        );
    }
}