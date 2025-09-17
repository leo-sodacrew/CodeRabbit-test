import React from 'react';
import {DateField, FunctionField, ReferenceField, Show, SimpleShowLayout, TextField} from 'react-admin';

const KycShow = () => {
  return (
      <Show>
        <SimpleShowLayout>
          <TextField source="id"/>
          <ReferenceField source="accountId" reference="biz/account" link="show">
            <FunctionField render={record => `[${record.id}] ${record.email}`}/>
          </ReferenceField>
          <TextField source="status"/>
          <DateField source="createdAt"/>
          <DateField source="updatedAt"/>
        </SimpleShowLayout>
      </Show>
  );
};

export default KycShow;