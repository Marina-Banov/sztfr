import React from "react";

import PageLoaderContext from "./PageLoaderContext";

export default function PageLoader() {
  return (
    <PageLoaderContext.Consumer>
      {(context) => <Loader percent={context.percent} />}
    </PageLoaderContext.Consumer>
  );
}

function Loader({ percent }) {
  const loading = percent && percent > 0;
  const loaderStyle = {
    width: `${percent}%`,
  };

  const wrapStyle = {
    opacity: loading && percent < 100 ? "1" : "0",
  };

  return (
    <div className="PageLoaderWrap" style={wrapStyle}>
      <div className="PageLoader" style={loaderStyle} />
    </div>
  );
}
