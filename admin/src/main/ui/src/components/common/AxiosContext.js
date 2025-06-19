import Axios from 'axios';
import Qs from 'qs';
import {HttpError} from 'react-admin';

const axios = host => {
  const axios = Axios.create({
    baseURL: host,
    paramsSerializer: params => Qs.stringify(params, {allowDots: true, arrayFormat: 'repeat'}),
  });

  axios.interceptors.response.use(
      response => response,
      error => {
        if (error.response && error.response.data && error.response.data.location) {
          window.location = error.response.data.location;
        }
        return Promise.reject(new HttpError(error.response.data.message, error.response.status, error.response));
      }
  );

  return axios;
};

export default axios;