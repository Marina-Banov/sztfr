import { createContext, useContext } from "react";
import ReactObserver from "react-event-observer";
import app from "firebase/app";
import "firebase/auth";
import "firebase/database";
import request from "superagent";

import { SZTFR } from "appConstants";
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
    this.db = app.database();
    this.observer = ReactObserver();
  }

  sendLoginLink = (email) => {
    this.auth
      .sendSignInLinkToEmail(email, {
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
  };

  logInWithEmailLink = (email) => {
    if (this.auth.isSignInWithEmailLink(window.location.href) && !!email) {
      this.auth
        .signInWithEmailLink(email, window.location.href)
        .then(() => {
          window.localStorage.removeItem(SZTFR.LOCAL_STORAGE_LOG_IN_EMAIL);
        })
        .catch(() => {
          this.observer.publish(SZTFR.FIREBASE_RESPONSE, "error_occured");
        });
      return true;
    }
    return false;
  };

  logOut = () => {
    this.auth.signOut().then();
  };

  loggedIn = () => {
    return !!this.auth.currentUser;
  };

  logInWithGoogle = () => {
    const googleProvider = new app.auth.GoogleAuthProvider();
    this.auth.signInWithPopup(googleProvider).then();
  };

  getFromDatabase = (ref) => {
    /*const dbRef = this.db.ref(ref);
        dbRef.on('value', (data) => {
            this.observer.publish(SZTFR.FIREBASE_RESPONSE, data.val());
        }, (error) => {
            this.observer.publish(SZTFR.FIREBASE_RESPONSE, error);
        })*/
    this.observer.publish(SZTFR.FIREBASE_RESPONSE, {
      0: "Zabava",
      1: "Strojarstvo",
      2: "Brodogradnja",
      3: "Elektrotehnika",
      4: "Računarstvo",
    });
  };

  getTags = () => {
    return request.get(buildURL(process.env.REACT_APP_API_PATH, "/tags/hr"));
  };

  updateTags = (tags) => {
    return request
      .put(buildURL(process.env.REACT_APP_API_PATH, "/tags/hr"))
      .send({ tags });
  };

  getEvents = () => {
    return request.get(buildURL(process.env.REACT_APP_API_PATH, "/events"));
  };

  getSurveys = () => {
    return request.get(buildURL(process.env.REACT_APP_API_PATH, "/surveys"));
  };

  addEvent = (e) => {
    return request()
      .post(buildURL(process.env.REACT_APP_API_PATH, "/events"))
      .send(e);
    /*
    var id: String,
            val imgSrcUrl: String,
            val title: String,
            val startTime: String,
            val location: String,
            val organisation: String,
            val tags: List<String>,
            val description: String
     */
  };

  writeToDatabase = (ref, data) => {
    this.db
      .ref(ref)
      .set(data)
      .then(
        (res) => this.observer.publish(SZTFR.FIREBASE_RESPONSE, res),
        (err) => this.observer.publish(SZTFR.FIREBASE_ERROR, err)
      );
  };
}
