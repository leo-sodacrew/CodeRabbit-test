import React, {useContext} from 'react';
import {useLogout} from 'react-admin';
import Button from '@mui/material/Button';
import PowerSettingsNewIcon from '@mui/icons-material/PowerSettingsNew';
import {AxiosContext} from '../../App';

const LogoutButton = (host) => {
  const logout = useLogout();
  const axios = useContext(AxiosContext);

  const handleLogout = () => {
    axios.get(`${host}/logout`).finally(() => logout());
  };

  return (
      <Button onClick={handleLogout} startIcon={<PowerSettingsNewIcon/>}>
        Logout
      </Button>
  );
};

export default LogoutButton; 