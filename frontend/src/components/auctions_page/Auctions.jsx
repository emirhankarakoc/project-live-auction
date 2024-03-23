import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import AuctionCard from "../general/AuctionCard";
import { Container, Row, Col, Form, Spinner } from "react-bootstrap";
import { http, httpError } from "../../lib/http";

export default function Auctions() {
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [postsPerPage, setPostsPerPage] = useState(12);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await http.get("/products");
        setProducts(response.data);
        setFilteredProducts(response.data);
      } catch (error) {
        console.error(httpError(error));
      } finally {
        setIsLoading(false);
      }
    };

    fetchProducts();
  }, []);

  const handleSearch = (term) => {
    setSearchTerm(term);
    const filtered = products.filter((product) =>
      product.productTitle.toLowerCase().includes(term.toLowerCase())
    );
    setFilteredProducts(filtered);
  };

  const handleInputChange = (event) => {
    handleSearch(event.target.value);
  };

  const handleChangePostsPerPage = (event) => {
    setPostsPerPage(parseInt(event.target.value));
  };

  const indexOfLastPost = postsPerPage;
  const indexOfFirstPost = 0;
  const currentPosts = filteredProducts.slice(
    indexOfFirstPost,
    indexOfLastPost
  );

  return (
    <div className="auctions-container">
      <Navbar name="Açık Arttırma Sayfası" />
      <div className="d-flex justify-content-center">
        <Container className="mt-4">
          <div
            className="text-center"
            style={{
              justifyContent: "center",
              alignItems: "center",
              display: "flex",
            }}
          >
            <Row className="align-items-end mb-4">
              <Col xs={12} md={9} className="mb-3 mb-md-0 text-center">
                {" "}
                <Form.Control
                  type="text"
                  value={searchTerm}
                  onChange={handleInputChange}
                  placeholder="Ürün Ara"
                  style={{ width: "100%" }} // Genişlik 100% yapıldı
                />
              </Col>
              <Col xs={6} md={3} className="mb-3 mb-md-0 text-center">
                <Form.Group
                  className="mb-0"
                  title="Gösterilecek Müzayede Sayısı"
                >
                  <Form.Control
                    as="select"
                    value={postsPerPage}
                    onChange={handleChangePostsPerPage}
                    style={{ width: "100%", textAlign: "center" }}
                  >
                    <option value={12}>12</option>
                    <option value={20}>20</option>
                    <option value={28}>28</option>
                  </Form.Control>
                </Form.Group>
              </Col>
            </Row>
          </div>
          {isLoading ? (
            <Row className="justify-content-center">
              <Col className="text-center">
                <Spinner animation="border" variant="light" />
                <p className="text-light mt-2">
                  Ürünler yüklenirken lütfen bekleyin.
                </p>
              </Col>
            </Row>
          ) : (
            <Row className="justify-content-start">
              {" "}
              {currentPosts.map((product, index) => (
                <Col key={index} xs={12} sm={6} md={4} lg={3} className="mb-4">
                  <AuctionCard
                    photoPath={product.photoPath}
                    productTitle={product.productTitle}
                    price={product.price}
                    id={product.id}
                  />
                </Col>
              ))}
            </Row>
          )}
        </Container>
      </div>
    </div>
  );
}
