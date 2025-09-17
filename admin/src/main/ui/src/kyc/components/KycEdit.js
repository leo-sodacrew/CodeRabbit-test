import React from 'react';
import {Edit, SelectInput, SimpleForm, TextInput} from 'react-admin';

const KycEdit = () => {
  return (
      <Edit>
        <SimpleForm>
          <TextInput source="id" disabled/>
          <TextInput source="userId" disabled/>
          <SelectInput
              source="status"
              choices={[
                {id: 'PENDING', name: '대기중'},
                {id: 'APPROVED', name: '승인'},
                {id: 'REJECTED', name: '거절'}
              ]}
          />
        </SimpleForm>
      </Edit>
  );
};

export default KycEdit; 