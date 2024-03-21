import React from "react";
import { Button, Form, InputGroup } from "react-bootstrap";

export default function AuctionSearcher() {
  return (
    <div>
      <InputGroup className="mb-3">
        <Form.Control
          placeholder="Güncel müzayedelerde ara"
          aria-label="Güncel müzayedelerde ara"
          aria-describedby="basic-addon2"
        />
        <Button variant="outline-light" id="button-addon2">
          Ara
        </Button>
      </InputGroup>
    </div>
  );
}

/*

import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import { Link } from "react-router-dom";
import { Button, Container, Form, InputGroup, Col, Row, Card, CardBody, CardTitle, CardSubtitle } from "react-bootstrap";
import { http, httpError } from "../../lib/http";

export default function AuctionSearcher() {
	const [searchValue, setSearchValue] = useState('');
	const [products, setProducts] = useState([]);
  	const [isLoading, setIsLoading] = useState(true);
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

 function handleSearchClick() {
    if (searchValue === "") { setProducts(products); return; }
    const filterBySearch = products.filter((item) => {
        if (item.toLowerCase()
            .includes(searchValue.toLowerCase())) { return item; }
    })
    setProducts(filterBySearch);
};
	
  return (
    <div>
	    <Container>
	    	<Navbar/>
	    	<InputGroup className="mb-3">
		        <Form.Control
		          placeholder="Güncel müzayedelerde ara"
		          aria-label="Güncel müzayedelerde ara"
		          aria-describedby="basic-addon2"
		          onChange={e => setSearchValue(e.target.value)}
		        />
		        <Button variant="outline-light" id="button-addon2" onClick={handleSearchClick}>
		          Ara
		        </Button>
	      </InputGroup>
	    
	    <Row className="mx-4 my-2">
            {products.map((product) => (
              <Col key={product.id}>
                <Card
                  style={{
                    width: "18rem",
                    marginBottom: "20px",
                    backgroundColor: "white",
                  }}
                >
                  <Card.Img
                    variant="top"
                    src={product.photoPath}
                    style={{ height: "150px" }}
                  />
                  <CardBody>
                    <CardTitle tag="h5">{product.productTitle}</CardTitle>
                    <CardSubtitle className="mb-2 text-muted" tag="h6">
                      {product.price} TL
                    </CardSubtitle>

                    <Link
                      to={`/product/${product.id}`}
                      className="text-white text-decoration-none"
                    >
                      {" "}
                      <Button>Incele</Button>
                    </Link>
                  </CardBody>
                </Card>
              </Col>
            ))}
          </Row>
	    
	    </Container>
    </div>
  );
}

*/