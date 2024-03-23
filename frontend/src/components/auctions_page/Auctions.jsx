import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import AuctionCard from "../general/AuctionCard";
import {
  Container,
  Row,
  Col,
  Form,
  Spinner,
  Card,
  Button,
} from "react-bootstrap";
import { http, httpError } from "../../lib/http";
import { Link } from "react-router-dom";

export default function Auctions() {
  const [auctions, setAuctions] = useState([]);
  const [message, setMessage] = useState("");
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
        setMessage(httpError(error));
      }
    };
    fetchAuctions();
  }, []);
  if (!auctions) {
    return (
      <div>
        <p>Ürünler yüklenirken lütfen bekleyin.</p>
        <Spinner />
      </div>
    );
  }

  if (auctions.length === 0) {
    return (
      <div>
        Gösterilecek herhangi bir ürün yok.
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
                <Col key={auction.id}>
                  <Card style={{ width: "18rem", margin: "50px" }}>
                    <Card.Img variant="top" src={auction.product.photoPath} />
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
