import {BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import NavBar from "./NavBar";
import Welcome from "../main/Welcome";
import Login from "../main/Login";
import Home from "../main/Home";
import React from "react";

export default function NavBarRoutes() {
    return (
        <BrowserRouter>
            <NavBar />
            <Routes>
                <Route exact path="/" element={<Welcome />} />
                <Route exact path="/welcome" element={<Welcome />} />
                <Route exact path="/login" element={<Login />} />
                <Route exact path="/home" element={localStorage.getItem('currentUser') ? <Home /> : <Navigate to= "/login" replace="/login" /> } />}
                <Route />
                <Route />
            </Routes>
        </BrowserRouter>
    );
}