import React from "react";
import Dropdown from "react-bootstrap/Dropdown";

export default function User() {
  function logOut() {
    localStorage.setItem("userToken", "");
    window.location.replace("/");
  }
  return (
    <div>
      <Dropdown>
        <Dropdown.Toggle variant="danger" id="dropdown-basic">
          Hesabim
        </Dropdown.Toggle>

        <Dropdown.Menu>
          <Dropdown.Item href="#/action-1">Action</Dropdown.Item>

          <Dropdown.Item onClick={logOut}>Cikis yap</Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </div>
  );
}
