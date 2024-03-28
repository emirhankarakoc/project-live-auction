import axios from "axios";

const HOSTLINK = "https://backend.kgzkbi.easypanel.host";
const LOCALHOST = "http://192.168.131.212:8080";
export const APIURL = LOCALHOST;

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
