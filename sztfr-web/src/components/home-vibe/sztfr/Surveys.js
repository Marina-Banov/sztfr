import { LinearProgress } from "@material-ui/core";
import { useFirebase } from "appFirebase";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Button, Card, CardBody, Table } from "reactstrap";
import { Link } from "react-router-dom";

import SurveyForm from "models/SurveyForm";

export default function Surveys() {
  const { t } = useTranslation();
  const firebase = useFirebase();
  const [loading, setLoading] = useState();
  const [surveys, setSurveys] = useState([]);

  useEffect(() => {
    setLoading(true);
    /*firebase
      .getSurveys()
      .then((res) => {
        setSurveys(res.body);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setLoading(false);
      });*/
  }, [firebase]);

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
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={3} align="center">
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
