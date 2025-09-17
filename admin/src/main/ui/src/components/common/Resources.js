import React from 'react';
import {Resource} from 'react-admin';
import PersonIcon from '@mui/icons-material/Person';
import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import InventoryIcon from '@mui/icons-material/Inventory';
import PeopleIcon from '@mui/icons-material/People';
import PolicyIcon from '@mui/icons-material/Policy';
import KycList from '../../kyc/components/KycList';
import OrderList from '../../order/components';
import BalanceList from '../../balance/components';
import ProductList from '../../product/components';
import UserList from '../../user/components';
import PolicyList from '../../policy/components';

const BizResources = [
  <Resource
      name="biz/order"
      key="order"
      options={{label: 'Order'}}
      icon={ShoppingCartIcon}
      list={OrderList}
  />,
  <Resource
      name="biz/kyc"
      key="kyc"
      options={{label: 'KYC'}}
      list={KycList}
      icon={PersonIcon}
  />,
  <Resource
      name="biz/balance"
      key="balance"
      options={{label: 'Balance'}}
      list={BalanceList}
      icon={AccountBalanceWalletIcon}
  />,
  <Resource
      name="biz/product"
      key="product"
      options={{label: 'Product'}}
      list={ProductList}
      icon={InventoryIcon}
  />,
  <Resource
      name="biz/user"
      key="user"
      options={{label: 'User'}}
      list={UserList}
      icon={PeopleIcon}
  />,
  <Resource
      name="biz/policy"
      key="policy"
      options={{label: 'Policy'}}
      list={PolicyList}
      icon={PolicyIcon}
  />
];

export default BizResources; 