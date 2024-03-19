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

    fetchProducts();
  }, []);

  return (
    <div>
      <Container>
        <Navbar name="Açık Arttırma Sayfası" />
        <Link to="/" className="text-white mx-2">
          Ana sayfaya don
        </Link>
        {isLoading ? (
          <p>Loading...</p>
        ) : (
          <Row className="mx-4">
            {products.map((product) => (
              <Col key={product.id}>
                <Card style={{ width: "18rem", marginBottom: "20px" }}>
                  <Card.Img variant="top" src="https://picsum.photos/300/200" />
                  <CardBody>
                    <CardTitle tag="h5">{product.productTitle}</CardTitle>
                    <CardSubtitle className="mb-2 text-muted" tag="h6">
                      {product.price}
                    </CardSubtitle>
                    <Button>
                      <Link
                        to={`/product/${product.id}`}
                        className="text-white text-decoration-none"
                      >
                        Incele
                      </Link>
                    </Button>
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
