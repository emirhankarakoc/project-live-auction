import React from "react";
import { Link, useParams } from "react-router-dom";
import Navbar from "../general/Navbar";
import { Button } from "react-bootstrap";

export default function Auction(props) {
  const { id } = useParams();

  return (
    <div className="bg-danger">
      <Navbar />
      <h2>Product Detail</h2>
      <p>ID: {id}</p>
      <p> birsuru urun vs.</p>

      <Button>
        <Link to={"/auctions"} className="text-white text-decoration-none">
          Geri Dön
        </Link>
      </Button>
    </div>
  );
}
