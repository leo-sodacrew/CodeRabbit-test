import React, {createContext} from 'react';
import {Admin, CustomRoutes} from 'react-admin';
import {Route} from "react-router-dom";
import DataProvider from './components/common/DataProvider';
import LoginPage from './components/authorization/LoginPage';
import axios from './components/common/AxiosContext';
import LogoutButton from './components/authorization/LogoutButton';
import OAuth2RedirectHandler from "./components/authorization/LoginSuccessHandler";
import AdminLayout from './components/layout/AdminLayout';
import AuthProvider from "./components/authorization/AuthProvider";
import BizResources from './components/common/Resources';

export const AxiosContext = createContext();

const ApiHost = process.env.REACT_APP_API_HOST
    ? process.env.REACT_APP_API_HOST
    : '';

const App = () => {
  const Axios = axios('/');

  return (
      <AxiosContext.Provider value={Axios}>
        <Admin
            authProvider={AuthProvider(Axios)}
            dataProvider={DataProvider(Axios)}
            loginPage={() => LoginPage(ApiHost)}
            logoutButton={() => LogoutButton(ApiHost)}
            layout={AdminLayout}
        >
          {BizResources}
          <CustomRoutes>
            <Route path="/login-success" element={<OAuth2RedirectHandler/>}/>
          </CustomRoutes>
        </Admin>
      </AxiosContext.Provider>
  );
};

export default App;
