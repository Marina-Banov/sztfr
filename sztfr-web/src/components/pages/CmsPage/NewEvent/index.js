import React, { useState } from "react";
import { usePlacesWidget } from "react-google-autocomplete";
import { CardBody, FormGroup, Label, Input, Row, Col } from "reactstrap";
import { useTranslation } from "react-i18next";

import { EventFormFields as FormFields } from "models";
import { combineDateTime, getISOTime, validDateRange } from "utils/dateUtils";
import { DatePicker, TimePicker } from "components/common";

export default function NewEvent({
  form,
  handleInputChange,
  setFormField,
  errors,
}) {
  const { t } = useTranslation();
  const [isOnline, setIsOnline] = useState(null);
  const { ref } = usePlacesWidget({
    apiKey: process.env.REACT_APP_API_KEY,
    onPlaceSelected: (place) => {
      setFormField(FormFields.locationOnsite, place);
    },
    options: {
      types: ["establishment"],
      componentRestrictions: { country: "hr" },
    },
  });

  function validDateTime() {
    const start = combineDateTime(form.startDate, getISOTime(form.startTime));
    const end = combineDateTime(form.endDate, getISOTime(form.endTime));
    return validDateRange(start, end);
  }

  function toggleOnline(value) {
    if (value === "true") {
      setFormField(FormFields.locationOnsite, "");
      ref.current.value = "";
    } else {
      setFormField(FormFields.locationOnline, "");
    }
    setIsOnline(value);
  }

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
      <Row>
        <Col md={6} className="py-1">
          <FormGroup>
            <Label for="rdp-form-control-startDate">
              {t("events.start_date")}
            </Label>
            <DatePicker
              id="startDate"
              minDate={new Date().toString()}
              invalid={errors.includes(FormFields.startDate)}
              className="mb-2"
              onChange={(v) => setFormField(FormFields.startDate, v)}
              value={form.startDate}
            />
          </FormGroup>
          <FormGroup>
            <TimePicker
              label={t("events.start_time")}
              order={0}
              invalid={errors.includes(FormFields.startTime)}
              onChange={(v) =>
                setFormField(FormFields.startTime, v.target.value)
              }
            />
          </FormGroup>
        </Col>
        <Col md={6} className="py-1">
          <FormGroup>
            <Label for="rdp-form-control-endDate">{t("events.end_date")}</Label>
            <DatePicker
              id="endDate"
              invalid={errors.includes(FormFields.endDate)}
              minDate={form.startDate ? form.startDate : new Date().toString()}
              onChange={(v) => setFormField(FormFields.endDate, v)}
              value={form.endDate}
              label={t("events.end_date")}
              className="mb-2"
            />
          </FormGroup>
          <FormGroup>
            <TimePicker
              label={t("events.end_time")}
              order={1}
              invalid={errors.includes(FormFields.endTime)}
              onChange={(v) => setFormField(FormFields.endTime, v.target.value)}
            />
          </FormGroup>
        </Col>
      </Row>
      <FormGroup>
        <Row className="mb-2">
          <Col md={4} className="flex_center_center">
            <FormGroup check>
              <Label check>
                <Input
                  type="radio"
                  name="radio1"
                  onChange={() => toggleOnline("true")}
                />
                <i>Online</i> {t("events.event")}
              </Label>
            </FormGroup>
          </Col>
          <Col md={8} className="flex_center_center">
            <FormGroup className="mb-0 w-full">
              <Input
                type="text"
                name={FormFields.locationOnline}
                disabled={isOnline === "false" || !isOnline}
                aria-label={t("events.online_address")}
                placeholder={t("events.online_address")}
                onChange={handleInputChange}
                value={form.locationOnline || ""}
              />
            </FormGroup>
          </Col>
        </Row>
        <Row>
          <Col md={4} className="flex_center_center">
            <FormGroup check>
              <Label check>
                <Input
                  type="radio"
                  name="radio1"
                  onChange={() => toggleOnline("false")}
                />
                <i>Onsite</i> {t("events.event")}
              </Label>
            </FormGroup>
          </Col>
          <Col md={8} className="flex_center_center">
            <FormGroup className="mb-0 w-full">
              <Input
                type="text"
                name={FormFields.locationOnsite}
                disabled={isOnline === "true" || !isOnline}
                aria-label={t("events.onsite_address")}
                placeholder={t("events.onsite_address")}
                onChange={handleInputChange}
                innerRef={ref}
              />
            </FormGroup>
          </Col>
        </Row>
      </FormGroup>
      <FormGroup className="mb-3">
        <Label for={FormFields.description}>{t("description")}</Label>
        <Input
          id={FormFields.description}
          type="textarea"
          name={FormFields.description}
          onChange={handleInputChange}
          value={form.description}
          invalid={errors.includes(FormFields.description)}
        />
      </FormGroup>
      <FormGroup className="mb-3">
        <Label for={FormFields.organisation}>{t("events.organisation")}</Label>
        <Input
          id={FormFields.organisation}
          type="text"
          name={FormFields.organisation}
          onChange={handleInputChange}
          value={form.organisation}
          invalid={errors.includes(FormFields.organisation)}
        />
      </FormGroup>
    </CardBody>
  );
}
