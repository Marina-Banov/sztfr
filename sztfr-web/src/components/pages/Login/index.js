import React, { useState } from "react";
import { useTranslation } from "react-i18next";

import { useFirebase } from "appFirebase";
import google_icon from "assets/google-icon.svg";
import { LoginWithEmailLink } from "components/pages";

import "./index.scss";

export default function Login() {
  const firebase = useFirebase();
  const { t } = useTranslation();
  const [menuActive, setMenuActive] = useState(true);

  function updateMenuActive() {
    setMenuActive(!menuActive);
  }

  return (
    <div className="login-page flex_center_center">
      <div className="login-container flex_center_center">
        <div className={menuActive ? "menu active" : "menu"}>
          <h3>{t("login.login")}</h3>

          <button className="email-button" onClick={updateMenuActive}>
            {t("login.login_with_email")}
          </button>

          <button className="google-button" onClick={firebase.logInWithGoogle}>
            <span className="google-button__icon">
              <img src={google_icon} alt={t("login.login_with_google")} />
            </span>
            <span className="google-button__text">
              {t("login.login_with_google")}
            </span>
          </button>
        </div>

        <div className={menuActive ? "email-login" : "email-login active"}>
          <LoginWithEmailLink cancel={updateMenuActive} />
        </div>
      </div>
    </div>
  );
}
