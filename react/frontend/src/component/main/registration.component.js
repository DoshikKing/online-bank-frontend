import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import AuthService from "../../service/auth.service.js";

import icon from "./assets/android-chrome-512x512.png"

const required = value => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

export default class Registration extends Component {
    constructor(props) {
        super(props);
        this.handleRegistration = this.handleRegistration.bind(this);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
        this.onChangerRepeatPassword = this.onChangerRepeatPassword.bind(this);
        this.onChangerCode = this.onChangerCode.bind(this);

        this.state = {
            username: "",
            password: "",
            repeat_password: "",
            code: "",
            loading: false,
            message: ""
        };
    }

    onChangeUsername(e) {
        this.setState({
            username: e.target.value
        });
    }

    onChangePassword(e) {
        this.setState({
            password: e.target.value
        });
    }

    onChangerRepeatPassword(e) {
        this.setState({
            repeat_password: e.target.value
        });
    }
    onChangerCode(e) {
        this.setState({
            code: e.target.value
        });
    }

    handleRegistration(e) {
        e.preventDefault();

        this.setState({
            message: "",
            loading: true
        });

        this.form.validateAll();

        if (this.checkBtn.context._errors.length === 0) {
            if (this.state.password !== this.state.repeat_password) {
                this.setState({
                    loading: false,
                    message: "Пароли не совпадают!"
                });
            }
            AuthService.register(this.state.username, this.state.password, this.state.repeat_password, this.state.code).then(
                () => {
                    window.location.href = "/login";
                },
                error => {
                    const resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();

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
                <div className="back item-center">
                    <div className="bg-light rounded-3 auth-card">
                        <Form
                            onSubmit={this.handleRegistration}
                            ref={c => {
                                this.form = c;
                            }}
                        >
                            <img className="mb-4" src={icon} alt="" width="57" height="57"/>
                            <h1 className="h3 mb-3 fw-normal">Регистрация</h1>

                            <div className="form-group card-input">
                                <label className="card-input-label" htmlFor="username">Номер телефона</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="username"
                                    value={this.state.username}
                                    onChange={this.onChangeUsername}
                                    validations={[required]}
                                />
                            </div>
                            <div className="form-group card-input">
                                <label className="card-input-label" htmlFor="password">Пароль</label>
                                <Input
                                    type="password"
                                    className="form-control"
                                    name="password"
                                    value={this.state.password}
                                    onChange={this.onChangePassword}
                                    validations={[required]}
                                />
                            </div>
                            <div className="form-group card-input">
                                <label className="card-input-label" htmlFor="repeat_password">Повтор пароля</label>
                                <Input
                                    type="password"
                                    className="form-control"
                                    name="repeat_password"
                                    value={this.state.repeat_password}
                                    onChange={this.onChangerRepeatPassword}
                                    validations={[required]}
                                />
                            </div>
                            <div className="form-group card-input">
                                <label className="card-input-label" htmlFor="code">Код регистрации</label>
                                <Input
                                    type="password"
                                    className="form-control"
                                    name="code"
                                    value={this.state.code}
                                    onChange={this.onChangerCode}
                                    validations={[required]}
                                />
                            </div>
                            <div className="form-group">
                                <button className="btn btn-primary btn-block card-btn" disabled={this.state.loading}>
                                    {this.state.loading && (
                                        <span className="spinner-border spinner-border-sm" />
                                    )}
                                    <span>Зарегистрироваться</span>
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
            </div>
        );
    }
}