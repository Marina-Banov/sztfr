import React from "react";
import * as Feather from "react-feather";
import { Row, Col, Card } from "reactstrap";

import TabWidget from "./TabWidget";
import ProfileWidget from "./ProfileWidget";
import AnalyticsWidget from "./AnalyticsWidget";
import StorageWidget from "./StorageWiget";
import TrafficWidget from "./TrafficWidget";
import RatingWidget from "./RatingWidget";

export default function Widgets() {
  return (
    <Row>
      <Col md={4}>
        <TrafficWidget />
        <ProfileWidget />
      </Col>
      <Col md={4}>
        <AnalyticsWidget />
        <StorageWidget />
      </Col>
      <Col md={4}>
        <Card body>
          <div className="text-center">
            <h2 className="h5 text-muted text-uppercase">Bounce Rate</h2>
            <span className="h2">
              <Feather.BarChart2 /> 2.7%
            </span>
          </div>
        </Card>
        <TabWidget />
        <Card body>
          <div>
            <span className="h4">
              <Feather.Headphones /> 3 Missed Messages
            </span>
          </div>
        </Card>
        <RatingWidget />
      </Col>
    </Row>
  );
}
