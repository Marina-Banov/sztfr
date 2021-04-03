import {BrowserRouter, Route, Switch} from "react-router-dom";
import Home from "./Home";
import Login from "./Login";
import EmailVerification from "./EmailVerification";

export default function App() {
  const user = null;
  return user ? <Home /> :
      <BrowserRouter>
          <Switch>
              <Route path="/email-verification" component={EmailVerification} />
              <Route path="/" component={Login} />
          </Switch>
      </BrowserRouter>;
}
