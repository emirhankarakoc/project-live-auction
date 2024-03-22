import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import { Link } from "react-router-dom";
import AuctionCard from "../general/AuctionCard";
import {
  Container,
  Row,
} from "react-bootstrap";
import axios from "axios";
import { http, httpError } from "../../lib/http";
import Pagination from '../general/Pagination';

export default function Auctions() {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1)
  const [postsPerPage, setPostsPerPage] = useState(12);
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
	const totalPages = Math.ceil(products.length/postsPerPage)

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
	            {currentPosts.map((product, index) => {return (
					<AuctionCard
					key={index}
					photoPath={product.photoPath}
					productTitle={product.productTitle}
					price={product.price}
					id={product.id}
					/>					
				)}
	            )}
	          </Row>
	        )}
	        
	        <div class="d-flex justify-content-start">
		        <label className="text-light">Gösterilecek müzayede sayısı: &nbsp;
				<select value={postsPerPage} onChange={(event) => setPostsPerPage(event.target.value)}>
			  	<option value={12}>12</option>
			  	<option value={20}>20</option>
			  	<option value={28}>28</option>
				</select>
				</label>
			</div>
        	<label className="text-light mx-5">Sayfa {currentPage} / {totalPages}</label>
      	</Container>
      	
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