import React from "react";

import PageAlertContext from "./PageAlertContext";

export default function Page({ children }) {
  return (
    <PageAlertContext.Consumer>
      {(context) => {
        const hasPageAlertClass = context.alert ? "has-alert" : "";
        return (
          <div id="page-content" className={`${hasPageAlertClass}`}>
            {children}
          </div>
        );
      }}
    </PageAlertContext.Consumer>
  );
}
