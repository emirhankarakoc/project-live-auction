import axios from "axios";

const HOSTLINK = "https://backend.bdmstf.easypanel.host";
const LOCALHOST = "http://localhost:8080";
export const APIURL = HOSTLINK;

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
