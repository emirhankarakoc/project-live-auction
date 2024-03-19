import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import Navbar from "../general/Navbar";
import { Button, Col, Container, Row } from "react-bootstrap";

export default function Auction(props) {
  const { id } = useParams();

  const [product, setProduct] = useState({});

  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await fetch(`http://localhost:8080/products/${id}`);
        if (!response.ok) {
          throw new Error("Failed to fetch products");
        }
        const data = await response.json();
        setProduct(data);
      } catch (error) {
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchProduct();
    console.log(product);
  }, []);

  return (
    <div className="bg-dark">
      <Navbar />

      <Container>
        <Row className="my-3">
          <Col>
            <div className="">
              <h2 className="text-light">{product.productTitle}</h2>
              <img style={{ width: "700px" }} src={product.photoPath}></img>
              <h4 className="text-light my-2">FIYAT: {product.price}</h4>
            </div>
            <Button className="bg-warning">Teklif ver</Button>
          </Col>
          <Col className="text-white">burada teklifler olcak</Col>
        </Row>
      </Container>

      <Button>
        <Link to={"/auctions"} className="text-white text-decoration-none">
          Geri Dön
        </Link>
      </Button>
    </div>
  );
}
