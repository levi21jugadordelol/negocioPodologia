import { BASE_URL } from "../../config/configuracion.js";
import { comenzarTollenarClientPorDiaApi } from "../../cliente/api_cliente/comenzarTollenarClientPorDia.js";
import { capturandoNombreUsuario } from "../api_usuario/tomarNombreUsuarioApi.js";

const URL_API = `${BASE_URL}/api/auth/login`;
const URL_API_USUARIO = `${BASE_URL}/api/user`;

const d = document;

export async function enviarAuthApi(datosAuth) {
  try {
    if (!datosAuth.email || !datosAuth.password) {
      console.log("valor email: ", datosAuth.email);
      console.log("valor del password: ", datosAuth.password);
      // console.log("valor del nombre de usuario: ", datosAuth.usuario);
      alert("Por favor, llena todos los campos.");
      return;
    }
    const respuesta = await fetch(URL_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(datosAuth),
    });
    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }
    const token = await respuesta.text();
    console.log("✅ Token del backend:", token);
    sessionStorage.setItem("token", token);

    // 2. Obtener datos del usuario con el token
    const respuestaUsuario = await fetch(URL_API_USUARIO, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!respuestaUsuario.ok) {
      throw new Error("❌ No se pudo obtener el nombre del usuario.");
    }

    const usuario = await respuestaUsuario.json();
    console.log("✅ Usuario:", usuario);

    // ✅ Aquí lo guardas en sessionStorage:
    sessionStorage.setItem("nombreUsuario", usuario.nombre);

    setTimeout(() => {
      window.location.href = "/html/dashboard.html";
    }, 5000); // 5000 milisegundos = 5 segundos
  } catch (error) {
    console.error("❌ Error al hacer login:", error);
    alert("Credenciales inválidas o error de conexión.");
  }
}
