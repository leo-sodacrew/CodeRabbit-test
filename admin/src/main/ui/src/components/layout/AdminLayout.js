import {AppLocationContext, Breadcrumb, SolarLayout} from '@react-admin/ra-navigation';
import React from 'react';
import AdminMenu from './AdminMenu';

const AdminLayout = ({children}) => (
    <AppLocationContext>
      <SolarLayout
          menu={AdminMenu}
      >
        <Breadcrumb/>
        {children}
      </SolarLayout>
    </AppLocationContext>
);

export default AdminLayout;