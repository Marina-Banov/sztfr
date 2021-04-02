import "../styles/Login.css";
import * as React from "react";
import google_icon from "../assets/google-icon.svg";
import LoginWithEmailLink from "./LoginWithEmailLink";
import {useState} from "react";

export default function Login() {
    const [menuActive, setMenuActive] = useState(true);

    function updateMenuActive() {
        setMenuActive(!menuActive);
    }

    return (
        <div className="login-page flex_center_center">
            <div className="login-container flex_center_center">
                <div className={menuActive ? "menu active" : "menu"}>
                    <h3>Prijava</h3>

                    <button className="email-button" onClick={updateMenuActive}>
                        Prijava s email adresom
                    </button>

                    <button className="google-button">
                        <span className="google-button__icon">
                            <img src={google_icon} alt="Prijava s Google računom"/>
                        </span>
                        <span className="google-button__text">Prijava s Google računom</span>
                    </button>
                </div>

                <div className={menuActive ? "email-login" : "email-login active"}>
                    <LoginWithEmailLink cancel={updateMenuActive}/>
                </div>
            </div>
        </div>
    );
}
