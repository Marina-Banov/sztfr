import React, {useState} from 'react';
import {BrowserRouter, Redirect, Route, Switch} from "react-router-dom";
import Home from './home-vibe/Home';
import Login from "./login/Login";
import EmailVerification from "./login/EmailVerification";
import {PrivateRoute} from "./PrivateRoute";
import {useFirebase} from "../firebase";
import '../vibe/scss/styles.scss';

export default function App() {
    const firebase = useFirebase();
    const [authenticated, setAuthenticated] = useState(firebase.loggedIn());
    const [isLoading, setIsLoading] = useState(true);

    firebase.auth.onAuthStateChanged((user) => {
        setAuthenticated(!!user);
        setIsLoading(false);
    });

    return isLoading ? <div/> :
        <BrowserRouter>
            <Switch>
                <Route path="/" exact>
                    <Redirect to={authenticated ? "/home" : "/login"} />
                </Route>
                <PrivateRoute path="/login" exact
                              component={Login}
                              hasAccess={!authenticated} />
                <PrivateRoute path="/email-verification" exact
                              component={EmailVerification}
                              hasAccess={!authenticated} />
                <PrivateRoute path="/"
                              component={Home}
                              hasAccess={authenticated} />
                <Route path="*">
                    <Redirect to={authenticated ? "/home" : "/login"} />
                </Route>
            </Switch>
        </BrowserRouter>;
}
