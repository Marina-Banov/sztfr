import React from "react";
import { CardBody, FormGroup, Label, Input, Button, Card } from "reactstrap";
import { Divider } from "@material-ui/core";
import { useTranslation } from "react-i18next";

import { SurveyFormFields as FormFields } from "models";
import constants from "appConstants";
import NewQuestion from "./NewQuestion";

export default function CmsSurveys({
  form,
  handleInputChange,
  setFormField,
  errors,
}) {
  const { t } = useTranslation();

  function addQuestion() {
    const t = [...form.questions];
    t.push({
      question: "",
      type: constants.SURVEY_QUESTION_TYPE_TEXT,
    });
    setFormField(FormFields.questions, t);
  }

  function deleteQuestion(index) {
    const t = [...form.questions];
    t.splice(index, 1);
    setFormField(FormFields.questions, t);
  }

  function updateQuestion(index, newQ) {
    const t = [...form.questions];
    t[index] = newQ;
    setFormField(FormFields.questions, t);
  }

  return (
    <Card>
      <CardBody>
        <FormGroup>
          <Label for={FormFields.title}>{t("title")}</Label>
          <Input
            id={FormFields.title}
            type="text"
            name={FormFields.title}
            onChange={handleInputChange}
            value={form.title}
            invalid={errors.includes(FormFields.title)}
          />
        </FormGroup>
        <FormGroup className="mb-3">
          <Label for={FormFields.description}>{t("description")}</Label>
          <Input
            type="textarea"
            name={FormFields.description}
            id={FormFields.description}
            onChange={handleInputChange}
            value={form.description}
            invalid={errors.includes(FormFields.description)}
          />
        </FormGroup>

        {form.questions.map((q, index) => (
          <NewQuestion
            key={index}
            question={q}
            order={index}
            updateQuestion={updateQuestion}
            deleteQuestion={deleteQuestion}
          />
        ))}

        <FormGroup>
          <Divider className="mb-3" />
          <Button
            block
            color="success"
            onClick={addQuestion}
            disabled={form.questions.length === constants.SURVEY_MAX_QUESTIONS}
          >
            <i className="fa fa-plus" />
            &nbsp; {t("surveys.new_question")}
          </Button>
        </FormGroup>
      </CardBody>
    </Card>
  );
}
