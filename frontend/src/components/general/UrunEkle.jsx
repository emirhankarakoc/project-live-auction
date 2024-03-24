import React, { useEffect, useState } from "react";
import { http, httpError } from "../../lib/http";
import { Button, Form, Nav, Spinner } from "react-bootstrap";
import Navbar from "./Navbar";

export default function UrunEkle() {
  const userToken = localStorage.getItem("userToken");

  const [role, setRole] = useState();
  const [image, setImage] = useState("");
  const [message, setMessage] = useState("");
  const [isLoad, setIsLoad] = useState(false);

  useEffect(() => {
    const response = async () => {
      const data = await http.get(`/users/token/${userToken}`);
      setRole(data.data);
    };

    response();
  }, []);
  if (!role) return <Spinner />;

  if (role == "ROLE_USER") {
    window.location.replace("/");
  }
  async function handleSubmit(event) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    data.append("adminToken", localStorage.getItem("userToken"));

    try {
      setIsLoad(true);

      const response = await http.post(`/products`, data);
      if (response) {
        setIsLoad(false);
        setTimeout(() => {
          window.location.replace("/admin/products");
        }, 3000);
        setMessage(
          "Ürün eklenmiştir, yönlendiriliyorsunuz. Lütfen birkaç saniye bekleyin."
        );
      }
    } catch (error) {
      setIsLoad(false);

      setMessage("Ürün eklenirken bir problem oluştu.");

      console.log(httpError(error));
    }
  }
  function onImageChange(event) {
    const file = event.target.files[0]; // Access the file from the event
    const imageUrl = URL.createObjectURL(file); // Create URL for the file
    setImage(imageUrl); // Set the image URL state
  }
  return (
    <>
      <div className="d-flex justify-content-center border border-danger p-2">
        <div className="d-flex justify-content-center">
          {isLoad && <Spinner />}
        </div>

        <div className="col-md-6 col-lg-3">
          <Form onSubmit={handleSubmit} className="row g-3 ">
            <input
              type="file"
              name="multipartFile"
              className="form-control"
              accept="image/*"
              onChange={onImageChange}
            />
            {image && <img src={image} alt="Uploaded" />}

            <input
              type="text"
              name="productTitle"
              className="form-control"
              placeholder="Ürün ismi"
            />
            <Button variant="danger" type="submit" className="mb-2">
              Ekle
            </Button>
            <div className="d-flex justify-content-center">
              {message && <div>{message}</div>}
            </div>
          </Form>
        </div>
      </div>
    </>
  );
}
