import React, { Suspense } from "react";
import ReactDOM from "react-dom";

import App from "./components/App";
import reportWebVitals from "./reportWebVitals";
import i18n from "./i18n/i18n";
import Firebase, { FirebaseContext } from "appFirebase";

import "./index.scss";

ReactDOM.render(
  <FirebaseContext.Provider value={new Firebase()}>
    <Suspense fallback="loading">
      <App />
    </Suspense>
  </FirebaseContext.Provider>,
  document.getElementById("root")
);

reportWebVitals();
