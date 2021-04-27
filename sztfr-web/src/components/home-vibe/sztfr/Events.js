import React from "react";
import { Button, Card, CardBody, Table } from "reactstrap";
import { Link } from "react-router-dom";

import EventForm from "models/EventForm";

export default function Events() {
  return (
    <Card>
      <CardBody>
        <Link
          to={{
            pathname: "/events/new",
            state: { initialValue: new EventForm() },
          }}
        >
          <Button className="m-b" color="success">
            <i className="fa fa-plus" />
            &nbsp; Novi događaj
          </Button>
        </Link>
        <Table hover>
          <thead>
            <tr>
              <th>#</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Username</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>1</td>
              <td>Mark</td>
              <td>Otto</td>
              <td>@mdo</td>
            </tr>
            <tr>
              <td>2</td>
              <td>Jacob</td>
              <td>Thornton</td>
              <td>@fat</td>
            </tr>
            <tr>
              <td>3</td>
              <td>Larry</td>
              <td>the Bird</td>
              <td>@twitter</td>
            </tr>
          </tbody>
        </Table>
      </CardBody>
    </Card>
  );
}
