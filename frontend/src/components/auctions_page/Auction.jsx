import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import Navbar from "../general/Navbar";
import { Button, Col, Container, Row } from "react-bootstrap";
import { http, httpError } from "../../lib/http";
import OffersTable from "./OffersTable";
import io from "socket.io-client";

export default function Auction(props) {
  const { id } = useParams();

  const [auction, setAuction] = useState(null);
  const [newOffer, setNewOffer] = useState(false);

  useEffect(() => {
    const socket = io("ws://ws.backend.kgzkbi.easypanel.host/", {
      //  const socket = io("ws://10.64.67.66:8085", {
      path: "/socket.io/",
      transports: ["websocket"],
      upgrade: false,
    });

    socket.on("new_offer", () => {
      console.log("teklif geldi!");
      setNewOffer(!newOffer);
    });

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
    return () => {
      socket.disconnect();
    };
  }, []);
  const createOffer = async () => {
    if (!localStorage.getItem("userToken")) {
      window.location.replace("/login");
    }
    try {
      const form = new FormData();
      form.append("price", auction.startPrice + 50);
      form.append("auctionId", auction.id);
      form.append("userToken", localStorage.getItem("userToken"));

      const data = await http.post("/offers", form);
      setNewOffer(!newOffer); // Teklif yapıldığında OffersTable yenilensin
    } catch (error) {
      console.log(httpError(error));
      window.location.replace(`/auction/${auction.id}`);
    } finally {
      setAuction((prevAuction) => ({
        ...prevAuction,
        startPrice: auction.startPrice + 50,
      }));
    }
    console.log("yeni teklif gonderdim.");
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
                  style={{ width: "700px", height: "300px" }}
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
              <OffersTable key={newOffer} id={auction.id} />
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
