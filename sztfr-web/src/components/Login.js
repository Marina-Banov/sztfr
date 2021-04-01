import "../styles/Login.css";
import * as React from "react";
import {Link} from "react-router-dom";
import google_icon from "../assets/google-icon.svg";

export default function Login() {
    return (
        <div className="login-page flex_center_center">
            <div className="login-container flex_center_center">
                <h3>LOGIN</h3>

                <Link to='/login-with-email'><button>Prijava</button></Link>

                <button className="google-button">
                    <span className="google-button__icon">
                        <img src={google_icon} alt="Prijava s Google računom"/>
                    </span>
                    <span className="google-button__text">Prijava s Google računom</span>
                </button>
            </div>
        </div>
    );
}
