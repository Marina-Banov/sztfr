import React from "react";
import ReactstrapDatePicker from "reactstrap-date-picker";

import "./index.scss";

export default function DatePicker({ ...props }) {
  const locale = "hr";

  function months() {
    const format = new Intl.DateTimeFormat(locale, { month: "long" }).format;
    return [...Array(12).keys()].map((m) => {
      const name = format(new Date(Date.UTC(2021, m)));
      return name.charAt(0).toUpperCase() + name.slice(1);
    });
  }

  function weekdays() {
    const format = new Intl.DateTimeFormat(locale, { weekday: "short" }).format;
    return [...Array(7).keys()].map((m) => {
      const name = format(new Date(Date.UTC(2021, 1, m)));
      return name.charAt(0).toUpperCase() + name.slice(1);
    });
  }

  return (
    <ReactstrapDatePicker
      dateFormat="DD.MM.YYYY."
      weekStartsOn={1}
      monthLabels={months()}
      dayLabels={weekdays()}
      onClear={() => {}}
      showClearButton={false}
      {...props}
    />
  );
}
