import React, { useState } from "react";
import { Button, Form, InputGroup } from "react-bootstrap";
import { Link } from "react-router-dom";

export default function AuctionSearcher() {
  const [searchTerm, setSearchTerm] = useState("");

  const handleChange = (event) => {
    setSearchTerm(event.target.value);
  };

  return (
    <div>
      <InputGroup className="mb-3">
        <Form.Control
          placeholder="Müzayede arama"
          aria-label="Güncel müzayedelerde ara"
          aria-describedby="basic-addon2"
          value={searchTerm}
          onChange={handleChange}
        />
        {searchTerm && (
          <Button variant="outline-light" id="button-addon2">
            <Link to={`/auctions/search/${searchTerm}`}>Ara</Link>
          </Button>
        )}
      </InputGroup>
    </div>
  );
}
