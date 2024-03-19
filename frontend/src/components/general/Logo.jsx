import React from "react";
import { Image } from "react-bootstrap";

export default function Logo() {
  return (
    <div className="mt-3">
      <a href="/">
        <Image
          src="https://m.brickshop.eu/components/com_virtuemart/shop_image/product/LEGO_Sticker_12x_5a7_5a7330349b7d9.jpg"
          roundedCircle
          style={{ width: "50px", height: "50px" }}
        />
      </a>
    </div>
  );
}
