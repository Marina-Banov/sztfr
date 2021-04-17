import React, {useEffect, useState} from 'react';
import {
    Row,
    Col,
    Card,
    CardHeader,
    CardBody,
    FormGroup,
    Label,
    Input,
    Button,
} from 'reactstrap';
import NewSurvey from "../sztfr/NewSurvey";
import NewEvent from "../sztfr/NewEvent";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {SZTFR} from "../../../constants";
import {useFirebase} from "../../../firebase";

export default function CmsPage () {
    const [tags, setTags] = useState([]);
    const firebase = useFirebase();

    useEffect(() => {
        firebase.observer.subscribe(SZTFR.FIREBASE_RESPONSE, (data) => {
           setTags(Object.values(data).sort());
        });

        firebase.getFromDatabase('tags');

        return () => { firebase.observer.unsubscribe(SZTFR.FIREBASE_RESPONSE); }
    }, [firebase]);

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
                        <CardHeader>Tags</CardHeader>
                        <CardBody>
                            {tags.map(tag =>
                                <Button className="m-1" key={tag}>
                                    {tag}
                                </Button>
                            )}
                            <FormGroup className="m-1">
                                <Input id="newTag"
                                       type="text"
                                       name="tag"
                                       placeholder="Dodaj novi tag..."/>
                            </FormGroup>
                            <Button>Add</Button>
                        </CardBody>
                    </Card>
                    <Card>
                        <CardHeader>Publish</CardHeader>
                        <CardBody>
                            <div>
                                <strong>Status:</strong> Draft
                                </div>
                            <hr />
                            <div>
                                <strong>Word Count:</strong> 329
                            </div>
                            <hr />
                            <div>
                            <FormGroup>
                                <Label for="exampleSelectMulti">Category</Label>
                                <Input type="select" name="select" id="exampleSelect3">
                                    <option>Entertainment</option>
                                    <option>Books</option>
                                    <option>Video</option>
                                    <option>Food</option>
                                    <option>Cars</option>
                                </Input>
                            </FormGroup>
                            </div>
                            <hr />
                            <Button block color="primary">Publish</Button>
                        </CardBody>
                    </Card>
                </Col>
            </Row>
        </div>
    )
}
