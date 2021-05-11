import React, { useEffect, useState } from "react";
import { LinearProgress } from "@material-ui/core";
import { useFirebase } from "appFirebase";
import { useTranslation } from "react-i18next";
import { Button, Card, CardBody, Table } from "reactstrap";
import { Link } from "react-router-dom";

import { SZTFR } from "appConstants";
import EventForm from "models/EventForm";

export default function Events() {
  const { t } = useTranslation();
  const firebase = useFirebase();
  const [loading, setLoading] = useState();
  const [events, setEvents] = useState([]);

  useEffect(() => {
    setLoading(true);
    firebase
      .firestoreRead(SZTFR.FIRESTORE_EVENTS_PATH)
      .then((res) => {
        setEvents(res.body);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setLoading(false);
      });
  }, [firebase]);

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
            &nbsp; {t("events.new_event")}
          </Button>
        </Link>
        <Table hover>
          <thead>
            <tr>
              <th>{t("title")}</th>
              <th>{t("events.start_time")}</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan={2} className="no-padding">
                  <LinearProgress />
                </td>
              </tr>
            ) : events.length ? (
              events.map((e) => (
                <tr key={e.id}>
                  <td>{e.title}</td>
                  <td>{e.startTime}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={2} align="center">
                  {t("events.no_events")}
                </td>
              </tr>
            )}
          </tbody>
        </Table>
      </CardBody>
    </Card>
  );
}
