import {BrowserRouter, Routes, Route} from "react-router-dom";
import NavBar from "./NavBar";
import Welcome from "./page/Welcome";
import Login from "./page/Login";

export default function NavBarRoutes() {
    return (
        <BrowserRouter>
            <NavBar />
            <Routes>
                <Route exact path="/welcome" element={<Welcome />}>

                </Route>
                <Route exact path="/login" element={<Login />}>

                </Route>
                <Route>

                </Route>
                <Route>

                </Route>
                <Route>

                </Route>
            </Routes>
        </BrowserRouter>
    );
}