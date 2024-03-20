import React from "react";
import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import Login from "./Login";

export default function LoginOrRegister() {
  return (
    <div>
      <div className="d-inline" style={{ marginRight: "30px" }}>
        <Link to={"/login"}>
          <Button variant="light" size="md">
            Giriş Yap
          </Button>
        </Link>
      </div>

      <div className="d-inline" style={{ marginLeft: "30px" }}>
        <Link to={"/register"}>
          {" "}
          <Button variant="light" size="md">
            Kayıt ol
          </Button>
        </Link>
      </div>
    </div>
  );
}
