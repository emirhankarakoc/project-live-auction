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
        <Button variant="outline-danger" id="button-addon2">
          Ara
        </Button>
      </InputGroup>
    </div>
  );
}
