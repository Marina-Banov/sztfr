import React from "react";
import { CardBody, FormGroup, Label, Input } from "reactstrap";
import { useTranslation } from "react-i18next";

import { SurveyFormFields as FormFields } from "models";

export default function NewSurvey({ form, handleInputChange, errors }) {
  const { t } = useTranslation();

  return (
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
      <FormGroup>
        <Label for={FormFields.googleFormURL}>
          {t("surveys.google_form_url")}
        </Label>
        <Input
          id={FormFields.googleFormURL}
          type="text"
          name={FormFields.googleFormURL}
          onChange={handleInputChange}
          value={form.googleFormURL}
          invalid={errors.includes(FormFields.googleFormURL)}
        />
      </FormGroup>
    </CardBody>
  );
}
