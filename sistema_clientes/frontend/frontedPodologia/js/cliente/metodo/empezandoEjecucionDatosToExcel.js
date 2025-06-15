import { endPointExcel } from "../api_cliente/api_excel/endPointExcel.js";

const d = document;
export const empezandoEjecucionDatosToExcel = async () => {
  const datosToExcel = await endPointExcel();
  if (datosToExcel) {
    alert("✅ Archivo Excel descargado correctamente");
  }
};
