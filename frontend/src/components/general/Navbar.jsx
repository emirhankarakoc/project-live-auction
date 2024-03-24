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
        <div className="d-flex flex-wrap align-items-center justify-content-between flex-wrap gap-3">
          <Logo />
          <AuctionSearcher />
          {!userToken ? <LoginOrRegister /> : <User />}
        </div>
      </Container>
    </div>
  );
}
