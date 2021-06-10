import { useState } from "react";
import { TextField } from "@material-ui/core";
import { useTranslation } from "react-i18next";

import { useFirebase } from "appFirebase";
import constants from "appConstants";

export default function LoginWithEmailLink({ cancel }) {
  const { t } = useTranslation();
  const [email, setEmail] = useState(
    window.localStorage.getItem(constants.LOCAL_STORAGE_LOG_IN_EMAIL) || ""
  );
  const [errorResponse, setErrorResponse] = useState("");
  const firebase = useFirebase();

  function sendLoginLink() {
    firebase
      .sendLoginLink(email)
      .then(() => {
        window.localStorage.setItem(
          constants.LOCAL_STORAGE_LOG_IN_EMAIL,
          email
        );
        setErrorResponse("login.email_sent");
      })
      .catch(() => {
        setErrorResponse("error_occurred");
      });
  }

  const updateEmail = (e) => {
    setErrorResponse("");
    setEmail(e.target.value);
  };

  return (
    <div>
      <TextField
        id="email-input"
        className="width_100"
        label={t("login.email")}
        variant="outlined"
        size="small"
        value={email}
        onChange={updateEmail}
      />
      <button className="email-button" onClick={() => sendLoginLink(email)}>
        {t("login.login")}
      </button>
      <button className="google-button" onClick={() => cancel()}>
        {t("cancel")}
      </button>
      <p>{t(errorResponse)}</p>
    </div>
  );
}
