import React from "react";
import { Label } from "reactstrap";
import { TimePicker as BootstrapTimePicker } from "react-tempusdominus-bootstrap";

import "./index.scss";
import "tempusdominus-bootstrap/src/sass/tempusdominus-bootstrap-build.scss";

export default function TimePicker({ onChange, order, label, invalid }) {
  return (
    <>
      <Label for={`react-js-utl-ellViKYMFK-${order * 3 + 1}`}>{label}</Label>
      <BootstrapTimePicker
        format="HH:mm"
        useCurrent={false}
        noIcon={true}
        onChange={onChange}
        className={invalid ? "is-invalid" : ""}
      />
    </>
  );
}
