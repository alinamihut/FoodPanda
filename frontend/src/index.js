import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Home from './Home';
import Login from "./components/Login";
import {Route, BrowserRouter, Routes} from "react-router-dom";
import Register from "./components/Register";
import CustomerProfile from "./components/CustomerProfile";
import AdminProfile from "./components/AdminProfile";
import ViewMenu from "./components/ViewMenu";
import ViewMenuCustomer from "./components/ViewMenuCustomer";
export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route exact path="/" element={<Home/>}/>
                    <Route exact path="/login" element = {<Login/>} />
                <Route exact path="/register" element = {<Register/>} />
                <Route exact path="/customerprofile" element = {<CustomerProfile/>} />
                <Route exact path="/adminprofile" element = {<AdminProfile/>} />
                <Route exact path="/viewmenu" element = {<ViewMenu/>} />
                <Route exact path="/viewmenucustomer" element = {<ViewMenuCustomer/>} />
            </Routes>
        </BrowserRouter>
    );
}


ReactDOM.render(<App />, document.getElementById('root'));

