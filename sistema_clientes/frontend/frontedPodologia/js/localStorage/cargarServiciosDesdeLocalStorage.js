import { BASE_URL } from "../config/configuracion.js";

import { servicioStorage } from "./servicioStorage.js";
//import {recuperarServiciosDesdeLocalStorage} from "../../js/"
import { recuperarServiciosDesdeLocalStorage } from "../servicios/metodos/recuperarServiciosDesdeLocalStorage.js";
import { Servicio } from "../clases/servicio.js";

export const cargarServiciosDesdeLocalStorage = async () => {
  try {
    console.log("🔄 Iniciando carga de servicios desde backend...");

    const respuesta = await fetch(`${BASE_URL}/servicio/listarServicios`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });

    console.log("📡 Respuesta recibida:", respuesta);

    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }

    const servicios = await respuesta.json();
    console.log("📦 servicios recibidos del backend:", servicios);

    servicioStorage.vaciarServicios();
    console.log("🧹 LocalStorage de servicios vaciado");

    servicioStorage.guardarTodosLosServicios(servicios);
    console.log("✅ Todos los productos fueron guardados en localStorage");

    //recuperarServiciosDesdeLocalStorage();
    recuperarServiciosDesdeLocalStorage();
    return servicios;
  } catch (error) {
    console.error("❌ Error al sincronizar servicios:", error.message);
    return [];
  }
};
