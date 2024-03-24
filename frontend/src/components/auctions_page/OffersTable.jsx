import { useEffect, useState } from "react";
import { http, httpError } from "../../lib/http";
import { Col, Container, Row } from "react-bootstrap";

export default function OffersTable(props) {
  const [offers, setOffers] = useState([]);

  useEffect(() => {
    const fetchOffers = async () => {
      try {
        const response = await http.get(`/offers/auction/${props.id}`);
        setOffers(response.data);
        console.log(offers);
      } catch (error) {
        console.log(httpError(error));
      }
    };

    fetchOffers();
  }, [props.id]);

  return (
    <div>
      <Container className="border border-success">
        <Row>
          <Col>
            <strong>Kişi İsmi</strong>
          </Col>
          <Col>
            <strong>Teklifi</strong>
          </Col>
        </Row>
        {offers.map((offer) => (
          <Row key={offer.id}>
            <Col className="text-light">{offer.fullname}</Col>
            <Col>{offer.price} TL</Col>
          </Row>
        ))}
      </Container>
    </div>
  );
}
