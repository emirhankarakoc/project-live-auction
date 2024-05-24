import React, { useEffect, useState } from "react";
import { http, httpError } from "../../lib/http";
import { Button, Col, Container, Row, Spinner } from "react-bootstrap";
import { useParams } from "react-router-dom";
import Navbar from "./Navbar";

export default function ProductOffersList() {
  const [offers, setOffers] = useState([]);
  const [isLoad, setIsLoad] = useState(false);
  const { auctionId } = useParams();
  const [auction, setAuction] = useState();
  const [mesaj, setMesaj] = useState("");
  const [satildiMi, setSatildiMi] = useState(false);

  useEffect(() => {
    const fetchOffers = async () => {
      try {
        setIsLoad(true);
        const response = await http.get(`/offers/auction/${auctionId}`);
        setOffers(response.data);
        setIsLoad(false);
      } catch (error) {
        setIsLoad(false);
        console.log(httpError(error));
        setMesaj(error);
      }
    };
    const fetchAuction = async () => {
      try {
        setIsLoad(true);
        const response = await http.get(`/auctions/${auctionId}`);
        setAuction(response.data);
        setIsLoad(false);
      } catch (error) {
        console.log(httpError(error));
      }
    };

    fetchAuction();
    fetchOffers();
  }, [auctionId]);

  const urunuSat = async () => {
    const form = new FormData();
    const mesaj = `${auction.product.owner} kişisinin sattığı ${auction.product.productTitle} ürününü ${auction.startPrice} tlye satın aldınız. Lütfen yönetim ekibi ile iletişime geçiniz. burayisilme_backend_icin_lazim  ${auction.product.photoPath}`;
    form.append("mesaj", mesaj);
    console.log(mesaj);

    try {
      setIsLoad(true);
      console.log(offers[0].userToken, localStorage.getItem("userToken"));
      const data = await http.post(
        `/mail/to/${offers[0].userToken}/adminToken/${localStorage.getItem(
          "userToken"
        )}`,
        form
      );
      const urunuSatilmisYap = await http.put(
        `/auctions/${auction.id}/${localStorage.getItem("userToken")}/sell`
      );

      setMesaj("Ürün başarıyla satıldı. Haneye hayırlı olsun.");
      setSatildiMi(true);
      setIsLoad(false);
    } catch (error) {
      console.log(httpError(error));
    }

    console.log();
    //ürünün statusunu değiştir.
  };

  if (isLoad) {
    return (
      <div className="text-light">
        <Navbar />
        <p>İşlem tamamlanırken lütfen bekleyiniz.</p>
        <Spinner />
      </div>
    );
  }
  if (offers.length === 0) {
    return (
      <div>
        <Navbar />
        <div className="text-light">Gösterilecek hiçbir teklif yok.</div>
        <div>
          <Button href="/admin/auctions">Geri dön</Button>
        </div>
      </div>
    );
  }
  return (
    <div>
      {offers && (
        <Container>
          <Navbar />
          <Row>
            <Row>
              <Col>
                <img
                  src={auction.product.photoPath}
                  style={{ height: "400px" }}
                ></img>
              </Col>
            </Row>
            <Col className="text-light">
              {!satildiMi && (
                <Button
                  className="bg-warning my-2 text-dark text-decoration-none"
                  onClick={urunuSat}
                >
                  {auction.startPrice} TL'YE {offers[0].fullname} KİŞİSİNE SAT
                </Button>
              )}
              <div>
                {" "}
                <Button>Teklifleri döküman olarak indir.</Button>
              </div>
              <div className="my-2">{mesaj}</div>
              <div>
                <h5 className="d-flex float-left">Offers List</h5>
                {offers.map((offer) => (
                  <div key={offer.id}>
                    <Row className="border border-warning p-1">
                      <Col>Kişi: {offer.fullname}</Col>
                      <Col>Fiyat: {offer.price}</Col>
                    </Row>
                  </div>
                ))}
              </div>
            </Col>
          </Row>
        </Container>
      )}
    </div>
  );
}
