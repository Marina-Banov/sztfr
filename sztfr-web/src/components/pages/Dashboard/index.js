import React from "react";
import { useTranslation } from "react-i18next";
import { Row, Col } from "reactstrap";

export default function Dashboard() {
  const { t } = useTranslation();

  return (
    <div>
      <Row>
        <Col md={6}>
          <div className="home-hero" style={{ padding: "50px 0 70px" }}>
            <h1>{t("sztfr")}</h1>
            <p className="text-muted">
              {t("home_p1")}
              <br />
              {t("home_p2")}
            </p>
          </div>
        </Col>
      </Row>
    </div>
  );
}
