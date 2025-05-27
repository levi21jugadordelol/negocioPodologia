import { Cita } from "./clases/cita.js";
import { enviarCitaApi } from "./conexion/enviarCitaApi.js";

export const datosCita = [];

export const getDataFromCita = async () => {
  //alert("hola desde el metodo getDataFromCita");
  const d = document;
  const fila = d.querySelector("#tabla-citas tbody tr");
  if (!fila) return null;

  const inputFecha = fila.querySelector(".fecha-cita");
  const selectServicio = fila.querySelector("td:nth-child(3) select");
  const selectTipo = fila.querySelector("td:nth-child(4) select");
  const selectEstado = fila.querySelector("td:nth-child(5) select");
  const inputObs = fila.querySelector("td:nth-child(6) input");

  const tdCliente = fila.querySelector("td:nth-child(2)");
  const idCliente = parseInt(tdCliente?.textContent.trim(), 10);

  // === Consolas de verificación ===
  console.log("📅 Fecha ingresada:", inputFecha?.value);
  console.log("👤 ID del cliente:", idCliente);
  console.log("💼 Servicio seleccionado:", selectServicio?.value);
  console.log("📌 Tipo de cita:", selectTipo?.value);
  console.log("📄 Estado de cita:", selectEstado?.value);
  console.log("📝 Observaciones:", inputObs?.value);

  try {
    const cita = new Cita(
      idCliente,
      selectTipo?.value || "",
      inputFecha?.value || "",
      selectEstado?.value || "",
      inputObs?.value || ""
      // detalles por defecto es []
    );

    datosCita.push(cita);
    console.log("✅ Cita creada correctamente:", cita);

    const respuesta = await enviarCitaApi(cita.toBackendJson());
  } catch (e) {
    alert("❌ Error al crear cita: " + e.message);
    console.error("Detalle del error:", e);
  }
};
