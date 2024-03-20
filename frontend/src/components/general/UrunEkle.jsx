import React, { useEffect, useState } from "react";
import { http, httpError } from "../../lib/http";
import { Button, Form, Nav, Spinner } from "react-bootstrap";
import Navbar from "./Navbar";

export default function UrunEkle() {
  const userToken = localStorage.getItem("userToken");

  const [role, setRole] = useState();
  const [image, setImage] = useState("");

  useEffect(() => {
    const response = async () => {
      const data = await http.get(`/accounts/token/${userToken}`);
      setRole(data.data);
    };

    response();
  }, []);
  console.log(role, "a31");
  if (!role) return <Spinner />;

  if (role == "ROLE_USER") {
    window.location.replace("/");
  }
  async function handleSubmit(event) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    data.append("adminToken", localStorage.getItem("userToken"));
    try {
      const response = await http.post(`/products`, data);
      alert("urun olusturuldu.");
    } catch (error) {
      httpError(error);
    }
  }
  function onImageChange(event) {
    const file = event.target.files[0]; // Access the file from the event
    const imageUrl = URL.createObjectURL(file); // Create URL for the file
    setImage(imageUrl); // Set the image URL state
  }

  console.log(role);

  return (
    <>
      <Navbar />

      <div className="d-flex justify-content-center">
        <div className="col-md-6 col-lg-3">
          <h2>ADMIN SAYFASI</h2>
          <Form onSubmit={handleSubmit} className="row g-3">
            <input
              type="file"
              name="multipartFile"
              className="form-control"
              accept="image/*"
              onChange={onImageChange}
            />
            {image && <img src={image} alt="Uploaded" />}{" "}
            {/* Display the image */}
            <input
              type="text"
              name="price"
              className="form-control"
              placeholder="Fiyat"
            />
            <input
              type="text"
              name="productTitle"
              className="form-control "
              placeholder="Ürün ismi"
            />
            <Button variant="danger" type="submit">
              Urun ekle
            </Button>
          </Form>
        </div>
      </div>
    </>
  );
}
