import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import { Button, Col, Form, FormGroup, Input, Label, Row } from "reactstrap";
import { Divider } from "@material-ui/core";

import { SZTFR } from "appConstants";

export default function NewQuestion({ questionNumber, deleteQuestion }) {
  const { t } = useTranslation();
  const [state, setState] = useState({
    question: "",
    type: SZTFR.SURVEY_QUESTION_TYPE_TEXT,
    order: questionNumber + 1,
  });

  function setType(event) {
    if (event.target.value === SZTFR.SURVEY_QUESTION_TYPE_TEXT) {
      setState({
        question: state.question,
        type: event.target.value,
        order: state.order,
      });
    } else if (state.type === SZTFR.SURVEY_QUESTION_TYPE_TEXT) {
      setState({ ...state, type: event.target.value, options: ["", ""] });
    } else {
      setState({ ...state, type: event.target.value });
    }
  }

  function addChoice() {
    const options = [...state.options];
    options.push("");
    setState({ ...state, options });
  }

  function removeChoice(index) {
    const options = [...state.options];
    options.splice(index, 1);
    setState({ ...state, options });
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
    setState({ ...state, question: event.target.value });
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
    const options = [...state.options];
    options[index] = event.target.value;
    setState({ ...state, options });
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
    <Form>
      <Divider className="mb-2" />
      <div className="flex_space_between mb-2">
        <p className="m-0 h5">
          {t("surveys.question_number", { number: state.order })}
        </p>
        <Button color="danger" onClick={() => deleteQuestion(questionNumber)}>
          <i className="fa fa-trash" /> &nbsp; {t("surveys.delete_question")}
        </Button>
      </div>
      <Row>
        <Col md={8} className="py-1">
          <FormGroup>
            <Label for={"title-" + questionNumber.toString()}>
              {t("surveys.question_title")}
            </Label>
            <Input
              id={"title-" + questionNumber.toString()}
              type="text"
              value={state.question}
              onChange={handleQuestionChange}
            />
          </FormGroup>
        </Col>
        <Col md={4} className="py-1">
          <FormGroup>
            <Label for={"select-" + questionNumber.toString()}>
              {t("surveys.question_type")}
            </Label>
            <Input
              id={"select-" + questionNumber.toString()}
              type="select"
              value={state.type}
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
      {state.options?.map((choice, index) => (
        <SurveyChoice
          key={index}
          choice={choice}
          choiceNumber={index}
          removeChoice={removeChoice}
          handleChoiceChange={handleChoiceChange}
          type={state.type}
        />
      ))}
      {state.options && (
        <Button
          className="mb-3"
          color="success"
          onClick={addChoice}
          disabled={state.options.length === SZTFR.MAX_CHOICES}
        >
          <i className="fa fa-plus" />
          &nbsp; {t("surveys.new_choice")}
        </Button>
      )}
    </Form>
  );
}

function SurveyChoice({
  choice,
  choiceNumber,
  removeChoice,
  handleChoiceChange,
  type,
}) {
  const { t } = useTranslation();

  return (
    <FormGroup
      className={
        (choiceNumber > 1 ? "flex_center_center input-with-button " : "") +
        (type === SZTFR.SURVEY_QUESTION_TYPE_MULTIPLE ? "multiple" : "single")
      }
    >
      <Input
        placeholder={t("surveys.choice_number", { number: choiceNumber + 1 })}
        value={choice}
        className={choiceNumber > 1 ? "mr-2 " : ""}
        onChange={(event) => handleChoiceChange(event, choiceNumber)}
      />
      {choiceNumber > 1 && (
        <Button color="danger" onClick={() => removeChoice(choiceNumber)}>
          <i className="fa fa-trash" />
        </Button>
      )}
    </FormGroup>
  );
}
