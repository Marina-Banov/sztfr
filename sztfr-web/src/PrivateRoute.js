import React from "react";
import { Route, Redirect } from "react-router-dom";

/**
 * Wrapper component for restricting access to routes that require authentication.
 * User will be redirected to "/" if he can't access the component.
 *
 * @param component
 * @param hasAccess
 * @param rest
 * @returns {JSX.Element}
 * @constructor
 */
export const PrivateRoute = ({ component: Component, hasAccess, ...rest }) => {
  function render(props) {
    if (hasAccess) {
      return <Component />;
    } else {
      return (
        <Redirect to={{ pathname: "/", state: { from: props.location } }} />
      );
    }
  }

  return <Route {...rest} render={render} />;
};
