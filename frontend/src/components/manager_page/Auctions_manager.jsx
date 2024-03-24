import React, { useEffect, useState } from "react";
import { Col, Container, Dropdown, Row, Spinner } from "react-bootstrap";
import UrunEkle from "../general/UrunEkle";
import { http, httpError } from "../../lib/http";
import Navbar from "../general/Navbar";
import ListProducts from "../general/ListProducts";
import ListAuctions from "../general/ListAuctions";

export default function Auctions_manager() {
  const userToken = localStorage.getItem("userToken");

  const [role, setRole] = useState();

  useEffect(() => {
    const controlUserIsAdminOrNot = async () => {
      try {
        const data = await http.get(`/users/token/${userToken}`);
        setRole(data.data);
      } catch (error) {
        console.log(httpError(error));
      }
    };
    controlUserIsAdminOrNot();
  }, [userToken]);
  if (!role) return <Spinner />;

  if (role === "ROLE_USER") {
    window.location.replace("/");
    return null;
  }

  return (
    <div>
      <Navbar />
      <Container className="text-light">
        <Row>
          <Row>
            <h3>Müzayede Listesi</h3>
            <p>Müzayedeler başlamamış olabilirler. Teklife açmayı unutmayın.</p>
          </Row>
          <Row>
            <ListAuctions />
          </Row>
        </Row>
      </Container>
    </div>
  );
}
