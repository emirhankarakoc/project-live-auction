import React from "react";
import Navbar from "../general/Navbar";
import { Link } from "react-router-dom";
import { Image, Container, Row, Col, Button } from "react-bootstrap";
import start_page from "../images/start_page.png";

export default function Starter() {
  console.log(localStorage.getItem("userToken"));
  return (
    <div>
      <Navbar name="Muzayede Evim" />
      <Container className="">
        <Row>
          <Col className="col-7">
            <div
              className="my-5 text-light text-center"
              style={{ fontSize: "75px" }}
            >
              Müzayede evine hoşgeldiniz
            </div>
            <Link to="/auctions" className="text-white mx-2 ">
              <Button className="btn btn-warning btn-lg">
                Tüm müzayedeleri görün
              </Button>
            </Link>
          </Col>
          <Col className="col-5">
            <Image
              src={start_page}
              style={{ width: "400px", height: "400px" }}
            />
          </Col>
        </Row>
      </Container>
    </div>
  );
}
