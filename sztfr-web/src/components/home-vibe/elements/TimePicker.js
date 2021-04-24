import React from "react";
import {Label} from "reactstrap";
import {TimePicker as BootstrapTimePicker} from "react-tempusdominus-bootstrap";

import "./TimePicker.scss";
import "tempusdominus-bootstrap/src/sass/tempusdominus-bootstrap-build.scss";

export default function TimePicker({onChange, order, label, invalid}) {
    const locale = "hr";

    return (
        <>
            <Label for={`react-js-utl-ellViKYMFK-${order * 3 + 1}`}>
                {label}
            </Label>
            <BootstrapTimePicker locale={locale}
                                 useCurrent={false}
                                 noIcon={true}
                                 onChange={onChange}
                                 className={invalid ? 'is-invalid' : ''}/>
        </>
    );
}
