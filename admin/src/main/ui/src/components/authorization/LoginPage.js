import React from 'react';
import {Avatar, Box, Button, Card, CardActions} from '@mui/material';
import LockIcon from '@mui/icons-material/Lock';

const LoginPage = (host) => {
  const handleLogin = () => {
    window.location.href = `${host}/oauth2/authorization/google`;
  };

  return (
      <Box sx={{
        display: 'flex',
        flexDirection: 'column',
        minHeight: '100vh',
        alignItems: 'center',
        justifyContent: 'flex-start',
        background: 'linear-gradient(175.97deg, #46237F 0.77%, #371A66 28.88%, #27104C 62.91%, #230E45 72.28%, #2F1559 99.41%)',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
      }}>
        <Card sx={{
          minWidth: 300,
          marginTop: '6em',
          backgroundColor: 'rgba(255, 255, 255, 0.9)',
        }}>
          <Box sx={{
            margin: '1em',
            display: 'flex',
            justifyContent: 'center',
          }}>
            <Avatar sx={{
              bgcolor: '#46237F'
            }}>
              <LockIcon/>
            </Avatar>
          </Box>
          <Box sx={{
            marginTop: '1em',
            marginBottom: '1em',
            display: 'flex',
            justifyContent: 'center',
            color: theme => theme.palette.grey[700],
          }}>
            B2B Admin
          </Box>
          <CardActions sx={{
            padding: '0 1em 1em 1em',
          }}>
            <Button
                variant="contained"
                type="submit"
                color="primary"
                fullWidth
                onClick={handleLogin}
            >
              Login with Google
            </Button>
          </CardActions>
        </Card>
      </Box>
  );
};

export default LoginPage; 