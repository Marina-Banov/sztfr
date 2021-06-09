import React from "react";
import { useTranslation } from "react-i18next";
import { Button, Col, FormGroup, Input, Label, Row } from "reactstrap";
import { Divider } from "@material-ui/core";

import { SZTFR } from "appConstants";

export default function NewQuestion({
  question,
  order,
  deleteQuestion,
  updateQuestion,
}) {
  const { t } = useTranslation();

  function setType(event) {
    let newQ;
    if (event.target.value === SZTFR.SURVEY_QUESTION_TYPE_TEXT) {
      newQ = {
        question: question.question,
        type: event.target.value,
      };
    } else if (question.type === SZTFR.SURVEY_QUESTION_TYPE_TEXT) {
      newQ = { ...question, type: event.target.value, options: ["", ""] };
    } else {
      newQ = { ...question, type: event.target.value };
    }
    updateQuestion(order, newQ);
  }

  function addChoice() {
    const options = [...question.options];
    options.push("");
    let newQ = { ...question, options };
    updateQuestion(order, newQ);
  }

  function removeChoice(index) {
    const options = [...question.options];
    options.splice(index, 1);
    let newQ = { ...question, options };
    updateQuestion(order, newQ);
  }
  /*
  function validateQuestion(questionText) {
    if (questionText.length === 0) {
      return {
        validateStatus: "error",
        errorMsg: "Please enter your question!",
      };
    } else if (questionText.length > SZTFR.POLL_QUESTION_MAX_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Question is too long (Maximum ${SZTFR.POLL_QUESTION_MAX_LENGTH} characters allowed)`,
      };
    } else {
      return {
        validateStatus: "success",
        errorMsg: null,
      };
    }
  }
*/
  function handleQuestionChange(event) {
    let newQ = { ...question, question: event.target.value };
    updateQuestion(order, newQ);
    /*const value = event.target.value;
    setState({
      ...state,
      question: {
        text: value,
        ...validateQuestion(value),
      },
    });
  }

  function validateChoice(choiceText) {
    if (choiceText.length === 0) {
      return {
        validateStatus: "error",
        errorMsg: "Please enter a choice!",
      };
    } else if (choiceText.length > SZTFR.POLL_CHOICE_MAX_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Choice is too long (Maximum ${SZTFR.POLL_CHOICE_MAX_LENGTH} characters allowed)`,
      };
    } else {
      return {
        validateStatus: "success",
        errorMsg: null,
      };
    }*/
  }

  function handleChoiceChange(event, index) {
    const options = [...question.options];
    options[index] = event.target.value;
    let newQ = { ...question, options };
    updateQuestion(order, newQ);
    /*const choices = state.choices.slice();
    const value = event.target.value;

    choices[index] = {
      text: value,
      ...validateChoice(value),
    };

    setState({
      ...state,
      choices: choices,
    });
  }

  function isFormInvalid() {
    if (state.question.validateStatus !== "success") {
      return true;
    }

    for (let i = 0; i < state.choices.length; i++) {
      const choice = state.choices[i];
      if (choice.validateStatus !== "success") {
        return true;
      }
    }*/
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
              <option value={SZTFR.SURVEY_QUESTION_TYPE_TEXT}>
                {t("surveys.input_text")}
              </option>
              <option value={SZTFR.SURVEY_QUESTION_TYPE_MULTIPLE}>
                {t("surveys.multiple_choice")}
              </option>
              <option value={SZTFR.SURVEY_QUESTION_TYPE_SINGLE}>
                {t("surveys.single_choice")}
              </option>
            </Input>
          </FormGroup>
        </Col>
      </Row>

      {question.options?.map((choice, index) => (
        <FormGroup
          key={index}
          className={
            (index > 1 ? "flex_center_center input-with-button " : "") +
            (question.type === SZTFR.SURVEY_QUESTION_TYPE_MULTIPLE
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

      {question.options && (
        <Button
          className="mb-3"
          color="success"
          onClick={addChoice}
          disabled={question.options.length === SZTFR.MAX_CHOICES}
        >
          <i className="fa fa-plus" />
          &nbsp; {t("surveys.new_choice")}
        </Button>
      )}
    </>
  );
}
