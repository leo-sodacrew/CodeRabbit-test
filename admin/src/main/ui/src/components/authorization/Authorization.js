const administrators = [
  "daniel.lee",
  "jake.kim",
  "michael.kim",
  "jenny.choi",
];

const isDev = () => {
  return window.location.hostname === 'localhost' || window.location.hostname === '';
}

export const setRole = (user) => {
  if (isDev()) {
    localStorage.setItem("role", "admin");
  } else {
    if (administrators.includes(user.name)) {
      localStorage.setItem("role", "admin");
    } else {
      localStorage.setItem("role", "user");
    }
  }
};

export const getRole = () => {
  return localStorage.getItem("role");
}

export const removeRole = () => {
  localStorage.removeItem('role');
}; 