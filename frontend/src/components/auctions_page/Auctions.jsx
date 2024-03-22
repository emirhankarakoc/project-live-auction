import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import { Link } from "react-router-dom";
import AuctionCard from "../general/AuctionCard";
import { Container, Row, Form } from "react-bootstrap"; // Form eklendi
import axios from "axios";
import { http, httpError } from "../../lib/http";
import Pagination from "../general/Pagination";

export default function Auctions() {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage, setPostsPerPage] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");

  const token = localStorage.getItem("userToken");

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await http.get("/products");
        setProducts(response.data);
      } catch (error) {
        console.error(httpError(error));
      } finally {
        setIsLoading(false);
      }
    };

    fetchProducts();
  }, []);

  useEffect(() => {
    const filteredProducts = searchTerm
      ? products.filter((product) =>
          product.productTitle.toLowerCase().includes(searchTerm.toLowerCase())
        )
      : products;

    setProducts(filteredProducts);
  }, [searchTerm]);

  const lastPostIndex = currentPage * postsPerPage;
  const firstPostIndex = lastPostIndex - postsPerPage;
  const currentPosts = products.slice(firstPostIndex, lastPostIndex);
  const totalPages = Math.ceil(products.length / postsPerPage);

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  return (
    <div>
      <Container>
        <Navbar name="Açık Arttırma Sayfası" />
        <Link to="/" className="text-white">
          {!token && <div>Ana sayfaya dön</div>}
        </Link>

        {/* Bootstrap form elemanı */}
        <Form className="my-3">
          <Form.Control
            type="text"
            placeholder="Ürün ara..."
            value={searchTerm}
            onChange={handleSearch}
          />
        </Form>

        {isLoading ? (
          <p className="text-light">Ürünler yüklenirken lütfen bekleyin.</p>
        ) : (
          <Row className="my-3">
            {currentPosts.map((product, index) => (
              <AuctionCard
                key={index}
                photoPath={product.photoPath}
                productTitle={product.productTitle}
                price={product.price}
                id={product.id}
              />
            ))}
          </Row>
        )}
      </Container>
      <label className="text-light">
        Sayfa {currentPage} / {totalPages}
      </label>
      <Pagination
        totalPosts={products.length}
        postsPerPage={postsPerPage}
        setCurrentPage={setCurrentPage}
      />
    </div>
  );
}
