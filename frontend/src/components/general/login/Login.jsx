import { useState } from "react";
import { Col, Container, Row } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Navbar from "../Navbar";
import axios from "axios"; // Import axios for making HTTP requests

function Login() {
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
        response.data ? `Token: ${response.data}` : "Login successful"
      );
    } catch (error) {
      console.log(error);
      setResponseMessage(`Error: ${error.response.data}`);
    }
  };

  return (
    <div>
      <Navbar />
      <Container>
        <Row>
          <h1 className="text-light">Giriş Yap</h1>
          <Col>
            <Form
              style={{ width: "800px", marginLeft: "200px" }}
              onSubmit={handleSubmit}
            >
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label className="text-light">
                  Mail Adresi & Kullanıcı adı
                </Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Enter username"
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
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </Form.Group>
              <Button variant="danger" type="submit">
                Giriş Yap
              </Button>
            </Form>
            {responseMessage && (
              <h2 className="text-light">{responseMessage}</h2>
            )}
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default Login;
