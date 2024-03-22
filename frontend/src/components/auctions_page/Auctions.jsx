import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import { Link } from "react-router-dom";
import AuctionCard from "../general/AuctionCard";
import { Container, Nav, Row } from "react-bootstrap";
import axios from "axios";
import { http, httpError } from "../../lib/http";
import Pagination from "../general/Pagination";

export default function Auctions() {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage, setPostsPerPage] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
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

    console.log(localStorage.getItem("userToken"));
    fetchProducts(); // Call the function
  }, []);

  const lastPostIndex = currentPage * postsPerPage;
  const firstPostIndex = lastPostIndex - postsPerPage;
  const currentPosts = products.slice(firstPostIndex, lastPostIndex);
  const totalPages = products.length / postsPerPage;

  if (products.length == 0)
    return (
      <>
        <Navbar />
        <div>
          <h3 className="text-light">
            Aktif herhangi bir müzayede bulunmamaktadır.
          </h3>
        </div>
      </>
    );

  return (
    <div>
      <Container>
        <Navbar name="Açık Arttırma Sayfası" />

        <Link to="/" className="text-white">
          {!token && <div>Ana sayfaya dön</div>}
        </Link>
        {isLoading ? (
          <p className="text-light">Ürünler yüklenirken lütfen bekleyin.</p>
        ) : (
          <Row className="my-3">
            {currentPosts.map((product, index) => {
              return (
                <AuctionCard
                  key={index}
                  photoPath={product.photoPath}
                  productTitle={product.productTitle}
                  price={product.price}
                  id={product.id}
                />
              );
            })}
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

/*
{postsPerPage === 10 && <SomeComponent/>}
{postsPerPage === 20 && <SomeOtherComponent/>}
{postsPerPage === 28 && <SomeAnotherComponent />}
*/
