import {BrowserRouter, Redirect, Route, Switch} from "react-router-dom";
import Home from './home-vibe/Home';
import Login from "./login/Login";
import EmailVerification from "./login/EmailVerification";
import {PrivateRoute} from "./PrivateRoute";
import {loggedIn, firebaseObserver} from "../firebase";
import {useEffect, useState} from "react";
import {SZTFR} from "../constants";
import React from 'react';
import '../vibe/scss/styles.scss';

export default function App() {
    const [authenticated, setAuthenticated] = useState(loggedIn());
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        firebaseObserver.subscribe(SZTFR.AUTH_STATE_CHANGED, data => {
            setAuthenticated(data);
            setIsLoading(false);
        });
        return () => { firebaseObserver.unsubscribe(SZTFR.AUTH_STATE_CHANGED); }
    }, []);

    return isLoading ? <div/> :
        <BrowserRouter>
            <Switch>
                <Route path="/" exact>
                    <Redirect to={authenticated ? "/home" : "/login"} />
                </Route>
                <PrivateRoute path="/"
                              component={Home}
                              hasAccess={authenticated} />
                <PrivateRoute path="/login" exact
                              component={Login}
                              hasAccess={!authenticated} />
                <PrivateRoute path="/email-verification" exact
                              component={EmailVerification}
                              hasAccess={!authenticated} />
                <Route path="*">
                    <Redirect to={authenticated ? "/home" : "/login"} />
                </Route>
            </Switch>
        </BrowserRouter>;
}
