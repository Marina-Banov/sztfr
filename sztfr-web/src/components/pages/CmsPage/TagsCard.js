import React, { useCallback, useEffect, useState } from "react";
import { CircularProgress } from "@material-ui/core";
import { useTranslation } from "react-i18next";
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  FormGroup,
  Input,
  Label,
} from "reactstrap";

import { SZTFR } from "appConstants";
import { useFirebase } from "appFirebase";

export default function TagsCard({ errors, setFormField, FormFields, form }) {
  const { t } = useTranslation();
  const firebase = useFirebase();
  const [tags, setTags] = useState([]);
  const [tagInput, setTagInput] = useState("");
  const [loading, setLoading] = useState(false);

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
    const t = [...form.tags];
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

  return (
    <Card
      className={errors.fields.includes(FormFields.tags) ? "invalid-card" : ""}
    >
      <CardHeader>{t("tags.tags")}</CardHeader>
      <CardBody>
        {loading ? (
          <div className="flex_center_center">
            <CircularProgress />
          </div>
        ) : (
          Object.keys(tags).map((tag) => (
            <Button
              color={form.tags.includes(tag) ? "primary" : "secondary"}
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
              onChange={(e) => setTagInput(e.target.value)}
            />
            <Button color="success" onClick={() => addTag()}>
              <i className="fa fa-plus" />
            </Button>
          </div>
        </FormGroup>
      </CardBody>
    </Card>
  );
}
