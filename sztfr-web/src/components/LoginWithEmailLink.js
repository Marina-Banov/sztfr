import { useEffect, useState } from "react";
import { sendLoginLink, firebaseObserver } from "../firebase";
import { TextField } from '@material-ui/core';
import { useTranslation } from "react-i18next";
import { SZTFR } from "../constants";

export default function LoginWithEmailLink({cancel}) {
    const { t } = useTranslation();
    const [email, setEmail] = useState(window.localStorage.getItem(SZTFR.LOCAL_STORAGE_LOG_IN_EMAIL) || "");
    const [errorResponse, setErrorResponse] = useState("");

    useEffect(() => {
        firebaseObserver.subscribe(SZTFR.FIREBASE_RESPONSE, data => {
            setErrorResponse(data);
        });
        return () => { firebaseObserver.unsubscribe(SZTFR.FIREBASE_RESPONSE); }
    }, []);

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
                    onClick={() => sendLoginLink(email)}>
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
