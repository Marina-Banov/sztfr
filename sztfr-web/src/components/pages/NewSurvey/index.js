import React, { useState } from "react";
import { useHistory } from "react-router-dom";

import constants from "appConstants";
import { useFirebase } from "appFirebase";
import { CmsPage, CmsSurveys } from "components/common";
import handlePromise from "utils/handlePromise";
import useForm from "utils/useForm";
import {
  Survey,
  Questions,
  SurveyForm,
  SurveyFormFields,
  SurveyFormValidation,
} from "models";

export default function NewSurvey() {
  const history = useHistory();
  const firebase = useFirebase();
  const [loading, setLoading] = useState(false);
  const {
    data,
    handleInputChange,
    setFormField,
    handleSubmit,
    errors,
  } = useForm(new SurveyForm(), SurveyFormValidation, onSubmit);

  async function onSubmit() {
    setLoading(true);
    let body = new Survey(data);

    const res1 = await handlePromise(
      firebase.firestoreCreate(constants.FIRESTORE_SURVEYS_PATH, body)
    );
    if (res1.error) {
      setLoading(false);
      return;
    }

    await handlePromise(firebase.uploadFile(body.image, data.image));

    const res2 = await handlePromise(
      firebase.firestoreCreateBulk(
        constants.FIRESTORE_QUESTIONS_COLLECTION,
        new Questions(data).questions,
        res1.data
      )
    );
    if (res2.error) {
      setLoading(false);
      return;
    }

    setLoading(false);
    history.push("/surveys");
  }

  return (
    <CmsPage
      form={data}
      handleSubmit={handleSubmit}
      setFormField={setFormField}
      FormFields={SurveyFormFields}
      errors={errors}
      loading={loading}
      submitButtonText={"surveys.add_new_survey"}
    >
      <CmsSurveys
        form={data}
        handleInputChange={handleInputChange}
        setFormField={setFormField}
        errors={errors.fields}
      />
    </CmsPage>
  );
}
