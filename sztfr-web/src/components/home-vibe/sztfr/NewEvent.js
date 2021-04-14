import React from 'react';
import { CardBody, FormGroup, Label, Input } from 'reactstrap';

export default function NewEvent () {
    return (
        <CardBody>
            <FormGroup>
                <Label for="exampleText">Title</Label>
                <Input type="text" name="text" id="exampleText" />
            </FormGroup>
            <FormGroup>
                <Label for="exampleText2">Description</Label>
                <Input type="textarea" name="text" id="exampleText2" style={{height: 300}} />
            </FormGroup>
        </CardBody>
    )
}
