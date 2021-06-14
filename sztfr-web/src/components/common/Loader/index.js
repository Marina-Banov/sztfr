import React from "react";

import spin from "assets/spin.svg";
import bars from "assets/bars.svg";

export default function Loader({ small, type }) {
  let loaderType;

  const size = small ? "small" : "";

  if (type === "spin") {
    loaderType = spin;
  } else if (type === "bars") {
    loaderType = bars;
  } else {
    loaderType = spin;
  }

  return (
    <div
      className={`loader ${size}`}
      style={{ backgroundImage: `url(${loaderType})` }}
    >
      Loading...
    </div>
  );
}
