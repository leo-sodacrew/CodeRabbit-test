import {Layout} from 'react-admin';
import React from 'react';
import AdminMenu from './AdminMenu';

const AdminLayout = ({children}) => (
    <Layout
        menu={AdminMenu}
        appBarAlwaysOn
    >
      {children}
    </Layout>
);

export default AdminLayout;