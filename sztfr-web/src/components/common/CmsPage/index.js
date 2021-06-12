import React from "react";
import { Row, Col, Button, Alert } from "reactstrap";
import { CircularProgress } from "@material-ui/core";
import { useTranslation } from "react-i18next";

import ImageCard from "./ImageCard";
import TagsCard from "./TagsCard";

import "./index.scss";

export default function CmsPage({
  children,
  form,
  handleSubmit,
  setFormField,
  FormFields,
  errors,
  loading,
  submitButtonText,
}) {
  const { t } = useTranslation();

  return (
    <Row>
      <Col md={8}>
        {children}

        <ImageCard
          form={form}
          errors={errors}
          setFormField={setFormField}
          FormFields={FormFields}
        />
      </Col>
      <Col md={4}>
        <TagsCard
          form={form}
          errors={errors}
          setFormField={setFormField}
          FormFields={FormFields}
        />

        <Button block color="primary" disabled={loading} onClick={handleSubmit}>
          {t(submitButtonText)}
        </Button>

        {errors.messages.map((error, index) => (
          <Alert color="danger" className="mt-3" key={`error-${index}`}>
            {t(error)}
          </Alert>
        ))}
        {loading && (
          <div className="flex_center_center mt-3">
            <CircularProgress />
          </div>
        )}
      </Col>
    </Row>
  );
}
