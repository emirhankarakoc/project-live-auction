import React, { useState } from "react";
import {
  Button,
  Container,
  Row,
  Spinner,
  Modal,
  Dropdown,
  Pagination,
} from "react-bootstrap";
import { http, httpError } from "../../lib/http";
import { useEffect } from "react";
import { Link } from "react-router-dom";

export default function ListProducts(props) {
  const userToken = localStorage.getItem("userToken");

  const [role, setRole] = useState();
  const [products, setProducts] = useState();
  const [showModal, setShowModal] = useState(false);
  const [selectedProductId, setSelectedProductId] = useState(null);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(2);
  const [pageItems, setPageItems] = useState([]);
  const [isLoad, setIsLoad] = useState(false);
  const [productsSize, setProdctsSize] = useState(0);

  useEffect(() => {
    setPageItems(
      Array.from({
        length: Math.ceil(productsSize / size),
      })
    );
  }, [productsSize, size]);

  console.log(pageItems);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoad(true);
        const data = await http.get(`/users/token/${userToken}`);
        setRole(data.data);
        const productsData = await http.get(
          `/products/pageable?page=${page}&size=${size}`
        );
        setProducts(productsData.data.content);
        const res = await http.get(`/products/size`);
        setProdctsSize(res.data);
        setIsLoad(false);
      } catch (error) {
        console.log(httpError(error));
      }
    };

    fetchData();
  }, [page, size, userToken]);

  const handleDelete = (productId) => {
    setSelectedProductId(productId);
    setShowModal(true);
  };

  const confirmDelete = async () => {
    try {
      setIsLoad(true);
      // Silme işlemi yapılır
      await http.delete(`/products/${selectedProductId}/token/${userToken}`);

      // Yeniden ürünleri getir
      const productsData = await http.get(
        `/products/pageable?page=${0}&size=${9}`
      );
      setProducts(productsData.data.content);
      setIsLoad(false);

      setShowModal(false);
    } catch (error) {
      console.log(httpError(error));
    }
  };

  if (!role) return <Spinner />;

  if (role === "ROLE_USER") {
    window.location.replace("/");
    return null;
  }
  if (!products) {
    return (
      <div>
        <div className="text-light">
          Ürünler yüklenirken lütfen bekleyiniz
          <br />
          <Spinner className="mt-3" size="xl" />
        </div>
      </div>
    );
  }

  if (products.length === 0) {
    return (
      <div>
        <div className="text-light"> Gösterilecek herhangi bir ürün yok.</div>
        <br />
        <Link to={"/"}>
          <Button variant="warning" className="text-decoration-none">
            Ana sayfaya dön
          </Button>
        </Link>
      </div>
    );
  }

  const sizeItems = [1, 3, 9, 27];

  return (
    <div className="d-flex justify-content-center">
      <Container>
        <Row>
          {isLoad && (
            <div>
              <Spinner />
            </div>
          )}
        </Row>
        <div className="d-flex justify-content-center gap-3">
          <Pagination>
            {pageItems.map((_, index) => {
              // bu zımbırtıyı kullanmayacağız ama tanımlamız gerekiyor
              return (
                <Pagination.Item key={index} onClick={() => setPage(index)}>
                  {index + 1}
                </Pagination.Item>
              );
            })}
          </Pagination>
          <Dropdown>
            <Dropdown.Toggle id="dropdown-basic" variant="warning">
              {size.toString()}
            </Dropdown.Toggle>
            <Dropdown.Menu>
              {sizeItems.map((val) => {
                return (
                  <Dropdown.Item
                    onClick={() => {
                      setSize(val);
                      setPage(0);
                    }}
                  >
                    {val.toString()}
                  </Dropdown.Item>
                );
              })}
            </Dropdown.Menu>
          </Dropdown>
        </div>
        {products &&
          products.map((product, index) => (
            <Row
              key={index}
              className="border rounded-5 border-warning my-2 p-3"
            >
              <div className="col-md-4">
                <img
                  className="img-fluid"
                  src={product.photoPath}
                  style={{ height: "250px", width: "400px" }}
                  alt={product.productTitle}
                />
              </div>
              <div className="col-md-4">
                <div className="d-flex justify-content-center flex-column align-items-center h-100">
                  <div>Ürünün ismi: {product.productTitle}</div>
                  <div>Oluşturulma tarihi: {product.createddatetime}</div>
                  <div>Ekleyen kişi: {product.owner}</div>
                  <div>Durumu: {product.productStatus}</div>
                </div>
              </div>
              <div className="col-md-4">
                <div className="d-flex justify-content-center  align-content-center h-100 flex-column">
                  {/* <Button
                    className="bg-danger my-1 w-100"
                    onClick={() => handleDelete(product.id)}
                  >
                    Sil
                  </Button> */}
       
            <div>
              {
                product.productStatus==="YAYINDA"?(
                  <div>
                       <Button href={`/admin/auctions`}
                    className="bg-danger my-1 w-100"
                  >
                    Silmek icin muzayedeler sayfasina gitmelisin.
                  </Button>
                    <p>Zaten yayında, ilk önce onu kaldırın.</p>
                  </div>
                ):(
                  <div>
                       <Button
                    className="bg-danger my-1 w-100"
                    onClick={() => handleDelete(product.id)}
                  >
                    Sil
                  </Button>
                     <Link to={`/create/${product.id}`}>
                    <Button className="bg-success my-1 w-100">
                      Müzayede oluştur 
                    </Button>
                  </Link>
                  </div>
                )
              }
            </div>


                  
                </div>
              </div>
            </Row>
          ))}
      </Container>

      {/* Silme Modalı */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Ürünü Sil</Modal.Title>
        </Modal.Header>
        <Modal.Body>Ürünü silmek istediğinize emin misiniz?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            İptal
          </Button>
          <Button variant="danger" onClick={confirmDelete}>
            Sil
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
