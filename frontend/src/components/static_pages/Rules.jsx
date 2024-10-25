import { Col, Container, Row } from "react-bootstrap";
import Navbar from "../general/Navbar";

function Rules() {
  return (
    <div>
      <Navbar />
      <Container>
        <Row>
          <Col>
            <h2 style={{ color: "white" }}>Açık Artırma Kuralları</h2>
            <table>
              <tr>
                <th>Kural</th>
                <th>Açıklama</th>
              </tr>
              <tr>
                <td>1.</td>
                <td>
                  Başlangıç ve Bitiş Zamanı: Açık artırma belirli bir başlangıç
                  ve bitiş zamanı içinde gerçekleşir.
                </td>
              </tr>
              <tr>
                <td>2.</td>
                <td>
                  Kayıt: Açık artırmaya katılmak isteyen herkesin kayıt olması
                  gereklidir.
                </td>
              </tr>
              <tr>
                <td>3.</td>
                <td>
                  Teklif Verme: Kayıtlı katılımcılar açık artırma süresince
                  teklif verebilirler.
                </td>
              </tr>
              <tr>
                <td>4.</td>
                <td>
                  Artırma Miktarı: Artırma miktarı, her teklifin üzerine en az
                  50 TL olacak şekilde ayarlanmıştır. Fazlası için birden fazla
                  kez teklif verebilirsiniz.
                </td>
              </tr>
              <tr>
                <td>5.</td>
                <td>
                  Satışın Onaylanması: Açık artırma sona erdiğinde, en yüksek
                  teklif veren kişi ürünü satın alır.
                </td>
              </tr>
              <tr>
                <td>6.</td>
                <td>
                  Ödeme ve Teslimat: Satın alınan ürün için ödeme hemen
                  gerçekleştirilmelidir.
                </td>
              </tr>
              <tr>
                <td>7.</td>
                <td>
                  İptal ve İtirazlar: Açık artırma sürecinde herhangi bir
                  aşamada hile veya yanlış anlaşılma tespit edilirse.
                </td>
              </tr>
              <tr>
                <td>8.</td>
                <td>
                  Kuralların Değiştirilmesi: Açık artırma sürecinde gerekli
                  görüldüğünde, yetkililer kuralları değiştirme hakkını saklı
                  tutarlar.
                </td>
              </tr>
            </table>
          </Col>
        </Row>
      </Container>
    </div>
  );
}
export default Rules;
