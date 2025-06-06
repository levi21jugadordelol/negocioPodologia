//import { citaStorage } from "./CitaStorage.js";
import { citaStorage } from "../localStorage/CitaStorage.js";

export function gestionarCitaEditadaStorage(id_cita, citaBackend) {
  const formulario = document.getElementById("form_cita_edit");
  if (formulario) {
    formulario.dataset.id = id_cita;
  }

  const citaNormalizado = {
    id_cita, // ✅ lo incluyes para que se pueda identificar luego
    idCliente: citaBackend.idCliente,
    servicio: citaBackend.servicio,
    estado: citaBackend.estado,
    observaciones: citaBackend.observaciones,
  };

  console.log("🟢 Guardando en localStorage:", citaNormalizado);
  citaStorage.guardar(citaNormalizado);

  const todasCitas = citaStorage.obtenerTodos();
  const citaEncontrado = todasCitas.find((c) => c.id_cita === id_cita);

  if (citaEncontrado) {
    console.log("✅ Cita editado encontrado en localStorage:", citaEncontrado);
  } else {
    console.warn("❌ Cita editado NO encontrado en localStorage.");
  }

  console.log("📦 Todas las citas en localStorage:", todasCitas);
}
