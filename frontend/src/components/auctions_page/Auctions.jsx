import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import { Link } from "react-router-dom";
import {
  Button,
  Card,
  CardBody,
  CardSubtitle,
  CardText,
  CardTitle,
  Col,
  Container,
  Row,
} from "react-bootstrap";
import axios from "axios";

export default function Auctions() {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await fetch("http://localhost:8080/products");
        if (!response.ok) {
          throw new Error("Failed to fetch products");
        }
        const data = await response.json();
        setProducts(data);
      } catch (error) {
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchProducts(); // Call the function
  }, []);

  return (
    <div>
      <Container>
        <Navbar name="Açık Arttırma Sayfası" />
        <Link to="/" className="text-white">
          Ana sayfaya don
        </Link>
        {isLoading ? (
          <p className="text-light">Ürünler yüklenirken lütfen bekleyin.</p>
        ) : (
          <Row className="mx-4 my-2">
            {products.map((product) => (
              <Col key={product.id}>
                <Card
                  style={{
                    width: "18rem",
                    marginBottom: "20px",
                    backgroundColor: "white",
                  }}
                >
                  <Card.Img
                    variant="top"
                    src={product.photoPath}
                    style={{ height: "150px" }}
                  />
                  <CardBody>
                    <CardTitle tag="h5">{product.productTitle}</CardTitle>
                    <CardSubtitle className="mb-2 text-muted" tag="h6">
                      {product.price} TL
                    </CardSubtitle>

                    <Link
                      to={`/product/${product.id}`}
                      className="text-white text-decoration-none"
                    >
                      {" "}
                      <Button>Incele</Button>
                    </Link>
                  </CardBody>
                </Card>
              </Col>
            ))}
          </Row>
        )}
      </Container>
    </div>
  );
}
