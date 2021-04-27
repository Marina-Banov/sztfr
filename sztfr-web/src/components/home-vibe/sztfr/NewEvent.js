import React from 'react';
import {CardBody, FormGroup, Label, Input, Row, Col} from 'reactstrap';
import {useTranslation} from "react-i18next";
import DatePicker from "../elements/DatePicker";
import TimePicker from "../elements/TimePicker";
import useForm from "../../../utils/useForm";
import {combineDateTime, getISOTime, validDateRange} from "../../../utils/dateUtils";

export default function NewEvent({initForm}) {
    const { t } = useTranslation();
    const { form, handleInputChange } = useForm(initForm);

    function validDateTime() {
        const start = combineDateTime(form.startDate, getISOTime(form.startTime));
        const end = combineDateTime(form.endDate, getISOTime(form.endTime));
        return validDateRange(start, end);
    }

    function updateDateFields(name, value) {
        let input;
        if (name === "startTime" || name === "endTime") {
            input = value;
            input.value = value.value;
        } else {
            input = document.getElementById(`rdp-form-control-${name}`);
            input.value = value;
        }
        input.name = name;
        input.ontimeupdate = handleInputChange;
        input.dispatchEvent(new Event('timeupdate'));
    }

    return (
        <CardBody>
            <FormGroup>
                <Label for="title">{t('title')}</Label>
                <Input id="title" type="text" name="title"
                       onChange={handleInputChange}
                       value={form.title}/>
            </FormGroup>
            <Row>
                <Col md={6} className="py-1">
                    <FormGroup>
                        <Label for="rdp-form-control-startDate">
                            {t("events.start_date")}
                        </Label>
                        <DatePicker id="startDate"
                                    minDate={(new Date()).toString()}
                                    className="mb-2"
                                    onChange={v => updateDateFields("startDate", v)}
                                    value={form.startDate} />
                    </FormGroup>
                    <FormGroup>
                        <TimePicker label={t("events.start_time")}
                                    order={0}
                                    invalid={false}
                                    onChange={v => updateDateFields('startTime', v.target)} />
                    </FormGroup>
                </Col>
                <Col md={6} className="py-1">
                    <FormGroup>
                        <Label for="rdp-form-control-endDate">
                            {t("events.end_date")}
                        </Label>
                        <DatePicker id="endDate"
                                    invalid={!validDateTime()}
                                    minDate={form.startDate ? form.startDate : (new Date()).toString()}
                                    onChange={v => updateDateFields("endDate", v)}
                                    value={form.endDate}
                                    label={t("events.end_date")}
                                    className="mb-2" />
                    </FormGroup>
                    <FormGroup>
                        <TimePicker label={t("events.end_time")}
                                    order={1}
                                    invalid={!validDateTime()}
                                    onChange={v => updateDateFields('endTime', v.target)} />
                    </FormGroup>
                </Col>
            </Row>
            <FormGroup className="mb-3">
                <Label for="description">{t("description")}</Label>
                <Input id="description" type="textarea" name="description"
                       onChange={handleInputChange}
                       value={form.description} />
            </FormGroup>
        </CardBody>
    );
}
