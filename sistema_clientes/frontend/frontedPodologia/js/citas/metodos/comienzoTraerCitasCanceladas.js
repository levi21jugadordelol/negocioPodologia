import { citaCanceladaApi } from "../api_cita/citaCanceladaApi.js";
import { renderizarTablaCitaCanceladas } from "./renderizarTablaCitaCanceladas.js";

const d = document;

export const comienzoTraerCitasCanceladas = async (estado = "CANCELADA") => {
  const citasCanceladas = await citaCanceladaApi(estado);

  if (!citasCanceladas || citasCanceladas.length === 0) {
    await Swal.fire({
      title: `Sin citas ${estado.toLowerCase()}`,
      text: `No se encontraron citas con estado ${estado}.`,
      icon: "info",
      timer: 2000,
      showConfirmButton: false,
    });
    return;
  }
  renderizarTablaCitaCanceladas(citasCanceladas);
};
