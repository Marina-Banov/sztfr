import {createContext, useContext} from "react";
import ReactObserver from "react-event-observer";
import app from "firebase/app";
import "firebase/auth";
import {SZTFR} from "./constants";

export const FirebaseContext = createContext({});
export const useFirebase = () => useContext(FirebaseContext);

const firebaseConfig = {
    apiKey: "AIzaSyAcdr7TsUUD4MSAM5QSbkGijnWNIDxjvec",
    authDomain: "sztfr-7a759.firebaseapp.com",
    projectId: "sztfr-7a759",
    storageBucket: "sztfr-7a759.appspot.com",
    messagingSenderId: "1091954528708",
    appId: "1:1091954528708:web:5261cdeb450bee7e955491"
};

export default class Firebase {
    constructor() {
        app.initializeApp(firebaseConfig);
        this.auth = app.auth();
        this.observer = ReactObserver();
    }

    sendLoginLink = (email) => {
        this.auth.sendSignInLinkToEmail(email, {
            url: "http://localhost:3000/email-verification",
            handleCodeInApp: true,
        })
        .then(() => {
            window.localStorage.setItem(SZTFR.LOCAL_STORAGE_LOG_IN_EMAIL, email);
            this.observer.publish(SZTFR.FIREBASE_RESPONSE, "login.email_sent");
        })
        .catch(() => {
            this.observer.publish(SZTFR.FIREBASE_RESPONSE, "error_occured");
        });
    }

    logInWithEmailLink = (email) => {
        if (this.auth.isSignInWithEmailLink(window.location.href) && !!email) {
            this.auth.signInWithEmailLink(email, window.location.href)
                .then(() => {
                    window.localStorage.removeItem(SZTFR.LOCAL_STORAGE_LOG_IN_EMAIL);
                })
                .catch(() => {
                    this.observer.publish(SZTFR.FIREBASE_RESPONSE, "error_occured");
                });
            return true;
        }
        return false;
    }

    logOut = () => {
        this.auth.signOut().then();
    }

    loggedIn = () => {
        return !!this.auth.currentUser;
    }

    logInWithGoogle = () => {
        const googleProvider = new app.auth.GoogleAuthProvider();
        this.auth.signInWithPopup(googleProvider).then();
    }

    getFromDatabase = (/*ref*/) => {
        /*const dbRef = db.ref(ref);
        const locale = 'hr';
        dbRef.on('value', (data) => {
            firebaseObserver.publish(SZTFR.FIREBASE_RESPONSE, data.val()[locale]);
        }, (error) => {
            firebaseObserver.publish(SZTFR.FIREBASE_RESPONSE, error);
        })*/
        this.observer.publish(SZTFR.FIREBASE_RESPONSE, {
            computing: "Računarstvo",
            elec_eng: "Elektrotehnika",
            fun: "Zabava",
            mech_eng: "Strojarstvo",
            nav_arch: "Brodogradnja"
        });
    }
}
