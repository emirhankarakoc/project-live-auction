import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import Navbar from "../general/Navbar";
import { Button, Col, Container, Row, Spinner } from "react-bootstrap";
import { http, httpError } from "../../lib/http";

export default function Profile() {
  const { username } = useParams();
  const [responseMessage, setResponseMessage] = useState("");
  const [user, setUser] = useState();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await http.get(`/users/username/${username}`);
        setUser(response.data);
      } catch (error) {
        setResponseMessage(httpError(error));
      }
    };

    fetchUser();
  }, []);

  if (!user) {
    return (
      <div className="text-light">
        <Navbar />
        <p> Kullanıcı profili yükleniyor. Lütfen bekleyiniz</p>
        <br />
        <Spinner
          style={{ width: "5rem", height: "5rem", borderWidth: "1rem" }}
        />
      </div>
    );
  }

  return (
    <div>
      <Navbar />
      {responseMessage && <div className="text-light">{responseMessage}</div>}

      {!responseMessage && (
        <Container className="text-light border border-danger">
          <Row>
            <Col>
              <h3>
                {user.firstname} {user.lastname} adlı kullanıcının profili
                {user.mail}
              </h3>
            </Col>
          </Row>
          <br />
          <div className="text-left">
            <Row>
              <Col>
                <h4>Mail adresi: {user.mail}</h4>
              </Col>
              <Col>
                <h4>Adı: {user.firstname}</h4>
              </Col>
            </Row>
            <br />
            <Row>
              <Col>
                <h4>Telefon numarası: {user.phoneNumber}</h4>
              </Col>
              <Col>
                <h4>Soyadı: {user.lastname}</h4>
              </Col>
            </Row>
          </div>
          <br />
          <div>
            <h2>Kazandığı açık arttırmalar:</h2>
            <p>/</p>
            <p>/</p>
            <p>/</p>
            <p>/</p>
            <p>/</p>
          </div>
          <div className="d-flex my-3 justify-content-center">
            <Button className=" bg-danger my-2 ">
              <Link
                to={"/admin/products"}
                className="text-light text-decoration-none"
              >
                Ürünler sayfasına git
              </Link>
            </Button>
            <Button className=" bg-danger my-2 ">
              <Link
                to={"/admin/auctions"}
                className="text-light text-decoration-none"
              >
                Müzayedeler sayfasına git
              </Link>
            </Button>
          </div>
        </Container>
      )}
    </div>
  );
}
