import React, { useEffect, useState } from "react";
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
} from "reactstrap";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Camera } from "react-feather";

import { SZTFR } from "appConstants";
import { useFirebase } from "appFirebase";
import EventForm from "models/EventForm";
import SurveyForm from "models/SurveyForm";
import useForm from "utils/useForm";
import NewSurvey from "./NewSurvey";
import NewEvent from "./NewEvent";

import "./Cms.scss";

export default function CmsPage(props) {
  const { t } = useTranslation();
  const firebase = useFirebase();
  const [tags, setTags] = useState([]);
  const { form, handleInputChange, setFormField } = useForm(initialFormValue());

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

  useEffect(() => {
    firebase.observer.subscribe(SZTFR.FIREBASE_RESPONSE, (data) => {
      if (data) {
        setTags(data);
        document.getElementById("tag").value = "";
      }
    });

    firebase.getFromDatabase("tags");

    return () => {
      firebase.observer.unsubscribe(SZTFR.FIREBASE_RESPONSE);
    };
  }, [firebase]);

  function handleTagClick(tag) {
    const t = [...form.tags];
    const index = t.indexOf(tag);
    if (index >= 0) {
      t.splice(index, 1);
    } else {
      t.push(tag);
    }
    setFormField("tags", t);
  }

  function addTag() {
    const tagInput = document.getElementById("tag");
    const t = [...tags];
    t.push(tagInput.value);
    firebase.writeToDatabase("tags", t);
  }

  return (
    <Row>
      <Col md={8}>
        <Card>
          <BrowserRouter>
            <Switch>
              <Route path="/surveys/new">
                <NewSurvey form={form} handleInputChange={handleInputChange} />
              </Route>
              <Route path="/events/new">
                <NewEvent
                  form={form}
                  handleInputChange={handleInputChange}
                  setFormField={setFormField}
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
              name="image"
              type="file"
              accept="image/*"
              onChange={(e) => setFormField("image", e.target.files[0])}
            />
            {form.image && <img src={URL.createObjectURL(form.image)} alt="" />}
          </CardBody>
        </Card>
      </Col>
      <Col md={4}>
        <Card>
          <CardHeader>{t("tags.tags")}</CardHeader>
          <CardBody>
            {Object.keys(tags).map((tag) => (
              <Button
                color={form.tags.includes(tag) ? "primary" : "secondary"}
                className="m-1"
                key={tags[tag]}
                onClick={() => handleTagClick(tag)}
              >
                {tags[tag]}
              </Button>
            ))}
            <FormGroup className="mt-3">
              <Label for="tag">{t("tags.add_new_tag")}</Label>
              <div className="flex_center_center tag-form-group">
                <Input id="tag" type="text" name="tag" className="mr-2" />
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
              <Button block color="primary">
                {t("surveys.add_new_survey")}
              </Button>
            </Route>
            <Route path="/events/new">
              <Button block color="primary">
                {t("events.add_new_event")}
              </Button>
            </Route>
          </Switch>
        </BrowserRouter>
      </Col>
    </Row>
  );
}
