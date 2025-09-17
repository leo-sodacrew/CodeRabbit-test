import React, {useState} from 'react';
import {
  AutocompleteInput,
  Button,
  Datagrid,
  DateField,
  FileField,
  Filter,
  FunctionField,
  List,
  ReferenceField,
  ReferenceInput,
  SelectInput,
  TextField,
  useDataProvider,
  useListController,
  useNotify,
  useRecordContext,
  useRefresh,
  useResourceContext,
  WrapperField
} from 'react-admin';

const KycFilter = (props) => (
    <Filter {...props} export={false}>
      <ReferenceInput
          label="Account"
          source="accountId"
          reference="biz/account"
          perPage={20}
          alwaysOn
      >
        <AutocompleteInput
            optionText={(account) => `[${account.id}] ${account.email}`}
            filterToQuery={(text) => ({email: text})}
            allowEmpty
            fullWidth
            sx={{"min-width": 260}}
        />
      </ReferenceInput>
      <SelectInput
          source="status"
          label="Status"
          choices={[
            {id: 'READY', name: 'Ready'},
            {id: 'INPROGRESS', name: 'In Progress'},
            {id: 'COMPLETED', name: 'Completed'}
          ]}
          alwaysOn
      />
    </Filter>
);

const KycConfirmButton = () => {
  const resource = useResourceContext();
  const record = useRecordContext();
  const notify = useNotify()
  const dataProvider = useDataProvider()
  const [loading, setLoading] = useState(false)
  const refresh = useRefresh();

  const handleClick = (e) => {
    e.stopPropagation()
    dataProvider.update(`${resource}/confirm`, {
      id: record.id,
      data: {
        status: 'COMPLETED'
      }
    })
    .then(() => {
      notify('KYC has been successfully approved', 'success');
      refresh();
    })
    .catch((error) => {
      if (error.status && error.status >= 400) {
        notify('Send failed : ' + error.message, 'warning', {}, false, 10000)
      }
    })
    .finally(() => setLoading(false))
  }

  return (
      <Button
          label="Confirm"
          color="secondary"
          variant="contained"
          onClick={handleClick}
          disabled={record.status !== 'INPROGRESS'}
      />
  )
}

const KycList = () => {
  const record = useListController()

  return (
      <List filters={<KycFilter/>} exporter={false}>
        <Datagrid>
          <ReferenceField
              label="Account"
              link="show"
              source="accountId"
              reference="biz/account"
              sortable={false}
          >
            <FunctionField render={record => `[${record.id}] ${record.email}`}/>
          </ReferenceField>
          <TextField source="companyName" label="Company Name"/>
          <TextField source="groupType" label="Group type"/>
          <TextField source="purpose" label="Purpose"/>
          <TextField source="expectedSpendAmount" label="Expected Spend"/>
          <TextField source="spendType" label="Spend Type"/>
          <TextField source="status" label="Status" sortable={false}/>
          <FileField
              label="Submitted files"
              source="downloadLink"
              title="Download"
              target="_blank"
              download={false}
              sortable={false}
          />
          <WrapperField label="Actions">
            <KycConfirmButton/>
          </WrapperField>
          <DateField source="createdAt" label="Submitted At" showTime sortable={true}/>
        </Datagrid>
      </List>
  );
}

export default KycList;