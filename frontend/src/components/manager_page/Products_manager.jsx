import React, { useEffect, useState } from "react";
import { Col, Container, Dropdown, Row, Spinner } from "react-bootstrap";
import UrunEkle from "../general/UrunEkle";
import { http, httpError } from "../../lib/http";
import Navbar from "../general/Navbar";
import ListProducts from "../general/ListProducts";

export default function Products_manager() {
  const userToken = localStorage.getItem("userToken");

  const [role, setRole] = useState();
  const [productCount, setProductCount] = useState();

  useEffect(() => {
    const controlUserIsAdminOrNot = async () => {
      try {
        const data = await http.get(`/users/token/${userToken}`);
        setRole(data.data);
      } catch (error) {
        console.log(httpError(error));
      }
    };

    const fetchProductCount = async () => {
      try {
        const data = await http.get("/products/size");
        setProductCount(data.data);
      } catch (error) {
        console.log(httpError(error));
      }
    };

    fetchProductCount();
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
            <h3>Müzayede için Ürün Ekle</h3>
            <UrunEkle />
          </Row>
        </Row>
        <Row>
          <Row>
            <h3>Ürün Listesi</h3>
            <p>
              Bunlar ürün, müzayede değil. Ürünlerden müzayede
              oluşturabilirsiniz.
            </p>
          </Row>
          <Row>
            <ListProducts productCount={productCount} />
          </Row>
        </Row>
      </Container>
    </div>
  );
}
