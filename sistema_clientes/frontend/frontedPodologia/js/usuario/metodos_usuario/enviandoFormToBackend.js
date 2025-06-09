import { Usuario } from "../../clases/usuario.js";
import { obteniendoDatosFormUsuario } from "./obteniendoDatosFormUsuario.js";
import { enviarUsuarioApi } from "../api_usuario/enviarUsuarioApi.js";

const d = document;

export const enviandoFormToBackend = async () => {
  const datos = obteniendoDatosFormUsuario();

  console.log(
    "los datos obtenidos del form en 'enviandoFormToBackend' es: ",
    datos
  );

  const datosForm = new Usuario(
    datos.nombreUsuario,
    datos.apellidoUsuario,
    datos.email,
    datos.contrasenia,
    datos.contraseniaConfirmada
  );

  console.log(
    "los datos del formulario pasado por la clase usuario es: ",
    datosForm
  );

  const datosFormBackend = datosForm.toBackendJson();

  console.log("los datos para ir al backend son : ", datosFormBackend);

  const respuesta = await enviarUsuarioApi(datosFormBackend);
  if (respuesta.exito) {
    alert("✅ Usuario registrado correctamente");
    // Redireccionamos a otra página (por ejemplo, login)
    window.location.href = "/html/login.html";
  } else {
    alert("❌ No se pudo registrar el usuario: " + respuesta.mensaje);
  }
};
