import React, { useState } from "react";
import {
  Button,
  Col,
  Container,
  Row,
  Spinner,
  Modal,
  Pagination,
  Dropdown,
} from "react-bootstrap";
import { http, httpError } from "../../lib/http";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import Navbar from "./Navbar";

export default function ListAuctions(props) {
  const userToken = localStorage.getItem("userToken");

  const [role, setRole] = useState();
  const [auctions, setAuctions] = useState();
  const [auctionsSize, setAuctionsSize] = useState(0);

  const [showModal, setShowModal] = useState(false);
  const [selectedAuctionId, setSelectedAuctionId] = useState(null);
  const [isLoad, setIsLoad] = useState(false);
  const [responseMessage, setResponseMessage] = useState();
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(2);
  const [pageItems, setPageItems] = useState([]);

  useEffect(() => {
    setPageItems(
      Array.from({
        length: Math.ceil(auctionsSize / size),
      })
    );
  }, [auctionsSize, size]);

  console.log(pageItems);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoad(true);
        const data = await http.get(`/users/token/${userToken}`);
        setRole(data.data);
        const auctionsData = await http.get(
          `/auctions/pageable?page=${page}&size=${size}`
        );
        setAuctions(auctionsData.data.content);

        const productsSize = await http.get(`/auctions/size`);
        setAuctionsSize(productsSize.data);
        setIsLoad(false);
      } catch (error) {
        console.log(httpError(error));
      }
    };

    fetchData();
  }, [page, size, userToken]);
  console.log(auctions);
  const handleDelete = (auctionId) => {
    setSelectedAuctionId(auctionId);
    setShowModal(true);
  };
  const setProductAuctionStatusToReady = async (auctionId) => {
    try {
      // YAY
      setIsLoad(true);

      const degistir = await http.put(
        `/auctions/filter/ready/auction/${auctionId}/token/${userToken}`
      );
      setIsLoad(false);
      setResponseMessage(
        "Müzayede başlatılmıştır, yönlendiriliyorsunuz. Lütfen birkaç saniye bekleyiniz."
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
      setIsLoad(true);
      await http.delete(`/auctions/${selectedAuctionId}/token/${userToken}`);
      // Yeniden ürünleri getir

      const auctionsData = await http.get(
        `/auctions/pageable?page=${page}&size=${size}`
      );
      setAuctions(auctionsData.data.content);

      setIsLoad(false);
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
        <div className="text-light">
          Müzayedeler yüklenirken lütfen bekleyiniz
          <br />
          <Spinner className="mt-2" size="xl" />
        </div>
      </div>
    );
  }
  if (auctions.length === 0) {
    return (
      <div>
        <div className="text-light my-2 p-1">
          Gösterilecek herhangi bir müzayede yok.
        </div>
        <Link to={"/"}>
          <Button variant="warning" className=" mx-2 text-decoration-none">
            Ana sayfaya dön
          </Button>
        </Link>
        veya
        <Link to={"/admin/products"}>
          <Button variant="warning" className=" mx-2 text-decoration-none">
            Ürün ekle
          </Button>
        </Link>
      </div>
    );
  }
  const sizeItems = [1, 3, 9, 27];

  return (
    <div className="d-flex justify-content-center">
      <Container>
        <Row>
          {isLoad && (
            <div>
              <Spinner />
            </div>
          )}
        </Row>
        <div className="d-flex justify-content-center gap-3">
          <Pagination>
            {pageItems.map((_, index) => {
              // bu zımbırtıyı kullanmayacağız ama tanımlamız gerekiyor
              return (
                <Pagination.Item key={index} onClick={() => setPage(index)}>
                  {index + 1}
                </Pagination.Item>
              );
            })}
          </Pagination>
          <Dropdown>
            <Dropdown.Toggle id="dropdown-basic" variant="warning">
              {size.toString()}
            </Dropdown.Toggle>
            <Dropdown.Menu>
              {sizeItems.map((val) => {
                return (
                  <Dropdown.Item
                    onClick={() => {
                      setSize(val);
                      setPage(0);
                    }}
                  >
                    {val.toString()}
                  </Dropdown.Item>
                );
              })}
            </Dropdown.Menu>
          </Dropdown>
        </div>
        <div className="row">
          {responseMessage && <div>{responseMessage}</div>}
        </div>
        {auctions &&
          auctions.map((auction, index) => (
            <div key={index} className="row border border-warning my-2 p-2">
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
                        <Row>Durumu: {auction.status}</Row>
                        <Row>Ürünün ismi: {auction.product.productTitle}</Row>
                        <Row>Ekleyen kişi: {auction.product.owner}</Row>
                        <Row>Başlangıç fiyatı: {auction.startPrice}</Row>
                        <Row>Bitiş tarihi: {auction.endDate}</Row>
                        Açıklama:
                        <Row style={{ maxWidth: "300px", overflowY: "auto" }}>
                          {auction.description}
                        </Row>
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
                      Yayından kaldır
                    </Button>
                  </Row>

                  <Row>
                    <Button
                      className="bg-success my-1"
                      onClick={() => {
                        setProductAuctionStatusToReady(auction.id);
                      }}
                    >
                      Tekliflere aç
                    </Button>
                  </Row>
                  <Row>
                    <Button className="bg-primary my-1">
                      <Link
                        to={`/auction/${auction.id}/offers`}
                        className="text-decoration-none text-light"
                      >
                        Teklifleri gör
                      </Link>
                    </Button>
                  </Row>
                </div>
              </Col>
            </div>
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
          {isLoad && (
            <div>
              <Spinner />
            </div>
          )}
        </Modal.Footer>
      </Modal>
    </div>
  );
}
