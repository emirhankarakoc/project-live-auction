import React from "react";
import { Col, Container, Row } from "react-bootstrap";
import { Link } from "react-router-dom";

export default function UpperNavbar() {
  return (
    <div>
      <Container className="bg-dark">
        <Row>
          <Col sm={4}></Col>

          <Col>
            <div className="text-sm text-justify text-light d-inline">
              <Link to="/" className="text-light mx-2 text-decoration-none">
                Ana Sayfa
              </Link>
              <Link
                to="/about"
                className="text-light mx-2 text-decoration-none"
              >
                Hakkında
              </Link>
              <Link
                to="/rules"
                className="text-light mx-2 text-decoration-none"
              >
                Kurallar
              </Link>
              <Link
                to="/archive"
                className="text-light mx-2 text-decoration-none"
              >
                Arşiv
              </Link>
              <Link
                to="/contact"
                className="text-light mx-2 text-decoration-none"
              >
                İletişim
              </Link>
              <Link
                to="/admin"
                className="text-light mx-2 text-decoration-none"
              >
                Admin
              </Link>
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
}
