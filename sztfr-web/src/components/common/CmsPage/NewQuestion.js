import React from "react";
import { useTranslation } from "react-i18next";
import { Button, Col, FormGroup, Input, Label, Row } from "reactstrap";
import { Divider } from "@material-ui/core";

import constants from "appConstants";

// TODO validation is hard
export default function NewQuestion({
  question,
  order,
  deleteQuestion,
  updateQuestion,
}) {
  const { t } = useTranslation();

  function setType(event) {
    let newQ;
    if (event.target.value === constants.SURVEY_QUESTION_TYPE_TEXT) {
      newQ = {
        question: question.question,
        type: event.target.value,
      };
    } else if (question.type === constants.SURVEY_QUESTION_TYPE_TEXT) {
      newQ = { ...question, type: event.target.value, choices: ["", ""] };
    } else {
      newQ = { ...question, type: event.target.value };
    }
    updateQuestion(order, newQ);
  }

  function addChoice() {
    const choices = [...question.choices];
    choices.push("");
    let newQ = { ...question, choices };
    updateQuestion(order, newQ);
  }

  function removeChoice(index) {
    const choices = [...question.choices];
    choices.splice(index, 1);
    let newQ = { ...question, choices };
    updateQuestion(order, newQ);
  }

  function handleQuestionChange(event) {
    let newQ = { ...question, question: event.target.value };
    updateQuestion(order, newQ);
  }

  function handleChoiceChange(event, index) {
    const choices = [...question.choices];
    choices[index] = event.target.value;
    let newQ = { ...question, choices };
    updateQuestion(order, newQ);
  }

  return (
    <>
      <Divider className="mb-2" />

      <div className="flex_space_between mb-2">
        <p className="m-0 h5">
          {t("surveys.question_number", { number: order + 1 })}
        </p>
        <Button color="danger" onClick={() => deleteQuestion(order)}>
          <i className="fa fa-trash" /> &nbsp; {t("surveys.delete_question")}
        </Button>
      </div>

      <Row>
        <Col md={8} className="py-1">
          <FormGroup>
            <Label for={"title-" + order.toString()}>
              {t("surveys.question_title")}
            </Label>
            <Input
              id={"title-" + order.toString()}
              type="text"
              value={question.question}
              onChange={handleQuestionChange}
            />
          </FormGroup>
        </Col>

        <Col md={4} className="py-1">
          <FormGroup>
            <Label for={"select-" + order.toString()}>
              {t("surveys.question_type")}
            </Label>
            <Input
              id={"select-" + order.toString()}
              type="select"
              value={question.type}
              onChange={setType}
            >
              <option value={constants.SURVEY_QUESTION_TYPE_TEXT}>
                {t("surveys.input_text")}
              </option>
              <option value={constants.SURVEY_QUESTION_TYPE_MULTIPLE}>
                {t("surveys.multiple_choice")}
              </option>
              <option value={constants.SURVEY_QUESTION_TYPE_SINGLE}>
                {t("surveys.single_choice")}
              </option>
            </Input>
          </FormGroup>
        </Col>
      </Row>

      {question.choices?.map((choice, index) => (
        <FormGroup
          key={index}
          className={
            (index > 1 ? "flex_center_center input-with-button " : "") +
            (question.type === constants.SURVEY_QUESTION_TYPE_MULTIPLE
              ? "multiple"
              : "single")
          }
        >
          <Input
            placeholder={t("surveys.choice_number", { number: index + 1 })}
            value={choice}
            className={index > 1 ? "mr-2 " : ""}
            onChange={(event) => handleChoiceChange(event, index)}
          />
          {index > 1 && (
            <Button color="danger" onClick={() => removeChoice(index)}>
              <i className="fa fa-trash" />
            </Button>
          )}
        </FormGroup>
      ))}

      {question.choices && (
        <Button
          className="mb-3"
          color="success"
          onClick={addChoice}
          disabled={question.choices.length === constants.SURVEY_MAX_CHOICES}
        >
          <i className="fa fa-plus" />
          &nbsp; {t("surveys.new_choice")}
        </Button>
      )}
    </>
  );
}
