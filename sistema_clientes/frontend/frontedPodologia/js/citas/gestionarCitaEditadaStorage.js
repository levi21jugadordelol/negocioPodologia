//import { citaStorage } from "./CitaStorage.js";
import { citaStorage } from "../localStorage/CitaStorage.js";

export function gestionarCitaEditadaStorage(id_cita, citaBackend) {
  console.log("el valor de cita backend como parametro es: ", citaBackend);

  console.log("🟢 Entrando a gestionarCitaEditadaStorage con:");
  console.log("👉 ID cita:", id_cita, typeof id_cita);
  console.log("👉 citaBackend:", citaBackend);
  console.log("👉 clienteId tipo:", typeof citaBackend.clienteId);

  const formulario = document.getElementById("form_cita_edit");
  if (formulario) {
    formulario.dataset.id = id_cita;
  }

  const citaNormalizado = {
    id_cita: Number(id_cita),
    idCliente: Number(citaBackend.clienteId),
    servicio: Number(citaBackend.servicioId),
    fecha: citaBackend.fechaCita,
    estado: String(citaBackend.estadoCita),
    observaciones: citaBackend.observaciones ?? "",
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
