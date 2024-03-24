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
      <InputGroup>
        <Form.Control
          placeholder="Müzayede arama"
          aria-label="Güncel müzayedelerde ara"
          aria-describedby="basic-addon2"
          value={searchTerm}
          onChange={handleChange}
        />
        {searchTerm && (
          <Button
            href={`/auctions/search/${searchTerm}`}
            variant="outline-light"
            id="button-addon2"
          >
            Ara
          </Button>
        )}
      </InputGroup>
    </div>
  );
}
