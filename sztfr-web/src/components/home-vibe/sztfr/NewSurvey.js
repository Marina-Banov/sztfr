import React from 'react';
import {CardBody, FormGroup, Label, Input, Button, Row, Col} from 'reactstrap';
import {useTranslation} from "react-i18next";

export default function NewSurvey () {
    const { t } = useTranslation();
    return (
        <CardBody>
            <FormGroup>
                <Label for="title_hr">{t('title')}</Label>
                <Row>
                    <Col md={6} className="py-1">
                        <Input id="title_hr" type="text" name="title_hr"
                               placeholder={t('croatian')}/>
                    </Col>
                    <Col md={6} className="py-1">
                        <Input id="title_en" type="text" name="title_en"
                               placeholder={t('english')}/>
                    </Col>
                </Row>
            </FormGroup>
            <FormGroup className="mb-3">
                <Label for="description_hr">{t("description")}</Label>
                <Row>
                    <Col md={6} className="py-1">
                        <Input type="textarea" name="description_hr" id="description_hr" placeholder={t('croatian')}/>
                    </Col>
                    <Col md={6} className="py-1">
                        <Input type="textarea" name="description_en" id="description_en" placeholder={t('english')}/>
                    </Col>
                </Row>
            </FormGroup>
            <FormGroup>
                <Label for="google_form_url">{t('surveys.google_form_url')}</Label>
                <Input id="google_form_url" type="text" name="google_form_url" />
            </FormGroup>
            <Button block color="primary">
                {t("surveys.add_new_survey")}
            </Button>
        </CardBody>
    )
}
