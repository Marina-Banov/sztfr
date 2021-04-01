import {BrowserRouter, Route, Switch} from "react-router-dom";
import Home from "./Home";
import Login from "./Login";
import LoginWithEmailLink from "./LoginWithEmailLink";

export default function App() {
  const user = null;
  return user ? <Home /> :
      <BrowserRouter>
          <Switch>
              <Route path="/login-with-email" component={LoginWithEmailLink} />
              <Route path="/" component={Login} />
          </Switch>
      </BrowserRouter>;
}
