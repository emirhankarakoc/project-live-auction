import React from "react";
import { Col, Container, Row } from "react-bootstrap";
import { Link } from "react-router-dom";

export default function UpperNavbar() {
  return (
    <div>
      <Container className="bg-dark">
        <div className="d-flex justify-content-end">
          <div className="d-flex gap-3 flex-wrap">
            <Link to="/" className="text-light text-decoration-none">
              Ana Sayfa
            </Link>
            {/* <Link to="/about" className="text-light text-decoration-none">
              Hakkında
            </Link> */}
            {/* anamizi siktin ege. */}
            <Link to="/rules" className="text-light text-decoration-none">
              Kurallar
            </Link>
            <Link to="/archive" className="text-light text-decoration-none">
              Arşiv
            </Link>
            {/* <Link to="/contact" className="text-light text-decoration-none">
              İletişim
            </Link> */}
          </div>
        </div>
      </Container>
    </div>
  );
}
