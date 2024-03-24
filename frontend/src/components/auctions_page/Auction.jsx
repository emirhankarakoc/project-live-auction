import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import Navbar from "../general/Navbar";
import { Button, Col, Container, Row } from "react-bootstrap";
import { http, httpError } from "../../lib/http";
import OffersTable from "./OffersTable";
import { io } from "socket.io-client";

export default function Auction(props) {
  const { id } = useParams();

  const [auction, setAuction] = useState(null);
  const [newOffer, setNewOffer] = useState(null);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await http.get(`/auctions/${id}`);
        console.log(response.data);
        setAuction(response.data);
      } catch (error) {
        console.error(httpError(error));
      }
    };

    fetchProduct();

    const socket = io("wss://ws.backend.kgzkbi.easypanel.host", {
      autoConnect: true,
    });
    socket.on("new_offer", () => {
      console.log("dinliyorum");
    });
  }, []);
  const createOffer = async () => {
    try {
      const form = new FormData();
      form.append("price", auction.startPrice + 50);
      form.append("auctionId", auction.id);
      form.append("userToken", localStorage.getItem("userToken"));

      const data = await http.post("/offers", form);
      setAuction((prevAuction) => ({
        ...prevAuction,
        startPrice: prevAuction.startPrice + 50,
      }));
    } catch (error) {
      console.log(httpError(error));
    }
    console.log("yeni teklif geldi.");
  };
  if (!auction) return <></>;
  return (
    <div className="bg-dark">
      <Navbar />

      {auction && (
        <Container>
          <Row className="my-3">
            <Col>
              <div>
                <h2 className="text-light d-flex float-left">
                  {auction.product.productTitle}
                </h2>
                <img
                  style={{ width: "700px" }}
                  src={auction.product.photoPath}
                ></img>
                <h4 className="text-light my-2">
                  FIYAT: {auction.startPrice} TL
                </h4>
              </div>
              <Button className="bg-success" onClick={createOffer}>
                {auction.startPrice + 50} TL TEKLIF YAP
              </Button>
            </Col>
            <Col className="text-white">
              burada teklifler olcak <OffersTable id={auction.id} />
            </Col>
          </Row>

          <h3 className="text-light d-flex float-left">ÜRÜN AÇIKLAMASI</h3>
          <div className="border border-success text-white d-flex float-left  p-2 my-3">
            {auction.description}
          </div>
        </Container>
      )}

      <Link to={"/auctions"} className="text-white text-decoration-none">
        <Button>Geri Dön</Button>
      </Link>
    </div>
  );
}
