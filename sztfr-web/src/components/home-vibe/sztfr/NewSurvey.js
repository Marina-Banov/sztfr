import React from "react";
import { CardBody, FormGroup, Label, Input } from "reactstrap";
import { useTranslation } from "react-i18next";

import useForm from "utils/useForm";

export default function NewSurvey({ initForm }) {
  const { t } = useTranslation();
  const { form, handleInputChange } = useForm(initForm);

  return (
    <CardBody>
      <FormGroup>
        <Label for="title">{t("title")}</Label>
        <Input
          id="title"
          type="text"
          name="title"
          onChange={handleInputChange}
          value={form.title}
        />
      </FormGroup>
      <FormGroup className="mb-3">
        <Label for="description">{t("description")}</Label>
        <Input
          type="textarea"
          name="description"
          id="description"
          onChange={handleInputChange}
          value={form.description}
        />
      </FormGroup>
      <FormGroup>
        <Label for="google_form_url">{t("surveys.google_form_url")}</Label>
        <Input
          id="google_form_url"
          type="text"
          name="google_form_url"
          onChange={handleInputChange}
          value={form.googleFormURL}
        />
      </FormGroup>
    </CardBody>
  );
}
