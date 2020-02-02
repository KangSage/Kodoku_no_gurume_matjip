import axios from 'axios';

const { API_SERVER_HOST } = process.env;

console.log(API_SERVER_HOST);

export const ApiCilent = axios.create({
  baseURL: API_SERVER_HOST,
  xsrfCookieName: 'XSRF-TOKEN',
  xsrfHeaderName: 'X-XSRF-TOKEN',
});

const login1 = (form: FormData) =>
  ApiCilent.post('/j_spring_security_check', form);

export default login1;
