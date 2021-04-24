import React from 'react';
import {CardBody, FormGroup, Label, Input} from 'reactstrap';
import {useTranslation} from "react-i18next";

export default function NewSurvey () {
    const { t } = useTranslation();
    return (
        <CardBody>
            <FormGroup>
                <Label for="title">{t('title')}</Label>
                <Input id="title" type="text" name="title" />
            </FormGroup>
            <FormGroup className="mb-3">
                <Label for="description">{t("description")}</Label>
                <Input type="textarea" name="description" id="description" />
            </FormGroup>
            <FormGroup>
                <Label for="google_form_url">{t('surveys.google_form_url')}</Label>
                <Input id="google_form_url" type="text" name="google_form_url" />
            </FormGroup>
        </CardBody>
    )
}
