import React, { useState, useEffect } from "react";
import Navbar from "../general/Navbar";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

export default function Starter() {
  return (
    <div>
      <Navbar name="Muzayede Evim" />
      <Link to="/auctions" className="text-white mx-2 ">
        Auction sayfasi
      </Link>
    </div>
  );
}
