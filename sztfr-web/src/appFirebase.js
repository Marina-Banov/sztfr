import { SZTFR } from "appConstants";
import { createContext, useContext } from "react";
import app from "firebase/app";
import "firebase/auth";
import "firebase/database";
import request from "superagent";

import { buildURL } from "utils/buildURL";

export const FirebaseContext = createContext({});
export const useFirebase = () => useContext(FirebaseContext);

const firebaseConfig = {
  apiKey: process.env.REACT_APP_API_KEY,
  authDomain: process.env.REACT_APP_AUTH_DOMAIN,
  projectId: process.env.REACT_APP_PROJECT_ID,
  storageBucket: process.env.REACT_APP_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_APP_ID,
};

export default class Firebase {
  constructor() {
    app.initializeApp(firebaseConfig);
    this.auth = app.auth();
    this.auth.onAuthStateChanged(() => {
      if (!!this.auth.currentUser) {
        this.auth.currentUser
          .getIdToken(false)
          .then((token) => (this.userToken = token));
      } else {
        this.userToken = "";
      }
    });
  }

  sendLoginLink = (email) => {
    return this.auth.sendSignInLinkToEmail(email, {
      url: "http://localhost:3000/email-verification",
      handleCodeInApp: true,
    });
  };

  verifyEmailLink = () => {
    const email = window.localStorage.getItem(SZTFR.LOCAL_STORAGE_LOG_IN_EMAIL);
    return this.auth.isSignInWithEmailLink(window.location.href) && !!email;
  };

  logInWithEmailLink = () => {
    const email = window.localStorage.getItem(SZTFR.LOCAL_STORAGE_LOG_IN_EMAIL);
    return this.auth.signInWithEmailLink(email, window.location.href);
  };

  logInWithGoogle = () => {
    const googleProvider = new app.auth.GoogleAuthProvider();
    this.auth.signInWithPopup(googleProvider).then();
  };

  logOut = () => {
    this.auth.signOut().then();
  };

  loggedIn = () => {
    return !!this.auth.currentUser;
  };

  firestoreCreate = (path, body) => {
    return request()
      .post(buildURL(process.env.REACT_APP_API_PATH, path))
      .set("Authorization", "Bearer " + this.userToken)
      .send(body);
  };

  firestoreRead = (path) => {
    return request.get(buildURL(process.env.REACT_APP_API_PATH, path));
  };

  firestoreUpdate = (path, body) => {
    return request
      .put(buildURL(process.env.REACT_APP_API_PATH, path))
      .set("Authorization", "Bearer " + this.userToken)
      .send(body);
  };
}