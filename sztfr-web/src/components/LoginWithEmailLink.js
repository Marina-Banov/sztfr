import { useEffect, useState } from "react";
import { verifyEmailLink, loginWithEmailLink, observer } from "../utils/firebase";
import { TextField } from '@material-ui/core';
import { useTranslation } from "react-i18next";

export default function LoginWithEmailLink({cancel}) {
    const { t } = useTranslation();
    const savedEmail = window.localStorage.getItem("emailForSignIn") || "";
    const [email, setEmail] = useState(savedEmail);
    const [errorResponse, setErrorResponse] = useState("");

    useEffect(() => {
        observer.subscribe('firebaseErrorEvent', data => {
            setErrorResponse(data);
        })
        verifyEmailLink(savedEmail);
        return () => { observer.unsubscribe('firebaseErrorEvent'); }
    }, [savedEmail]);

    const updateEmail = (e) => {
        setErrorResponse("");
        setEmail(e.target.value);
    };

    return (
        <div>
            <TextField id="email-input"
                       className="width_100"
                       label={t('login.email')}
                       variant="outlined"
                       size="small"
                       value={email}
                       onChange={updateEmail} />
            <button className="email-button"
                    onClick={() => loginWithEmailLink(email)}>
                {t('login.login')}
            </button>
            <button className="google-button"
                    onClick={() => cancel()}>
                {t('cancel')}
            </button>
            <p>{t(errorResponse)}</p>
        </div>
    )
}
