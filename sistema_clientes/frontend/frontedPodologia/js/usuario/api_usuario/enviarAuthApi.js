import { BASE_URL } from "../../config/configuracion.js";

const URL_API = `${BASE_URL}/api/auth/login`;

const d = document;

export async function enviarAuthApi(datosAuth) {
  try {
    if (!datosAuth.email || !datosAuth.password) {
      console.log("valor usuario: ", datosAuth.email);
      console.log("valor del password: ", datosAuth.password);
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
    window.location.href = "/html/dashboard.html";
  } catch (error) {
    console.error("❌ Error al hacer login:", error);
    alert("Credenciales inválidas o error de conexión.");
  }
}
