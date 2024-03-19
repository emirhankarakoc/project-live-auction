import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import Starter from "./components/starter_page/Starter";
import Auctions from "./components/auctions_page/Auctions";
import Auction from "./components/auctions_page/Auction";

function App() {
  return (
    <div className="App bg-dark">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Starter />} />
          <Route path="/auctions" element={<Auctions />} />
          <Route path="/product/:id" element={<Auction />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
