// aca capturamos la info de cita luego de que dieramos click en edit

import { Cita } from "../clases/cita.js";
import { citaStorage } from "../localStorage/CitaStorage.js";
import { enviarEditCitaApi } from "./api_cita/enviarEditCitaApi.js";
import { getDatosDeFila } from "./getDatosDeFila.js";

const d = document;
export const getDataCiteDorEdit = async (fila, id_cita) => {
  try {
    console.log("==============info de cita para el edit==============");
    const $form_cita = d.getElementById("tabla-citas");

    console.log("la captura de la tabla citas es: ", $form_cita);

    // Suponiendo que este método se llama desde un click en el botón editar
    const datos = getDatosDeFila(fila);

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

    await enviarEditCitaApi(id_cita, citaBackend);
    const citasActualizadas = citaStorage.obtenerTodos();
    console.log("📦 Citas actualizadas localStorage:", citasActualizadas);
  } catch (error) {
    console.warn("el error es: ", error.message);
  }
  $form.reset();
};
