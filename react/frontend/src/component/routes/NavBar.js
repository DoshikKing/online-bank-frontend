import {NavLink} from "react-router-dom";
import isAuthenticated from "../../helpers/isAuthenticated"

export default function NavBar() {

    return (
        <div className="navigation">
            <nav className="navbar navbar-expand navbar-dark bg-dark">
                <div className="container">
                    <NavLink className="navbar-brand" to="/" >
                        Online Bank
                    </NavLink>
                    <div>
                        <ul className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/welcome" >
                                    Welcome
                                </NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/registration" >
                                    Sign up
                                </NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/login" >
                                    Sign in
                                </NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/home" >
                                    Home
                                </NavLink>
                            </li>
                            {isAuthenticated(
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/home" >
                                        Home
                                    </NavLink>
                                </li>
                            )}
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    );
}