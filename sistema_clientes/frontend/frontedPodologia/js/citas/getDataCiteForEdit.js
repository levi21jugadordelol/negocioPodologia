// aca capturamos la info de cita luego de que dieramos click en edit

import { Cita } from "../clases/cita.js";
import { citaStorage } from "../localStorage/CitaStorage.js";
import { enviarEditCitaApi } from "./api_cita/enviarEditCitaApi.js";
import { getDatosDeFila } from "./getDatosDeFila.js";

const d = document;
export const getDataCiteDorEdit = async (fila, id_cita) => {
  try {
    console.log("==============info de cita para el edit==============");

    // Suponiendo que este método se llama desde un click en el botón editar
    const datos = getDatosDeFila(fila);

    console.log(
      "el numero de id cliente desde 'getDataCiteDorEdit' es: ",
      datos.idCliente
    );

    console.log(
      "el tipo de dato que es el id cliente es : ",
      typeof datos.clienteId
    );

    const cita = new Cita(
      datos.idCliente,
      datos.servicio,
      datos.fecha,
      datos.estado,
      datos.observaciones,
      []
    );

    console.log("cita editada:", cita);

    const citaBackend = cita.toBackendJson();

    console.log("la cita editado es : ", citaBackend);

    console.log("📌 Tipo de clienteId:", typeof citaBackend.clienteId);
    console.log("📌 Tipo de servicioId:", typeof citaBackend.servicioId);
    console.log("📌 citaBackend completo:", citaBackend);

    await enviarEditCitaApi(id_cita, citaBackend);
    const citasActualizadas = citaStorage.obtenerTodos();
    console.log("📦 Citas actualizadas localStorage:", citasActualizadas);
  } catch (error) {
    console.warn("el error es: ", error.message);
  }
};
