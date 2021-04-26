import firebase from "firebase/app";
import "firebase/auth";
import ReactObserver from 'react-event-observer';
import {SZTFR} from "./constants";

const firebaseConfig = {
    apiKey: "AIzaSyAcdr7TsUUD4MSAM5QSbkGijnWNIDxjvec",
    authDomain: "sztfr-7a759.firebaseapp.com",
    projectId: "sztfr-7a759",
    storageBucket: "sztfr-7a759.appspot.com",
    messagingSenderId: "1091954528708",
    appId: "1:1091954528708:web:5261cdeb450bee7e955491"
};
firebase.initializeApp(firebaseConfig);
export const auth = firebase.auth();
export const firebaseObserver = ReactObserver();

auth.onAuthStateChanged(function() {
    firebaseObserver.publish(SZTFR.AUTH_STATE_CHANGED, loggedIn())
});

export async function sendLoginLink(email) {
    auth.sendSignInLinkToEmail(email, {
        url: "http://localhost:3000/email-verification",
        handleCodeInApp: true,
    })
    .then(() => {
        window.localStorage.setItem(SZTFR.LOCAL_STORAGE_LOG_IN_EMAIL, email);
        firebaseObserver.publish(SZTFR.FIREBASE_RESPONSE, "login.email_sent");
    })
    .catch(() => {
        firebaseObserver.publish(SZTFR.FIREBASE_RESPONSE, "error_occured");
    });
}

export function logInWithEmailLink(email) {
    if (auth.isSignInWithEmailLink(window.location.href) && !!email) {
        auth.signInWithEmailLink(email, window.location.href)
            .then(() => {
                window.localStorage.removeItem(SZTFR.LOCAL_STORAGE_LOG_IN_EMAIL);
            })
            .catch(() => {
                firebaseObserver.publish(SZTFR.FIREBASE_RESPONSE, "error_occured");
            });
        return true;
    }
    return false;
}

export function logOut() {
    auth.signOut().then();
}

export function loggedIn() {
    return !!auth.currentUser;
}

export async function logInWithGoogle() {
    const googleProvider = new firebase.auth.GoogleAuthProvider();
    auth.signInWithPopup(googleProvider).then();
}
