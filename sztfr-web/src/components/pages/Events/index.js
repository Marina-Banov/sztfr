import React, { useCallback, useEffect, useState } from "react";
import { LinearProgress } from "@material-ui/core";
import { useTranslation } from "react-i18next";
import { Button, Card, CardBody, Table } from "reactstrap";
import { Link } from "react-router-dom";

import constants from "appConstants";
import { useFirebase } from "appFirebase";
import { EventForm } from "models";

export default function Events() {
  const { t } = useTranslation();
  const firebase = useFirebase();
  const [loading, setLoading] = useState();
  const [events, setEvents] = useState([]);

  const getEvents = useCallback(() => {
    firebase
      .firestoreRead(constants.FIRESTORE_EVENTS_PATH)
      .then((res) => {
        setEvents(res.body);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setLoading(false);
      });
  }, [firebase]);

  useEffect(() => {
    setLoading(true);
    getEvents();
  }, [getEvents]);

  function displayDate(e) {
    return new Date(e.startTime._seconds * 1000).toLocaleString("hr", {
      dateStyle: "long",
      timeStyle: "short",
    });
  }

  function deleteEvent(id) {
    setLoading(true);
    firebase
      .firestoreDelete(constants.FIRESTORE_EVENTS_PATH, id)
      .then(() => getEvents())
      .catch((err) => {
        console.error(err);
        setLoading(false);
      });
  }

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
              <th />
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan={3} className="no-padding">
                  <LinearProgress />
                </td>
              </tr>
            ) : events.length ? (
              events.map((e) => (
                <tr key={e.id}>
                  <td>{e.title}</td>
                  <td>{displayDate(e)}</td>
                  <td>
                    <Button className="mr-2 mb-1 py-1">{t("edit")}</Button>
                    <Button
                      color="danger"
                      className="py-1"
                      onClick={() => deleteEvent(e.id)}
                    >
                      {t("delete")}
                    </Button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={3} align="center">
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
