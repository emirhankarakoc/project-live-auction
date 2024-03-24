import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Navbar from "./Navbar";
import { Button, Col, Container, Form, Row } from "react-bootstrap";
import { http, httpError } from "../../lib/http";

export default function CreateAuction() {
  const { productId } = useParams();
  const [product, setProduct] = useState();
  const [message, setMessage] = useState("");

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const data = await http.get(`/products/${productId}`);
        setProduct(data.data);
        console.log(product);
      } catch (error) {
        console.log(httpError(error));
      }
    };

    fetchProduct();
  }, [productId]);

  async function handleSubmit(event) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    data.append("adminToken", localStorage.getItem("userToken"));
    data.append("productId", productId);
    try {
      const response = await http.post(`/auctions`, data);

      if (response) {
        setTimeout(() => {
          setMessage("Ürün eklenmiştir.");
        }, 3000);
      }
      if (!response) {
        setMessage("Ürün eklenirken bir problem oluştu.");
      }
    } catch (error) {
      console.log(httpError(error));
    }
  }
  return (
    <div>
      <Navbar />
      {product && (
        <div className="text-light">
          <h3>Açık arttırma oluşturma sayfası</h3>
          <Container>
            <Row>
              <Col className="border border-success p-3 ">
                <Row>
                  <img src={product.photoPath} />
                </Row>
                <div className="d-flex justify-content-center">
                  <Row>
                    <h2>Ürün İsmi: {product.productTitle}</h2>
                  </Row>
                </div>
                <div className="d-flex justify-content-center">
                  <Row>
                    <h2>Ürün sahibi: {product.owner}</h2>
                  </Row>
                </div>
                <div className="d-flex justify-content-center">
                  <Row>
                    <h2>Eklenme Tarihi: {product.createddatetime}</h2>
                  </Row>
                </div>
              </Col>
              <Col className="border border-danger">
                <Row className="border border-danger p-2">
                  Müzayedeyi oluşturmak için ekstra detayları girin (otomatik
                  başlamaz)
                </Row>
                <Row>
                  <div className="d-flex justify-content-center my-2 p-2">
                    <Form
                      onSubmit={handleSubmit}
                      className="row g-3 "
                      style={{ width: "400px" }}
                    >
                      <input
                        type="text"
                        name="productTitle"
                        className="form-control"
                        placeholder="Ürüne isim verin"
                      />
                      <input
                        type="datetime-local"
                        name="endDate"
                        className="form-control"
                        placeholder="Müzayede ne zaman bitmeli?"
                      />
                      <input
                        type="number"
                        name="startPrice"
                        className="form-control"
                        placeholder="Başlangıç fiyatı"
                      />
                      <textarea
                        name="description"
                        className="form-control"
                        placeholder="Açıklama girin"
                        style={{ height: "200px" }}
                      />

                      <Button variant="danger" type="submit" className="mb-2">
                        Ekle
                      </Button>
                      <div className="text-light my-3">{message}</div>
                    </Form>
                  </div>
                </Row>
              </Col>
            </Row>
          </Container>
        </div>
      )}
    </div>
  );
}
