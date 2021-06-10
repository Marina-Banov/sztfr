import React, { useEffect, useState } from "react";
import { CircularProgress } from "@material-ui/core";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";

import constants from "appConstants";
import { useFirebase } from "appFirebase";

export default function EmailVerification() {
  const { t } = useTranslation();
  const [message, setMessage] = useState("");
  const firebase = useFirebase();

  useEffect(() => {
    if (
      window.localStorage.getItem(constants.LOCAL_STORAGE_LOG_IN_EMAIL) ===
      "null"
    ) {
      const email = window.prompt(t("login.email_for_confirmation"));
      window.localStorage.setItem(constants.LOCAL_STORAGE_LOG_IN_EMAIL, email);
    }

    if (!firebase.verifyEmailLink()) {
      setMessage("login.invalid_verification_link");
    } else {
      firebase
        .logInWithEmailLink()
        .then(() => {
          window.localStorage.removeItem(constants.LOCAL_STORAGE_LOG_IN_EMAIL);
        })
        .catch(() => {
          setMessage("error_occurred");
        });
    }
  }, [t, firebase]);

  return (
    <div className="login-page flex_center_center">
      {!message && <CircularProgress />}
      {message && (
        <div className="login-container flex_center_center">
          <div className="menu active">
            <p className="text-center">{t(message)}</p>
            <Link to="/login">
              <button className="email-button">{t("try_again")}</button>
            </Link>
          </div>
        </div>
      )}
    </div>
  );
}
