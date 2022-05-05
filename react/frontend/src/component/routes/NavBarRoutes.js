import React, {Component} from "react";
import {BrowserRouter, Routes, Route, NavLink} from "react-router-dom";
import Welcome from "../main/welcome.component";
import Login from "../main/login.component"
import Home from "../main/home.component";
import Abstract from "../main/abstract.component";
import AbstractHelper from "../../helpers/AbstractHelper"

import AuthService from "../../service/auth.service";
import EventBus from "../../common/EventBus";

class NavBarRoutes extends Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            currentUser: undefined
        };
    }

    componentDidMount() {
        const user = AuthService.getCurrentUser();

        if (user) {
            this.setState({
                currentUser: user
            });
        }

        EventBus.on("logout", () => {
            this.logOut();
        });
    }

    componentWillUnmount() {
        EventBus.remove("logout");
    }

    logOut() {
        AuthService.logout();
        this.setState({
            currentUser: undefined
        });
    }
    render() {
        const {currentUser} = this.state;
        return (
            <div>
                <BrowserRouter>
                    <div className="navigation">
                        <nav className="navbar navbar-expand navbar-dark bg-dark">
                            <div className="container">
                                <NavLink className="navbar-brand" to="/" >
                                    Online Bank
                                </NavLink>
                                <div>
                                    <ul className="navbar-nav ml-auto">
                                        {currentUser ?
                                            (
                                                <div>
                                                    <li className="nav-item">
                                                        <NavLink className="nav-link" to="/home" >
                                                            Home
                                                        </NavLink>
                                                    </li>
                                                    <li className="nav-item">
                                                        <a href="/login" className="nav-link" onClick={this.logOut}>
                                                            LogOut
                                                        </a>
                                                    </li>
                                                </div>
                                            )
                                            :
                                            (
                                                <div className="container">
                                                    <li className="nav-item">
                                                        <NavLink className="nav-link" to="/" >
                                                            Welcome
                                                        </NavLink>
                                                    </li>
                                                    <li className="nav-item">
                                                        <NavLink className="nav-link" to="/login" >
                                                            Sign in
                                                        </NavLink>
                                                    </li>
                                                    <li className="nav-item">
                                                        <NavLink className="nav-link" to="/registration" >
                                                            Sign up
                                                        </NavLink>
                                                    </li>
                                                </div>
                                            )}
                                    </ul>
                                </div>
                            </div>
                        </nav>
                    </div>
                    <Routes>
                        <Route exact path="/" element={<Welcome />} />
                        <Route exact path="login" element={<Login/>} />
                        <Route exact path="home" element={<Home/>} />
                        <Route exact path="abstract/:type/:id" element={<AbstractHelper /> } />
                        <Route
                            path="*"
                            element={
                                <main style={{ padding: "1rem" }}>
                                    <p>There's nothing here!</p>
                                </main>
                            }
                        />
                    </Routes>
                </BrowserRouter>
            </div>
        );
    }
}
export default NavBarRoutes;