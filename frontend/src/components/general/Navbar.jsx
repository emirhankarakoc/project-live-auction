import React from "react";
import { Col, Container, Row } from "react-bootstrap";
import AuctionSearcher from "./AuctionSearcher";
import UpperNavbar from "./UpperNavbar";
import LoginOrRegister from "./login/LoginOrRegister";
import Login from "./login/Login";
import Logo from "./Logo";

export default function Navbar(props) {
  return (
    <div>
      <UpperNavbar />

      <Container className="bg-dark">
        <div>
          <Row>
            <Col>
              <Logo />
            </Col>
            <Col>
              <div className="mt-3">
                <AuctionSearcher />
              </div>
            </Col>
            <Col>
              <div className="mt-3">
                <LoginOrRegister />
              </div>{" "}
            </Col>
          </Row>
        </div>
      </Container>
    </div>
  );
}
