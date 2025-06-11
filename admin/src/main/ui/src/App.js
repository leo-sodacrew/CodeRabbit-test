import React, {createContext} from 'react';
import {Admin, CustomRoutes, Resource} from 'react-admin';
import {Route} from "react-router-dom";
import DataProvider from './components/common/DataProvider';
import LoginPage from './components/authorization/LoginPage';
import axios from './components/common/AxiosContext';
import LogoutButton from './components/authorization/LogoutButton';
import OAuth2RedirectHandler from "./components/authorization/LoginSuccessHandler";
import AdminLayout from './components/layout/AdminLayout';
import AuthProvider from "./components/authorization/AuthProvider";
import KycList from './kyc/components';
import BalanceList from './balance/components';
import OrderList from './order/components';
import ProductList from './product/components';
import UserList from './user/components';
import PolicyList from './policy/components';
import PersonIcon from '@mui/icons-material/Person';
import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import InventoryIcon from '@mui/icons-material/Inventory';
import PeopleIcon from '@mui/icons-material/People';
import PolicyIcon from '@mui/icons-material/Policy';

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
          <Resource name="biz/order" options={{label: 'Order'}} list={OrderList} icon={ShoppingCartIcon}/>
          <Resource name="biz/kyc" options={{label: 'KYC'}} list={KycList} icon={PersonIcon}/>
          <Resource name="biz/balance" options={{label: 'Balance'}} list={BalanceList} icon={AccountBalanceWalletIcon}/>
          <Resource name="biz/product" options={{label: 'Product'}} list={ProductList} icon={InventoryIcon}/>
          <Resource name="biz/user" options={{label: 'User'}} list={UserList} icon={PeopleIcon}/>
          <Resource name="biz/policy" label="Policy" list={PolicyList} icon={PolicyIcon}/>
          <CustomRoutes>
            <Route path="/login-success" element={<OAuth2RedirectHandler/>}/>
          </CustomRoutes>
        </Admin>
      </AxiosContext.Provider>
  );
};

export default App;
