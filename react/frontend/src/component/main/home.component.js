import React, {Component} from "react";

import UserService from "../../service/user.service";



export default class Home extends Component {
    constructor(props) {
        super(props);

        this.state = {
            cards: [],
            accounts: []
        };
    }

    componentDidMount() {
        console.log(localStorage.getItem("user"));
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
                        </tr>
                        </thead>
                        {this.AccountDataDisplay()}
                    </table>
                </div>
            </div>
        );
    }
}