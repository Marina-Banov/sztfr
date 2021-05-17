import React, { useState } from "react";
import { BrowserRouter, Redirect, Route, Switch } from "react-router-dom";

import { useFirebase } from "appFirebase";
import { Main } from "components/common";
import { Login, EmailVerification } from "components/pages";
import { PrivateRoute } from "PrivateRoute";

import "bootstrap/scss/bootstrap.scss";
import "font-awesome/css/font-awesome.min.css";
import "scss/index.scss";

export default function App() {
  const firebase = useFirebase();
  const [authenticated, setAuthenticated] = useState(firebase.loggedIn());
  const [isLoading, setIsLoading] = useState(true);

  firebase.auth.onAuthStateChanged((user) => {
    setAuthenticated(!!user);
    setIsLoading(false);
  });

  return isLoading ? (
    <div />
  ) : (
    <BrowserRouter>
      <Switch>
        <Route path="/" exact>
          <Redirect to={authenticated ? "/home" : "/login"} />
        </Route>
        <PrivateRoute
          path="/login"
          exact
          component={Login}
          hasAccess={!authenticated}
        />
        <PrivateRoute
          path="/email-verification"
          exact
          component={EmailVerification}
          hasAccess={!authenticated}
        />
        <PrivateRoute path="/" component={Main} hasAccess={authenticated} />
        <Route path="*">
          <Redirect to={authenticated ? "/home" : "/login"} />
        </Route>
      </Switch>
    </BrowserRouter>
  );
}
