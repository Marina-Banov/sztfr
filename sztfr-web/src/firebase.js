import firebase from "firebase/app";
import "firebase/auth";
import "firebase/firestore";
import ReactObserver from 'react-event-observer';

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
export const firestore = firebase.firestore();
export const firebaseObserver = ReactObserver();

auth.onAuthStateChanged(function(user) {
    firebaseObserver.publish("authStateChanged", loggedIn())
});

export async function loginWithEmailLink(email) {
    if (!verifyEmailLink(email)) {
        auth.sendSignInLinkToEmail(email, {
                url: "http://localhost:3000/email-verification",
                handleCodeInApp: true,
            })
            .then(() => {
                window.localStorage.setItem("emailForSignIn", email);
                firebaseObserver.publish('firebaseErrorEvent', "login.email_sent");
            })
            .catch(() => {
                firebaseObserver.publish('firebaseErrorEvent', "error_occured");
            });
    }
}

/**
 * Verifies the user went through an email link.
 * @param email
 * @returns {boolean}
 */
export function verifyEmailLink(email) {
    if (auth.isSignInWithEmailLink(window.location.href) && !!email) {
        auth.signInWithEmailLink(email, window.location.href)
            .catch(() => {
                firebaseObserver.publish('firebaseErrorEvent', "error_occured");
            });
        return true;
    }
    return false;
}

export function signOut() {
    auth.signOut().then();
}

export function loggedIn() {
    return !!auth.currentUser;
}

export async function loginWithGoogle() {
    const googleProvider = new firebase.auth.GoogleAuthProvider();
    auth.signInWithPopup(googleProvider).then();
}
