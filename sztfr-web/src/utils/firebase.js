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
export const googleProvider = new firebase.auth.GoogleAuthProvider();
export let observer = ReactObserver();

export async function loginWithEmailLink(email) {
    if (auth.isSignInWithEmailLink(window.location.href) && !!email) {
        auth.signInWithEmailLink(email, window.location.href)
            .catch(() => {
                observer.publish('firebaseErrorEvent', "An unknown error has occured");
            });
    } else {
        auth.sendSignInLinkToEmail(email, {
                url: "http://localhost:3000/signin",
                handleCodeInApp: true,
            })
            .then(() => {
                window.localStorage.setItem("emailForSignIn", email);
                observer.publish('firebaseErrorEvent', "The email was sent!");
            })
            .catch(() => {
                observer.publish('firebaseErrorEvent', "An unknown error has occured");
            });
    }
}

export function verifyEmailLink(email) {
    if (auth.isSignInWithEmailLink(window.location.href) && !!email) {
        auth.signInWithEmailLink(email, window.location.href).then();
    }
}
