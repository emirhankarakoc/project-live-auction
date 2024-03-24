import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import { Container, Row, Col, Spinner, Card, Button } from "react-bootstrap";
import { http } from "../../lib/http";
import { Link, useParams } from "react-router-dom";

export default function FilteredAuctions() {
  const [auctions, setAuctions] = useState([]);
  const [loading, setLoading] = useState(true);
  const { keyword } = useParams();
  if (!keyword) {
    window.location.replace("/auctions");
  }
  useEffect(() => {
    const fetchAuctions = async () => {
      try {
        const data = await http.get(
          `/auctions/page/0/size/10/search/${keyword}`
        );
        setAuctions(data.data.content);
      } catch (error) {
        console.log(error);
      } finally {
        setLoading(false);
      }
    };
    fetchAuctions();
  }, [keyword]);

  if (loading) {
    return (
      <div>
        <Navbar />
        <div className="text-light">
          Müzayedeler yüklenirken lütfen bekleyiniz
          <br />
          <Spinner className="mt-3" size="xl" />
        </div>
      </div>
    );
  }

  if (auctions.length === 0) {
    return (
      <div>
        <Navbar />
        <div className="text-light">
          Gösterilecek herhangi bir müzayede yok.
        </div>
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
