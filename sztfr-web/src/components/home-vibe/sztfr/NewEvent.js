import React from 'react';
import {CardBody, FormGroup, Label, Input, Row, Col} from 'reactstrap';
import {useTranslation} from "react-i18next";
import DatePicker from "../elements/DatePicker";
import TimePicker from "../elements/TimePicker";
import useForm from "../../../utils/useForm";

export default function NewEvent () {
    const { t } = useTranslation();
    const { form, handleInputChange } = useForm(
        { title: '', startDate: '', endDate: '', description: '' }
    );

    function combine(date, time) {
        console.log("date", date)
        console.log("time", time)
        date = date ? new Date(date) : new Date();
        time = time ? new Date(time) : new Date();
        const res = new Date(date.getFullYear(), date.getMonth(), date.getDate(),
            time.getHours(), time.getMinutes(), 0);
        console.log("res", res.toISOString())
        return res.toISOString();
    }

    function validDateRange(start, end) {
        if (!start || !end) {
            return true;
        }
        return (new Date(end)).getTime() > (new Date(start)).getTime();
    }

    function updateDateFields(name, date, time) {
        const input = document.getElementById(`rdp-form-control-${name}`);
        console.log(form)
        input.name = name;
        input.value = combine(date, time);
        input.onchange = handleInputChange;
        input.dispatchEvent(new Event('change'));
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
                                    onChange={v => updateDateFields("startDate", v, form.startDate)}
                                    value={form.startDate} />
                    </FormGroup>
                    <FormGroup>
                        <TimePicker label={t("events.start_time")}
                                    order={0}
                                    invalid={false}
                                    onChange={v => updateDateFields('startDate', form.startDate, v.date.toDate())} />
                    </FormGroup>
                </Col>
                <Col md={6} className="py-1">
                    <FormGroup>
                        <Label for="rdp-form-control-endDate">
                            {t("events.end_date")}
                        </Label>
                        <DatePicker id="endDate"
                                    invalid={!validDateRange(form.startDate, form.endDate)}
                                    minDate={form.startDate ? form.startDate : (new Date()).toString()}
                                    onChange={v => updateDateFields("endDate", v, form.endDate)}
                                    value={form.endDate}
                                    label={t("events.end_date")}
                                    className="mb-2" />
                    </FormGroup>
                    <FormGroup>
                        <TimePicker label={t("events.end_time")}
                                    order={1}
                                    invalid={!validDateRange(form.startDate, form.endDate)}
                                    onChange={v => updateDateFields('endDate', form.endDate, v.date.toDate())} />
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
