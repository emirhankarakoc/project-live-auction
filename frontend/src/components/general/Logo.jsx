import React from "react";
import { Image } from "react-bootstrap";
import mainLogo from'../images/logo.png';

export default function Logo() {
  return (
    <div className="mt-3">
      <a href="/">
        <Image
          src={mainLogo}
          style={{ width: "200px", height: "64px" }}
        />
      </a>
    </div>
  );
}
