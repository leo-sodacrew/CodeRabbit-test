import React, {useEffect} from "react";
import {useAuthProvider} from "react-admin";
import {useNavigate} from "react-router-dom";
import {setRole} from "./Authorization";

export default () => {

  const authProvider = useAuthProvider();
  const navigate = useNavigate();

  useEffect(() => {
    const init = async () => {
      try {
        const user = await authProvider.getIdentity();
        setRole(user);

        const redirection = sessionStorage.getItem("redirection");
        if (redirection) {
          sessionStorage.removeItem("redirection");
          navigate(redirection);
        } else {
          navigate("/");
        }
      } catch (error) {
        navigate("/login");
      }
    };

    init();
  }, [authProvider, navigate]);

  return <p>Please wait...</p>;
};