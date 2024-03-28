import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import {
  Container,
  Row,
  Col,
  Spinner,
  Card,
  Button,
  Pagination,
  Dropdown,
} from "react-bootstrap";
import { http } from "../../lib/http";
import { Link } from "react-router-dom";

export default function Auctions() {
  const [auctions, setAuctions] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(2);
  const [pageItems, setPageItems] = useState([]);
  const [isLoad, setIsLoad] = useState(false);
  const [auctionsSize, setAuctionsSize] = useState(0);

  useEffect(() => {
    setPageItems(
      Array.from({
        length: Math.ceil(auctionsSize / size),
      })
    );
  }, [auctionsSize, size]);

  useEffect(() => {
    const fetchAuctions = async () => {
      try {
        setIsLoad(true);
        const data = await http.get(
          `/auctions/filter/ready/pageable?page=${page}&size=${size}`
        );
        setAuctions(data.data.content);
        const res = await http.get(`/auctions/filter/ready/size`);
        setAuctionsSize(res.data);
        setIsLoad(false);
      } catch (error) {
        console.log(error);
      }
    };
    fetchAuctions();
  }, [page, size]);
  if (isLoad) {
    return (
      <div>
        <Navbar />
        <Spinner />
      </div>
    );
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
  const sizeItems = [1, 3, 9, 27];

  return (
    <div>
      <Navbar />
      <div>
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
        {auctions && (
          <Container>
            <Row>
              {auctions.map((auction) => (
                <Col key={auction.id} xl={4} sm={4}>
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
