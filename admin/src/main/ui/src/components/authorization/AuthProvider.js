import {getRole, removeRole} from "./Authorization";
import {getPermissionsFromRoles} from "@react-admin/ra-rbac";

const AuthProvider = (axios) => {
  return {
    login: () => {

    },

    logout: () => {
      removeRole();
      return Promise.resolve()
    },

    checkAuth: () => Promise.resolve(),

    checkError: (error) => {
      if (error.status !== 401 && error.status !== 403) {
        return Promise.reject();
      }

      const currentPage = window.location.pathname;
      if (currentPage !== '/login' && currentPage !== '/') {
        sessionStorage.setItem("redirection", window.location.pathname);
      }
      return Promise.reject();
    },

    getIdentity: async () => {
      try {
        const response = await axios.get("/auth/user");
        return response.data;
      } catch (error) {
        throw error;
      }
    },

    getPermissions: () =>
        Promise.resolve(
            getPermissionsFromRoles({
              // static role definitions
              roleDefinitions: {
                admin: [{action: "*", resource: "*"}],
                user: [
                  {action: "*", resource: "*"},
                  {
                    type: "deny",
                    action: ["create", "edit"],
                    resource: "gift/exchange-fee-rules"
                  },
                ],
              },
              // permissions for the current user
              userPermissions: [],
              // roles of the current user
              userRoles: [getRole()],
            })
        ),
  };
};

export default AuthProvider;