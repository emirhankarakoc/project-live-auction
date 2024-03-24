import React, { useState } from "react";
import { Button, Col, Container, Row, Spinner, Modal } from "react-bootstrap";
import { http, httpError } from "../../lib/http";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import Navbar from "./Navbar";

export default function ListProducts(props) {
  const userToken = localStorage.getItem("userToken");

  const [role, setRole] = useState();
  const [products, setProducts] = useState();
  const [showModal, setShowModal] = useState(false);
  const [selectedProductId, setSelectedProductId] = useState(null);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await http.get(`/users/token/${userToken}`);
        setRole(data.data);
        const productsData = await http.get(`/products/page/${0}/${9}`);
        setProducts(productsData.data.content);
      } catch (error) {
        console.log(httpError(error));
      }
    };

    fetchData();
  }, []);

  const handleDelete = (productId) => {
    setSelectedProductId(productId);
    setShowModal(true);
  };

  const confirmDelete = async () => {
    try {
      // Silme işlemi yapılır
      await http.delete(`/products/${selectedProductId}/token/${userToken}`);
      // Yeniden ürünleri getir
      const productsData = await http.get(`/products/page/${0}/${9}`);
      setProducts(productsData.data.content);
      setShowModal(false);
    } catch (error) {
      console.log(httpError(error));
    }
  };
  if (!role) return <Spinner />;

  if (role === "ROLE_USER") {
    window.location.replace("/");
    return null;
  }
  if (!products) {
    return (
      <div>
        <Navbar />
        <div className="text-light">
          Ürünler yüklenirken lütfen bekleyiniz
          <br />
          <Spinner className="mt-3" size="xl" />
        </div>
      </div>
    );
  }

  if (products.length == 0) {
    return (
      <div>
        <Navbar />
        <div className="text-light"> Gösterilecek herhangi bir ürün yok.</div>
        <br />
        <Link to={"/"}>
          <Button variant="warning" className="text-decoration-none">
            Ana sayfaya dön
          </Button>
        </Link>
      </div>
    );
  }
  return (
    <div className="d-flex justify-content-center">
      <Container>
        {products &&
          products.map((product, index) => (
            <Row key={index} className="border border-warning my-2 p-2">
              <Col>
                <div>
                  <Row>
                    <Col>
                      <img
                        className="d-flex float-left"
                        src={product.photoPath}
                        style={{ height: "250px", width: "400px" }}
                        alt={product.productTitle}
                      />
                    </Col>
                    <Col className="d-flex float-left align-items-center">
                      <div>
                        <Row>Ürünün ismi: {product.productTitle}</Row>
                        <Row>Oluşturulma tarihi: {product.createddatetime}</Row>
                        <Row>Ekleyen kişi: {product.owner}</Row>
                      </div>
                    </Col>
                  </Row>
                </div>
              </Col>
              <Col xl={2}>
                <div>
                  <Row>
                    <Button
                      className="bg-danger my-1"
                      onClick={() => handleDelete(product.id)}
                    >
                      Sil
                    </Button>
                  </Row>
                  <Row>
                    <Button className="bg-warning my-1">Güncelle</Button>
                  </Row>
                  <Row>
                    <Link to={`/create/${product.id}`}>
                      <Button className="bg-success my-1">Yayınla</Button>
                    </Link>
                  </Row>
                </div>
              </Col>
            </Row>
          ))}
      </Container>

      {/* Silme Modalı */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Ürünü Sil</Modal.Title>
        </Modal.Header>
        <Modal.Body>Ürünü silmek istediğinize emin misiniz?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            İptal
          </Button>
          <Button variant="danger" onClick={confirmDelete}>
            Sil
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
