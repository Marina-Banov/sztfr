import * as React from "react";
import {useEffect, useState} from "react";
import {firebaseObserver, logInWithEmailLink} from "../../firebase";
import {CircularProgress} from "@material-ui/core";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import {SZTFR} from "../../constants";

export default function EmailVerification() {
    const { t } = useTranslation();
    const [message, setMessage] = useState("");

    useEffect(() => {
        firebaseObserver.subscribe(SZTFR.FIREBASE_RESPONSE, data => {
            setMessage(data);
        });

        let savedEmail = window.localStorage.getItem(SZTFR.LOCAL_STORAGE_LOG_IN_EMAIL);
        if (savedEmail === 'null') {
            savedEmail = window.prompt(t('login.email_for_confirmation'));
            window.localStorage.setItem(SZTFR.LOCAL_STORAGE_LOG_IN_EMAIL, savedEmail);
        }
        if (!logInWithEmailLink(savedEmail)) {
            setMessage('login.invalid_verification_link');
        }

        return () => { firebaseObserver.unsubscribe(SZTFR.FIREBASE_RESPONSE); }
    }, [message, t]);

    return (
        <div className="login-page flex_center_center">
            {!message && <CircularProgress />}
            {message &&
            <div className="login-container flex_center_center">
                <div className="menu active">
                    <p className="text-center">{t(message)}</p>
                    <Link to="/login">
                        <button className="email-button">
                            {t('try_again')}
                        </button>
                    </Link>
                </div>
            </div>}
        </div>
    );
}
