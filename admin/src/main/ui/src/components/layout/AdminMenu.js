import {Menu} from 'react-admin';
import React from 'react';

const AdminMenu = () => (
    <Menu>
          <Menu.ResourceItem name="biz/kyc"/>
          <Menu.ResourceItem name="biz/order"/>
          <Menu.ResourceItem name="biz/balance"/>
          <Menu.ResourceItem name="biz/product"/>
          <Menu.ResourceItem name="biz/brand"/>
          <Menu.ResourceItem name="biz/user"/>
          <Menu.ResourceItem name="biz/policy"/>
    </Menu>
);

export default AdminMenu;
