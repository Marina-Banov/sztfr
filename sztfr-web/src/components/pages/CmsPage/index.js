import { CircularProgress } from "@material-ui/core";
import React, { useState } from "react";
import {
  Row,
  Col,
  Card,
  CardHeader,
  CardBody,
  Button,
  Alert,
} from "reactstrap";
import { BrowserRouter, Route, Switch, useHistory } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Camera } from "react-feather";

import { SZTFR } from "appConstants";
import { useFirebase } from "appFirebase";
import {
  Event,
  EventForm,
  EventFormFields,
  EventFormValidation,
  Survey,
  SurveyForm,
  SurveyFormFields,
  SurveyFormValidation,
} from "models";
import useForm from "utils/useForm";
import CmsSurveys from "./CmsSurveys";
import CmsEvents from "./CmsEvents";
import TagsCard from "./TagsCard";

import "./index.scss";

export default function CmsPage(props) {
  const { t } = useTranslation();
  const history = useHistory();
  const firebase = useFirebase();
  const [loading, setLoading] = useState(false);
  const [FormFields] = useState(
    props.location.pathname === "/events/new"
      ? EventFormFields
      : SurveyFormFields
  );
  const {
    data,
    handleInputChange,
    setFormField,
    handleSubmit,
    errors,
  } = useForm(
    initialFormValue(),
    props.location.pathname === "/events/new"
      ? EventFormValidation
      : SurveyFormValidation,
    onSubmit
  );

  function initialFormValue() {
    switch (props.location.pathname) {
      case "/events/new":
        return props.location.state
          ? props.location.state.initialValue
          : new EventForm();
      case "/surveys/new":
        return props.location.state
          ? props.location.state.initialValue
          : new SurveyForm();
      default:
        return {};
    }
  }

  function onSubmit() {
    setLoading(true);
    let body;
    let firestorePath;
    let successPath;

    switch (props.location.pathname) {
      case "/events/new":
        body = new Event(data);
        firestorePath = SZTFR.FIRESTORE_EVENTS_PATH;
        successPath = "/events";
        break;
      case "/surveys/new":
        body = new Survey(data);
        firestorePath = SZTFR.FIRESTORE_SURVEYS_PATH;
        successPath = "/surveys";
        break;
      default:
    }

    firebase
      .firestoreCreate(firestorePath, body)
      .then((res) => {
        firebase
          .uploadFile(body.image, data.image)
          .then((res) => {
            setLoading(false);
            history.push(successPath);
          })
          .catch((err) => {
            setLoading(false);
            console.error(err);
          });
      })
      .catch((err) => {
        setLoading(false);
        console.error(err);
      });
  }

  return (
    <Row>
      <Col md={8}>
        <Card>
          <BrowserRouter>
            <Switch>
              <Route path="/surveys/new">
                <CmsSurveys
                  form={data}
                  handleInputChange={handleInputChange}
                  setFormField={setFormField}
                  errors={errors.fields}
                />
              </Route>
              <Route path="/events/new">
                <CmsEvents
                  form={data}
                  handleInputChange={handleInputChange}
                  setFormField={setFormField}
                  errors={errors.fields}
                />
              </Route>
            </Switch>
          </BrowserRouter>
        </Card>
        <Card
          className={
            errors.fields.includes(FormFields.image) ? "invalid-card" : ""
          }
        >
          <CardHeader>{t("images.image")}</CardHeader>
          <CardBody>
            <label htmlFor="image" className="btn btn-secondary">
              <Camera size={18} className="mb-1 mr-2" />
              {t("images.add_image")}
            </label>
            <input
              id="image"
              name={FormFields.image}
              type="file"
              accept="image/*"
              onChange={(e) =>
                setFormField(FormFields.image, e.target.files[0])
              }
            />
            <br />
            {data.image && <img src={URL.createObjectURL(data.image)} alt="" />}
          </CardBody>
        </Card>
      </Col>
      <Col md={4}>
        <TagsCard
          form={data}
          errors={errors}
          setFormField={setFormField}
          FormFields={FormFields}
        />
        <BrowserRouter>
          <Switch>
            <Route path="/surveys/new">
              <Button block color="primary" onClick={handleSubmit}>
                {t("surveys.add_new_survey")}
              </Button>
            </Route>
            <Route path="/events/new">
              <Button
                block
                color="primary"
                onClick={handleSubmit}
                disabled={loading}
              >
                {t("events.add_new_event")}
              </Button>
            </Route>
          </Switch>
        </BrowserRouter>
        {errors.messages.map((error, index) => (
          <Alert color="danger" className="mt-3" key={`error-${index}`}>
            {t(error)}
          </Alert>
        ))}
        {loading && (
          <div className="flex_center_center">
            <CircularProgress />
          </div>
        )}
      </Col>
    </Row>
  );
}
