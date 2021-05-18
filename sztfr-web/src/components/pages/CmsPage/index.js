import React, { useCallback, useEffect, useState } from "react";
import { CircularProgress } from "@material-ui/core";
import {
  Row,
  Col,
  Card,
  CardHeader,
  CardBody,
  FormGroup,
  Input,
  Button,
  Label,
  UncontrolledAlert,
} from "reactstrap";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Camera } from "react-feather";

import { SZTFR } from "appConstants";
import { useFirebase } from "appFirebase";
import {
  Event,
  EventForm,
  EventFormFields,
  EventFormValidation,
  SurveyForm,
  SurveyFormFields,
  SurveyFormValidation,
} from "models";
import useForm from "utils/useForm";
import NewSurvey from "./NewSurvey";
import NewEvent from "./NewEvent";

import "./index.scss";

export default function CmsPage(props) {
  const { t } = useTranslation();
  const firebase = useFirebase();
  const [FormFields] = useState(
    props.location.pathname === "/events/new"
      ? EventFormFields
      : SurveyFormFields
  );
  const [tags, setTags] = useState([]);
  const [tagInput, setTagInput] = useState("");
  const [loading, setLoading] = useState(false);
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
    let x;
    switch (props.location.pathname) {
      case "/events/new":
        x = props.location.state
          ? props.location.state.initialValue
          : new EventForm();
        break;
      case "/surveys/new":
        x = props.location.state
          ? props.location.state.initialValue
          : new SurveyForm();
        break;
      default:
        x = {};
    }
    return { image: null, tags: [], ...x };
  }

  const getTags = useCallback(() => {
    /*firebase
      .firestoreRead(SZTFR.FIRESTORE_TAGS_PATH)
      .then((res) => {
        setLoading(false);
        setTags(res.body.tags);
      })
      .catch((err) => {
        setLoading(false);
        console.error(err);
      });*/
    setLoading(false);
    console.log(firebase.loggedIn());
    setTags(["jedan", "dva", "tri"]);
  }, [firebase]);

  useEffect(() => {
    setLoading(true);
    getTags();
  }, [getTags]);

  function handleTagClick(tag) {
    const t = [...data.tags];
    const index = t.indexOf(tag);
    if (index >= 0) {
      t.splice(index, 1);
    } else {
      t.push(tag);
    }
    setFormField(FormFields.tags, t);
  }

  function addTag() {
    if (tagInput === "") {
      return;
    }
    setLoading(true);
    const t = [...tags];
    t.push(tagInput);
    firebase
      .firestoreUpdate(SZTFR.FIRESTORE_TAGS_PATH, { tags: t })
      .then((res) => {
        setTagInput("");
        getTags();
      })
      .catch((err) => {
        setLoading(false);
        console.error(err);
      });
  }

  function onSubmit() {
    switch (props.location.pathname) {
      case "/events/new":
        console.log(new Event(data));
        // firebase.firestoreCreate(SZTFR.FIRESTORE_EVENTS_PATH, new Event(data));
        break;
      case "/surveys/new":
        console.log("survey", data);
        break;
      default:
    }
  }

  return (
    <Row>
      <Col md={8}>
        <Card>
          <BrowserRouter>
            <Switch>
              <Route path="/surveys/new">
                <NewSurvey
                  form={data}
                  handleInputChange={handleInputChange}
                  errors={errors.fields}
                />
              </Route>
              <Route path="/events/new">
                <NewEvent
                  form={data}
                  handleInputChange={handleInputChange}
                  setFormField={setFormField}
                  errors={errors.fields}
                />
              </Route>
            </Switch>
          </BrowserRouter>
        </Card>
        <Card>
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
            {data.image && <img src={URL.createObjectURL(data.image)} alt="" />}
          </CardBody>
        </Card>
      </Col>
      <Col md={4}>
        <Card>
          <CardHeader>{t("tags.tags")}</CardHeader>
          <CardBody>
            {loading ? (
              <div className="flex_center_center">
                <CircularProgress />
              </div>
            ) : (
              Object.keys(tags).map((tag) => (
                <Button
                  color={data.tags.includes(tag) ? "primary" : "secondary"}
                  className="m-1"
                  key={tags[tag]}
                  onClick={() => handleTagClick(tag)}
                >
                  {tags[tag]}
                </Button>
              ))
            )}
            <FormGroup className="mt-3">
              <Label for="tag">{t("tags.add_new_tag")}</Label>
              <div className="flex_center_center tag-form-group">
                <Input
                  id="tag"
                  type="text"
                  name="tag"
                  className="mr-2"
                  value={tagInput}
                  onChange={() => {}}
                />
                <Button color="success" onClick={() => addTag()}>
                  <i className="fa fa-plus" />
                </Button>
              </div>
            </FormGroup>
          </CardBody>
        </Card>
        <BrowserRouter>
          <Switch>
            <Route path="/surveys/new">
              <Button block color="primary" onClick={handleSubmit}>
                {t("surveys.add_new_survey")}
              </Button>
            </Route>
            <Route path="/events/new">
              <Button block color="primary" onClick={handleSubmit}>
                {t("events.add_new_event")}
              </Button>
            </Route>
          </Switch>
        </BrowserRouter>
        {errors.messages.map((error, index) => (
          <UncontrolledAlert
            color="danger"
            className="mt-3"
            key={`error-${index}`}
          >
            {error}
          </UncontrolledAlert>
        ))}
      </Col>
    </Row>
  );
}
