import React from "react";
import { CardBody, FormGroup, Label, Input } from "reactstrap";
import { useTranslation } from "react-i18next";

export default function NewSurvey({ form, handleInputChange }) {
  const { t } = useTranslation();

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
        <Label for="googleFormURL">{t("surveys.google_form_url")}</Label>
        <Input
          id="googleFormURL"
          type="text"
          name="googleFormURL"
          onChange={handleInputChange}
          value={form.googleFormURL}
        />
      </FormGroup>
    </CardBody>
  );
}
