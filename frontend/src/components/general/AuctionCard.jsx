import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  Button,
  Card,
  CardBody,
  CardSubtitle,
  CardText,
  CardTitle,
  Col,
  Container,
  Row,
} from "react-bootstrap";

const AuctionCard = ({photoPath, productTitle, price, id}) => {	
	return (
		<div className="col-3">
			<Card
			  style={{
			    width: "18rem",
			    marginBottom: "20px",
			    backgroundColor: "white",
			  }}
			>
			  <Card.Img
			    variant="top"
			    src={photoPath}
			    style={{ height: "150px" }}
			  />
			  <CardBody>
			    <CardTitle tag="h5">{productTitle}</CardTitle>
			    <CardSubtitle className="mb-2 text-muted" tag="h6">
			      {price} TL
			    </CardSubtitle>
			
			    <Link
			      to={`/product/${id}`}
			      className="text-white text-decoration-none"
			    >
			      {" "}
			      <Button>Incele</Button>
			    </Link>
			  </CardBody>
			</Card>
		</div>
	);
};

export default AuctionCard;

