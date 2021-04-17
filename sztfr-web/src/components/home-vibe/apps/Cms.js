import React, {useEffect, useState} from 'react';
import {Row, Col, Card, CardHeader, CardBody, FormGroup, Input, Button, Label} from 'reactstrap';
import NewSurvey from "../sztfr/NewSurvey";
import NewEvent from "../sztfr/NewEvent";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {SZTFR} from "../../../constants";
import {useFirebase} from "../../../firebase";
import {useTranslation} from "react-i18next";
import {Camera} from "react-feather";

export default function CmsPage () {
    const [tags, setTags] = useState([]);
    const firebase = useFirebase();
    const [selectedTags, setSelectedTags] = useState([]);
    const { t } = useTranslation();

    useEffect(() => {
        firebase.observer.subscribe(SZTFR.FIREBASE_RESPONSE, (data) => {
           setTags(data);
        });

        firebase.getFromDatabase('tags');

        return () => { firebase.observer.unsubscribe(SZTFR.FIREBASE_RESPONSE); }
    }, [firebase]);

    function handleTagClick(tag) {
        const s = [...selectedTags]
        const index = s.indexOf(tag);
        if (index >= 0) {
            s.splice(index, 1);
        } else {
            s.push(tag);
        }
        setSelectedTags(s);
    }

    return (
        <div>
            <Row>
                <Col md={8}>
                    <Card>
                        <BrowserRouter>
                            <Switch>
                                <Route path='/surveys/new' component={NewSurvey} />
                                <Route path='/events/new' component={NewEvent} />
                            </Switch>
                        </BrowserRouter>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card>
                        <CardHeader>{t('tags.tags')}</CardHeader>
                        <CardBody>
                            {Object.keys(tags).map(tag =>
                                <Button color={(selectedTags.includes(tag) ? 'primary' : 'secondary')}
                                        className="m-1"
                                        key={tags[tag]}
                                        onClick={() => handleTagClick(tag)}>
                                    {tags[tag]}
                                </Button>
                            )}
                            <FormGroup className="mt-3">
                                <Label for="tag_hr">{t('tags.add_new_tag')}</Label>
                                <div className="flex_center_center">
                                    <Input id="tag_hr" type="text" name="tag_hr"
                                       className="mr-2"
                                       placeholder={t('croatian')}/>
                                    <Input id="tag_en" type="text" name="tag_en"
                                       className="mr-2"
                                       placeholder={t('english')}/>
                                    <Button color="success">
                                        <i className="fa fa-plus"/>
                                    </Button>
                                </div>
                            </FormGroup>
                        </CardBody>
                    </Card>
                    <Card>
                        <CardHeader>{t("images.image")}</CardHeader>
                        <CardBody>
                            <Button>
                                <Camera size={18} className="mb-1 mr-2"/>
                                {t("images.add_image")}
                            </Button>
                        </CardBody>
                    </Card>
                </Col>
            </Row>
        </div>
    )
}
