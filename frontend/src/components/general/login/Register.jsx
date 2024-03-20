import React, { useState } from "react";
import { Col, Container, Row, Form, Button } from "react-bootstrap";
import Navbar from "../Navbar";
import axios from "axios";
import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as Yup from "yup";

const validationSchema = Yup.object().shape({
  firstname: Yup.string().required("Ad alanı zorunludur"),
  lastname: Yup.string().required("Soyad alanı zorunludur"),
  phoneNumber: Yup.string().required("Telefon numarası alanı zorunludur"),
  username: Yup.string().required("Kullanıcı adı alanı zorunludur"),
  mail: Yup.string()
    .email("Geçersiz e-posta formatı")
    .required("E-posta alanı zorunludur"),
  password: Yup.string()
    .required("Şifre alanı zorunludur")
    .matches(
      /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/,
      "En az 8 karakter, bir büyük harf, bir küçük harf, bir rakam ve bir özel karakter içermelidir"
    ),
  repeatPassword: Yup.string()
    .required("Şifre onayı zorunludur")
    .oneOf([Yup.ref("password"), null], "Şifreler eşleşmelidir"),
});

function Register(props) {
  const [responseMessage, setResponseMessage] = useState("");

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(validationSchema),
  });

  /***
   * @param {React.FormEvent} e
   *
   */
  const onSubmit = async (e) => {
    e.preventDefault();
    const data = new FormData(e.currentTarget);
    try {
      const response = await axios.post("http://localhost:8080/users", data);
      console.log(response.data);

      setResponseMessage(
        response.data
          ? "Kayıt başarılı, giriş yapabilirsiniz."
          : "Kayıt başarısız"
      );
      setTimeout(() => {
        window.location.replace("/login");
      }, 2000);
    } catch (error) {
      console.log(error);
      setResponseMessage(`Hata: ${error.response.data}`);
    }
  };

  return (
    <div>
      <Navbar />
      <Container>
        <Row className="justify-content-md-center">
          <Col md={6}>
            <h1 className="text-light text-center mb-4">Kayıt Ol</h1>
            <Form onSubmit={onSubmit}>
              <Form.Group className="mb-3" controlId="formBasicFirstname">
                <Form.Label className="text-light">Ad</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Adınızı girin"
                  {...register("firstname")}
                />
                <p className="text-danger">{errors.firstname?.message}</p>
              </Form.Group>
              <Form.Group className="mb-3" controlId="formBasicLastname">
                <Form.Label className="text-light">Soyad</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Soyadınızı girin"
                  {...register("lastname")}
                />
                <p className="text-danger">{errors.lastname?.message}</p>
              </Form.Group>
              <Form.Group className="mb-3" controlId="formBasicPhoneNumber">
                <Form.Label className="text-light">Telefon Numarası</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Telefon numaranızı girin"
                  {...register("phoneNumber")}
                />
                <p className="text-danger">{errors.phoneNumber?.message}</p>
              </Form.Group>
              <Form.Group className="mb-3" controlId="formBasicUsername">
                <Form.Label className="text-light">Kullanıcı Adı</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Kullanıcı adınızı girin"
                  {...register("username")}
                />
                <p className="text-danger">{errors.username?.message}</p>
              </Form.Group>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label className="text-light">E-posta Adresi</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="E-posta adresinizi girin"
                  {...register("mail")}
                />
                <p className="text-danger">{errors.email?.message}</p>
              </Form.Group>
              <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label className="text-light">Şifre</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Şifrenizi girin"
                  {...register("password")}
                />
                <p className="text-danger">{errors.password?.message}</p>
              </Form.Group>
              <Form.Group className="mb-3" controlId="formBasicRepeatPassword">
                <Form.Label className="text-light">Şifre Tekrarı</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Şifrenizi tekrar girin"
                  {...register("repeatPassword")}
                />
                <p className="text-danger">{errors.repeatPassword?.message}</p>
              </Form.Group>
              <Button variant="danger" type="submit" className="w-100">
                Kayıt Ol
              </Button>
            </Form>
            {responseMessage && (
              <h2 className="text-light mt-2 text-center">{responseMessage}</h2>
            )}
            <p className="text-light mt-3 text-center">
              Zaten hesabınız var mı?{" "}
              <Link to="/login" className="text-light">
                Giriş yapın.
              </Link>
            </p>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default Register;
