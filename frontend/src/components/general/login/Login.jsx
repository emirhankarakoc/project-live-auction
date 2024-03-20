import { useState } from "react";
import { Col, Container, Row } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Navbar from "../Navbar";
import axios from "axios"; // Import axios for making HTTP requests

function Login(props) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [responseMessage, setResponseMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/accounts/login",
        {
          username,
          password,
        }
      );
      console.log(response.data);

      setResponseMessage(
        response.data
          ? `Giriş başarılı , yönlendiriliyorsunuz.`
          : "Login successful"
      );
      setTimeout(() => {
        localStorage.setItem("userToken", response.data);
        window.location.replace("/auctions");
      }, 2000);
    } catch (error) {
      console.log(error);
      setResponseMessage(`Error: ${error.response.data}`);
    }
  };

  return (
    <div>
      <Navbar />
      <Container>
        <Row className="justify-content-md-center">
          <Col md={6}>
            <h1 className="text-light text-center mb-4">Giriş Yap</h1>
            <Form onSubmit={handleSubmit}>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label className="text-light">
                  Mail Adresi & Kullanıcı adı
                </Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Kullanıcı adınızı girin"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
                <Form.Text className="text-white">
                  Mail adreslerinizi başkalarıyla paylaşmıyoruz.
                </Form.Text>
              </Form.Group>
              <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label className="text-light">Şifre</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Şifrenizi girin"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </Form.Group>
              <Button variant="danger" type="submit" className="w-100">
                Giriş Yap
              </Button>
            </Form>
            {responseMessage && (
              <h2 className="text-light mt-2 text-center">{responseMessage}</h2>
            )}
            <p className="text-light mt-3 text-center">
              Hesabınız yok mu?{" "}
              <Link to="/register" className="text-light">
                Kayıt Ol
              </Link>
            </p>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default Login;
