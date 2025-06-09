import { enviarAuthApi } from "../api_usuario/enviarAuthApi.js";
import { capturandoValoresLogin } from "./metodos_auth/capturandoValoresLogin.js";

const d = document;
export const event_login = (button_login) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(button_login)) {
      //  alert("funciona el buton de login");
      const datosAuth = capturandoValoresLogin();
      enviarAuthApi(datosAuth);
    }
  });
};
