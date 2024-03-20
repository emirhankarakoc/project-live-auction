import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Navbar from "../general/Navbar";
import { Col, Container, Row } from "react-bootstrap";

export default function Profile() {
  const { username } = useParams();
  console.log(username);
  const [responseMessage, setResponseMessage] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState({});

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/accounts/${username}`
        );

        if (!response.ok) {
          setResponseMessage("Kullanıcı bulunamadı.");
          throw new Error("Kullaıcı bulunamadı.");
        }
        const data = await response.json();
        setUser(data);
      } catch (error) {
        console.log(error);
      }
    };

    fetchUser();
  }, []);

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
        </Container>
      )}
    </div>
  );
}
