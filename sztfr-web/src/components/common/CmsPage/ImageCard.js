import React from "react";
import { Camera } from "react-feather";
import { useTranslation } from "react-i18next";
import { Card, CardBody, CardHeader } from "reactstrap";

export default function ImageCard({ errors, setFormField, FormFields, form }) {
  const { t } = useTranslation();

  return (
    <Card
      className={errors.fields.includes(FormFields.image) ? "invalid-card" : ""}
    >
      <CardHeader>{t("images.image")}</CardHeader>
      <CardBody>
        <label htmlFor="image" className="btn btn-secondary">
          <Camera size={18} className="mb-1 mr-2" />
          {t("images.add_image")}
        </label>
        <input
          id="image"
          name={FormFields.image}
          type="file"
          accept="image/*"
          onChange={(e) => setFormField(FormFields.image, e.target.files[0])}
        />
        <br />
        {form.image && <img src={URL.createObjectURL(form.image)} alt="" />}
      </CardBody>
    </Card>
  );
}
