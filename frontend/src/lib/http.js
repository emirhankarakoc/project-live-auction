import axios from "axios";

const HOSTLINK = "https://auction-backend.6xzqep.easypanel.host";
const LOCALHOST = "http://localhost:8080";
export const APIURL = HOSTLINK;

const SOCKET_HOST = "wss://ws.auction-backend.6xzqep.easypanel.host/";
const SOCKET_LOCAL = "ws://localhost:8085";
export const SOCKETURL = SOCKET_HOST;

export const http = axios.create({
  baseURL: APIURL,
  data: {
    token: localStorage.getItem("userToken"),
  },
});

export const httpError = (error) => {
  let errorMessage = error.message;

  if (error.response) {
    errorMessage = error.response.data;
  }
  return errorMessage;
};
