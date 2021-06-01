import React, { useCallback, useEffect, useState } from "react";
import { LinearProgress } from "@material-ui/core";
import { useTranslation } from "react-i18next";
import { Button, Card, CardBody, Table } from "reactstrap";
import { Link } from "react-router-dom";

import { SZTFR } from "appConstants";
import { useFirebase } from "appFirebase";
import { SurveyForm } from "models";

export default function Surveys() {
  const { t } = useTranslation();
  const firebase = useFirebase();
  const [loading, setLoading] = useState();
  const [surveys, setSurveys] = useState([]);

  const getSurveys = useCallback(() => {
    firebase
      .firestoreRead(SZTFR.FIRESTORE_SURVEYS_PATH)
      .then((res) => {
        setSurveys(res.body);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setLoading(false);
      });
  }, [firebase]);

  useEffect(() => {
    setLoading(true);
    getSurveys();
  }, [getSurveys]);

  function deleteSurvey(id) {
    setLoading(true);
    firebase
      .firestoreDelete(SZTFR.FIRESTORE_SURVEYS_PATH, id)
      .then(() => getSurveys())
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
            pathname: "/surveys/new",
            state: { initialValue: new SurveyForm() },
          }}
        >
          <Button className="m-b" color="success">
            <i className="fa fa-plus" />
            &nbsp; {t("surveys.new_survey")}
          </Button>
        </Link>
        <Table hover>
          <thead>
            <tr>
              <th>{t("title")}</th>
              <th>{t("surveys.number_of_answers")}</th>
              <th>{t("surveys.published")}</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan={4} className="no-padding">
                  <LinearProgress />
                </td>
              </tr>
            ) : surveys.length ? (
              surveys.map((s) => (
                <tr key={s.id}>
                  <td>{s.title}</td>
                  <td>{s.answersCount}</td>
                  <td>
                    <i
                      className={`fa ${s.published ? "fa-check" : "fa-times"}`}
                    />
                  </td>
                  <td>
                    <Button className="mr-2 mb-1 py-1">{t("edit")}</Button>
                    <Button
                      color="danger"
                      className="py-1"
                      onClick={() => deleteSurvey(s.id)}
                    >
                      {t("delete")}
                    </Button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={4} align="center">
                  {t("surveys.no_surveys")}
                </td>
              </tr>
            )}
          </tbody>
        </Table>
      </CardBody>
    </Card>
  );
}
