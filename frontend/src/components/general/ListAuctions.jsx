import React, { useState } from "react";
import { Button, Col, Container, Row, Spinner, Modal } from "react-bootstrap";
import { http, httpError } from "../../lib/http";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import Navbar from "./Navbar";

export default function ListAuctions(props) {
  const userToken = localStorage.getItem("userToken");

  const [role, setRole] = useState();
  const [auctions, setAuctions] = useState();
  const [showModal, setShowModal] = useState(false);
  const [selectedAuctionId, setSelectedAuctionId] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await http.get(`/users/token/${userToken}`);
        setRole(data.data);
        const auctionsData = await http.get(`/auctions/page/${0}/size/${9}`);
        setAuctions(auctionsData.data.content);
      } catch (error) {
        console.log(httpError(error));
      }
    };

    fetchData();
  }, []);

  const handleDelete = (auctionId) => {
    setSelectedAuctionId(auctionId);
    setShowModal(true);
  };
  const setProductAuctionStatusToReady = async (auctionId) => {
    try {
      // YAY
      const degistir = await http.put(
        `/auctions/filter/ready/auction/${auctionId}/token/${userToken}`
      );
      setTimeout(() => {
        window.location.replace("/auctions");
      }, 2000);
    } catch (error) {
      console.log(httpError(error));
    }
  };
  const confirmDelete = async () => {
    try {
      // Silme işlemi yapılır
      await http.delete(`/auctions/${selectedAuctionId}/token/${userToken}`);
      // Yeniden ürünleri getir
      const auctionsData = await http.get(`/auctions/page/${0}/size/${9}`);
      setAuctions(auctionsData.data.content);
      setShowModal(false);
    } catch (error) {
      console.log(httpError(error));
    }
  };

  if (!role) return <Spinner />;

  if (role === "ROLE_USER") {
    window.location.replace("/");
    return null;
  }
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
        <div className="text-light my-3 p-2">
          {" "}
          Gösterilecek herhangi bir ürün yok.
        </div>
        <Link to={"/"}>
          <Button variant="warning" className="text-decoration-none">
            Ana sayfaya dön
          </Button>
        </Link>
      </div>
    );
  }
  return (
    <div className="d-flex justify-content-center">
      <Container>
        {auctions &&
          auctions.map((auction, index) => (
            <Row key={index} className="border border-warning my-2 p-2">
              <Col>
                <div>
                  <Row>
                    <Col>
                      <img
                        className="d-flex float-left"
                        src={auction.product.photoPath}
                        style={{ height: "250px", width: "400px" }}
                        alt={auction.product.productTitle}
                      />
                    </Col>
                    <Col className="d-flex float-left align-items-center">
                      <div>
                        <Row>Ürünün ismi: {auction.product.productTitle}</Row>
                        <Row>Ekleyen kişi: {auction.product.owner}</Row>
                        <Row>Başlangıç fiyatı: {auction.startPrice}</Row>
                        <Row>Bitiş tarihi: {auction.endDate}</Row>
                        <Row>Açıklama: {auction.description}</Row>
                      </div>
                    </Col>
                  </Row>
                </div>
              </Col>
              <Col xl={2}>
                <div>
                  <Row>
                    <Button
                      className="bg-danger my-1"
                      onClick={() => handleDelete(auction.id)}
                    >
                      Sil
                    </Button>
                  </Row>
                  <Row>
                    <Button className="bg-warning my-1">Güncelle</Button>
                  </Row>
                  <Row>
                    <Button
                      className="bg-success my-1"
                      onClick={() => {
                        setProductAuctionStatusToReady(auction.id);
                      }}
                    >
                      Yayınla
                    </Button>
                  </Row>
                </div>
              </Col>
            </Row>
          ))}
      </Container>

      {/* Silme Modalı */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Ürünü Sil</Modal.Title>
        </Modal.Header>
        <Modal.Body>Ürünü silmek istediğinize emin misiniz?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            İptal
          </Button>
          <Button variant="danger" onClick={confirmDelete}>
            Sil
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
