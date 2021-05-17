import React from "react";

import spin from "./spin.svg";
import bars from "./bars.svg";
import puff from "./puff.svg";
import dots from "./dots.svg";

export default function Loader({ small, type }) {
  let loaderType;

  const size = small ? "small" : "";

  if (type === "spin") {
    loaderType = spin;
  } else if (type === "bars") {
    loaderType = bars;
  } else if (type === "puff") {
    loaderType = puff;
  } else if (type === "dots") {
    loaderType = dots;
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
