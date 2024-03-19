import React from "react";
import { Button } from "react-bootstrap";

export default function LoginOrRegister() {
  return (
    <div>
      <div className="d-inline" style={{ marginRight: "30px" }}>
        <Button variant="light" size="md">
          Giriş Yap
        </Button>{" "}
      </div>

      <div className="d-inline" style={{ marginLeft: "30px" }}>
        <Button variant="light" size="md">
          Kayıt ol
        </Button>{" "}
      </div>
    </div>
  );
}
