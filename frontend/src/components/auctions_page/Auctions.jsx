import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import { Container, Row, Col, Spinner, Card, Button } from "react-bootstrap";
import { http, httpError } from "../../lib/http";
import { Link } from "react-router-dom";

export default function Auctions() {
  const [auctions, setAuctions] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);

  useEffect(() => {
    const fetchAuctions = async () => {
      try {
        const data = await http.get(
          `/auctions/filter/ready/page/${page}/size/${size}`
        );
        setAuctions(data.data.content);
      } catch (error) {
        console.log(error);
      }
    };
    fetchAuctions();
  }, []);
  if (!auctions) {
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

  if (auctions.length == 0) {
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
    <div>
      <Navbar />
      <div>
        {auctions && (
          <Container>
            <Row>
              {auctions.map((auction) => (
                <Col key={auction.id} xl={4}>
                  <Card style={{ width: "18rem" }} className="my-2">
                    <Card.Img
                      variant="top"
                      src={auction.product.photoPath}
                      style={{ height: "300px" }}
                    />
                    <Card.Body>
                      <Card.Title style={{ fontWeight: "700" }}>
                        {auction.product.productTitle}
                      </Card.Title>

                      <Card.Subtitle>{auction.startPrice} TL</Card.Subtitle>

                      <Card.Text> Bitiş tarihi: {auction.endDate}</Card.Text>

                      <Link to={`/auction/${auction.id}`}>
                        <Button
                          variant="warning"
                          className="text-decoration-none"
                        >
                          Incele
                        </Button>
                      </Link>
                    </Card.Body>
                  </Card>
                </Col>
              ))}
            </Row>
          </Container>
        )}
      </div>
    </div>
  );
}
