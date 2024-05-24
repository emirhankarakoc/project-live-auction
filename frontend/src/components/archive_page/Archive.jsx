import React, { useEffect, useState } from "react";
import Navbar from "../general/Navbar";
import { http } from "../../lib/http";

export default function Archive() {
  const [archiveData, setArchiveData] = useState([]);

  useEffect(() => {
    // Component yüklendiğinde API çağrısını gerçekleştirme
    http
      .get("/auctions/archive")
      .then((response) => {
        setArchiveData(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div>
      <Navbar />
      <div className="container mt-4">
        <div className="row">
          {archiveData.length === 0 && (
            <div className="col">
              <p>Gösterilecek herhangi bir ürün yoktur.</p>
            </div>
          )}
          {archiveData.map((item, index) => (
            <div className="col-md-4 mb-4" key={index}>
              <div className="card">
                <img
                  src={item.photoPath}
                  className="card-img-top"
                  alt={item.productTitle}
                />
                <div className="card-body">
                  <h5 className="card-title">{item.productTitle}</h5>
                  <p className="card-text">
                    Satıcısı: {item.owner}
                    <br />
                    Satıldığı ücret: {item.price}TL
                    <br />
                    Açıklaması: {item.description}
                  </p>
                  <p className="card-text">
                    <small className="text-muted">
                      Satıldığı tarih: {item.endDate}
                    </small>
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
