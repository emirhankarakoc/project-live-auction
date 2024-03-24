import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import Starter from "./components/starter_page/Starter";
import Auctions from "./components/auctions_page/Auctions";
import Auction from "./components/auctions_page/Auction";
import Login from "./components/general/login/Login";
import Register from "./components/general/login/Register";
import Profile from "./components/user_page/Profile";
import About from "./components/static_pages/About";
import Rules from "./components/static_pages/Rules";
import CreateAuction from "./components/general/CreateAuction";
import Products_manager from "./components/manager_page/Products_manager";
import Auctions_manager from "./components/manager_page/Auctions_manager";
import FilteredAuctions from "./components/auctions_page/FilteredAuctions";
import ProductOffersList from "./components/general/ProductOffersList";

function App() {
  return (
    <div className="App bg-dark" style={{ minHeight: "100dvh" }}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Starter />} />
          <Route path="/auctions" element={<Auctions />} />
          <Route
            path="/auctions/search/:keyword"
            element={<FilteredAuctions />}
          />
          <Route path="/auctions/search/" element={<FilteredAuctions />} />
          <Route
            path="/auction/:auctionId/offers/"
            element={<ProductOffersList />}
          />
          <Route path="/auction/:id" element={<Auction />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/user/:username" element={<Profile />} />
          <Route path="/about" element={<About />} />
          <Route path="/admin/products" element={<Products_manager />} />
          <Route path="/admin/auctions" element={<Auctions_manager />} />

          <Route path="/rules" element={<Rules />} />
          <Route path="/create/:productId" element={<CreateAuction />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
