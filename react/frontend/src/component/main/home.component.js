import React, {Component} from "react";

import UserService from "../../service/user.service";
import EventBus from "../../common/EventBus";
import { NavLink } from "react-router-dom";

import person from "./assets/person.png"
import card from "./assets/card.png"



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
                localStorage.setItem("cards", JSON.stringify(this.state.cards));
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
                localStorage.setItem("accounts", JSON.stringify(this.state.accounts));
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
                    <div className="col">
                        <img src={card} className="rounded mx-auto d-block" id="cardPlaceHolder" alt="..." />
                        <div className="card-body text-center">
                            <h4>{info.code} {info.paySystem}</h4>
                            <h5>Остаток: {info.summ} ₽</h5>
                            <h6> Срок: {info.statusTime}</h6>
                            <button className="btn btn-primary btn-sm home-btn">
                                <NavLink className="nav-link link-light " to={"/abstract"} state={{id: info.id, type: "card"}}>
                                    Получить выписку
                                </NavLink>
                            </button>
                            <button className="btn btn-primary btn-sm home-btn">
                                <NavLink className="nav-link link-light " to={"/transfer"} state={{id: info.id, type: "card"}}>
                                    Перевести
                                </NavLink>
                            </button>
                        </div>
                    </div>
                )
            }
        );
    }

    AccountDataDisplay() {
        return this.state.accounts.map(
            (info) => {
                return (
                    <div className="col">
                        <img src={person} className="rounded mx-auto d-block" id="cardPlaceHolder" alt="..." />
                        <div className="card-body text-center">
                            <h4>{info.accountNumber}</h4>
                            <h5>Остаток: {info.balance} ₽</h5>
                            <h6> Срок: {info.statusTime}</h6>
                            <button className="btn btn-primary btn-sm home-btn">
                                <NavLink className="nav-link link-light " to={"/abstract"} state={{id: info.id, type: "account"}}>
                                    Получить выписку
                                </NavLink>
                            </button>
                            <button className="btn btn-primary btn-sm home-btn">
                                <NavLink className="nav-link link-light " to={"/transfer"} state={{id: info.id, type: "account"}}>
                                    Перевести
                                </NavLink>
                            </button>
                        </div>
                    </div>
                )
            }
        );
    }

    render() {
        return (
            <div className="container">
                <div className="back">
                    <div className="container">
                        <div className="row">
                            {this.CardDataDisplay()}
                        </div>
                        <div className="row">
                            {this.AccountDataDisplay()}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}