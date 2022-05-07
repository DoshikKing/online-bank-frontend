import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import Select from 'react-select'

import UserService from "../../service/user.service";

const required = value => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

export default class Transaction extends Component {
    constructor(props) {
        super(props);
        this.handleTransaction = this.handleTransaction.bind(this);
        this.onChangeTo = this.onChangeTo.bind(this);
        this.onChangeAmount = this.onChangeAmount.bind(this);
        this.onChangeComment = this.onChangeComment.bind(this);

        this.type = props.type;

        this.state = {
            from: props.id,
            to: null,
            amount: null,
            comment: "",
            cards: [],
            accounts: [],
            loading: false,
            message: ""
        };
    }
    componentDidMount() {
        this.setState({
            cards: JSON.parse(localStorage.getItem("cards")),
            accounts: JSON.parse(localStorage.getItem("accounts"))
        })
    }

    SelectBuilder() {
        if(this.type === "card"){
            return this.state.cards.map(
                (info) => {
                    return (
                        {"value": info.id, "label": info.code}
                    )
                }
            );
        } else {
            return this.state.accounts.map(
                (info) => {
                    return (
                        {"value": info.id, "label": info.accountNumber}
                    )
                }
            );
        }
    }

    onChangeTo(e) {
        this.setState({
            to: e.value
        });
    }

    onChangeAmount(e) {
        this.setState({
            amount: e.target.value
        });
    }

    onChangeComment(e) {
        this.setState({
            comment: e.target.value
        });
    }

    handleTransaction(e) {
        e.preventDefault();

        this.setState({
            message: "",
            loading: true
        });

        this.form.validateAll();

        if (this.checkBtn.context._errors.length === 0) {
            UserService.executeTransaction(this.state.from, this.state.to, parseFloat(this.state.amount), this.state.comment, this.type)
                .then(
                () => {
                    window.location.assign("/home");
                },
                error => {
                    const resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.comment ||
                        error.toString() + this.state.message;

                    this.setState({
                        loading: false,
                        message: resMessage
                    });
                }
            );
        } else {
            this.setState({
                loading: false
            });
        }
    }

    render() {
        return (
            <div className="container text-center">
                <div className="back">
                    <Form
                        onSubmit={this.handleTransaction}
                        ref={c => {
                            this.form = c;
                        }}
                    >
                        <h1 className="h3 mb-3 fw-normal">Перевод</h1>

                        <div className="col">
                            <div className="row">
                                <label htmlFor="amount">Куда перевести?</label>
                                <Select options={this.SelectBuilder()} onChange={this.onChangeTo}/>
                            </div>
                            <div className="row">
                                <label htmlFor="amount">Сумма перевода</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="amount"
                                    value={this.state.amount}
                                    onChange={this.onChangeAmount}
                                    validations={[required]}
                                />
                            </div>
                            <div className="row">
                                <label htmlFor="comment">Коментарий</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="comment"
                                    value={this.state.comment}
                                    onChange={this.onChangeComment}
                                    validations={[required]}
                                />
                            </div>
                        </div>
                        <div className="form-group">
                            <button className="btn btn-primary btn-block" disabled={this.state.loading}>
                                {this.state.loading && (
                                    <span className="spinner-border spinner-border-sm" />
                                )}
                                <span>Перевести</span>
                            </button>
                        </div>
                        {this.state.message && (
                            <div className="form-group">
                                <div className="alert alert-danger" role="alert">
                                    {this.state.message}
                                </div>
                            </div>
                        )}
                        <CheckButton
                            style={{ display: "none" }}
                            ref={c => {
                                this.checkBtn = c;
                            }}
                        />
                        <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
                    </Form>
                </div>
            </div>
        );
    }
}