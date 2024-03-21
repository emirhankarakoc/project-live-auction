import React from "react";
import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";

export default function LoginOrRegister() {
  return (
    <div>
      <div className="d-inline" style={{ marginRight: "10px" }}>
        <Link to={"/login"}>
          <Button variant="secondary" size="md">
            Giriş Yap
          </Button>
        </Link>
      </div>

      <div className="d-inline" style={{ marginLeft: "10px" }}>
        <Link to={"/register"}>
          <Button variant="warning" size="md">
            Kayıt ol
          </Button>
        </Link>
      </div>
    </div>
  );
}
