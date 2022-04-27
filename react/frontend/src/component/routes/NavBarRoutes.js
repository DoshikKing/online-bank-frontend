import React from "react";
import {BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import NavBar from "./NavBar";
import Welcome from "../main/Welcome";
import Login from "../main/Login";
import Home from "../main/Home";

export default function NavBarRoutes() {
    return (
        <BrowserRouter>
            <NavBar />
            <Routes>
                <Route exact path="/" element={<Welcome />} />
                <Route exact path="login" element={<Login />} />
                <Route exact path="home" element={localStorage.getItem('currentUser') != null ? <Home /> : <Navigate to= "/login" replace="/login" /> } />}
                <Route />
                <Route />
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
    );
}