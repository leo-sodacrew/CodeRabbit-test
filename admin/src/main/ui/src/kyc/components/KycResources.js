import React from 'react';
import {Resource} from 'react-admin';
import PersonIcon from '@mui/icons-material/Person';
import KycList from './KycList';
import KycEdit from './KycEdit';
import KycShow from './KycShow';

const KycResources = (
    <Resource
        name="biz/kyc"
        options={{label: 'KYC'}}
        list={KycList}
        show={KycShow}
        edit={KycEdit}
        icon={PersonIcon}
    />
);

export default KycResources; 