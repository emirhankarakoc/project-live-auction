import React from "react";
import { Col, Container, Row } from "react-bootstrap";
import AuctionSearcher from "./AuctionSearcher";
import UpperNavbar from "./UpperNavbar";
import LoginOrRegister from "./login/LoginOrRegister";
import Logo from "./Logo";
import User from "./User";

export default function Navbar(props) {
  const userToken = localStorage.getItem("userToken");
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
                {!userToken ? <LoginOrRegister /> : <User />}
              </div>{" "}
            </Col>
          </Row>
        </div>
      </Container>
    </div>
  );
}
