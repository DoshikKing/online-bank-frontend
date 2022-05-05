import React, {Component} from "react";

import UserService from "../../service/user.service";
import EventBus from "../../common/EventBus";
import { NavLink } from "react-router-dom";



export default class Home extends Component {
    constructor(props) {
        super(props);

        this.state = {
            cards: [],
            accounts: []
        };
    }

    componentDidMount() {
        UserService.getListOfCards().then(
            response => {
                this.setState({
                    cards: response.data
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
        UserService.getListOfAccounts().then(
            response => {
                this.setState({
                    accounts: response.data
                });
            },
            error => {
                this.setState({
                    accounts:
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

    CardDataDisplay() {
        return this.state.cards.map(
            (info) => {
                return (
                    <tr>
                        <td>{info.id}</td>
                        <td>{info.code}</td>
                        <td>{info.summ}</td>
                        <td>{info.statusTime}</td>
                        <td>{info.paySystem}</td>
                        <td><NavLink className="nav-link" to={"/abstract/card/" + info.id}>
                            Выписка
                        </NavLink></td>
                    </tr>
                )
            }
        );
    }

    AccountDataDisplay() {
        return this.state.accounts.map(
            (info) => {
                return (
                    <tr>
                        <td>{info.id}</td>
                        <td>{info.accountNumber}</td>
                        <td>{info.statusTime}</td>
                        <td>{info.balance}</td>
                        <td><NavLink className="nav-link" to={"/abstract/account/" + info.id}>
                            Выписка
                        </NavLink></td>
                    </tr>
                )
            }
        );
    }

    render() {
        return (
            <div className="container">
                <div className="table-responsive">
                    <table className="table table-striped table-sm">
                        <thead>
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">Code</th>
                            <th scope="col">Summ</th>
                            <th scope="col">Status Time</th>
                            <th scope="col">Pay System</th>
                            <th scope="col">#</th>
                        </tr>
                        </thead>
                        {this.CardDataDisplay()}
                    </table>
                    <table className="table table-striped table-sm">
                        <thead>
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">Account number</th>
                            <th scope="col">Status time</th>
                            <th scope="col">Balance</th>
                            <th scope="col">#</th>
                        </tr>
                        </thead>
                        {this.AccountDataDisplay()}
                    </table>
                </div>
            </div>
        );
    }
}