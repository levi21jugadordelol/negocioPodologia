import { getDataFromCita } from "./getDatafromCita.js";
import { abrirModalDetallesServicio } from "./citas/metodos/abrirModalDetallesServicio.js";

export const infoCita = (btnSelector) => {
  const d = document;
  d.addEventListener("click", async (e) => {
    const btn = e.target.closest(btnSelector);
    if (!btn) return;

    const fila = btn.closest("tr");
    const selects = fila.querySelectorAll("select");
    const selectEstado = selects[1];
    if (!selectEstado) {
      console.warn("⚠️ No se encontró el select de estado.");
      return;
    }

    const estadoSeleccionadoTexto = selectEstado.selectedOptions[0].text;
    console.log("✅ Estado visible seleccionado:", estadoSeleccionadoTexto);
    console.log("🧪 Valor del option:", selectEstado.value);

    if (estadoSeleccionadoTexto.toUpperCase() === "ATENDIDA") {
      // 🕒 Primero guarda los datos de la cita
      await getDataFromCita();

      // 🆙 Luego accede al dataset actualizado
      const currentCitaId = btn?.dataset.idCita;
      const servicioId = btn?.dataset.servicioId;
      const servicioNombre = btn?.dataset.servicioNombre;

      console.log("🆔 ID de cita actualizado: ", currentCitaId);
      console.log("💼 ID de servicio actualizado: ", servicioId);
      console.log("📛 Nombre de servicio actualizado: ", servicioNombre);

      // ✅ Validaciones antes de continuar
      if (!currentCitaId || isNaN(Number(currentCitaId))) {
        alert("❌ ID de cita no es válido");
        return;
      }
      if (!servicioId || isNaN(Number(servicioId))) {
        alert("❌ ID del servicio no es válido");
        return;
      }

      const citaIdInt = Number(currentCitaId);
      const servicioIdInt = Number(servicioId);

      // 🧼 Limpia campos del modal antes de mostrarlo
      d.getElementById("lista_productos").innerHTML = "";
      d.getElementById("modal_servicio_id").value = servicioId;
      d.getElementById("modal_servicio_nombre").value = servicioNombre;

      // 📢 Ahora sí muestra el modal
      abrirModalDetallesServicio(citaIdInt, servicioIdInt, servicioNombre);
    } else {
      alert(`❌ Estado actual: ${estadoSeleccionadoTexto}`);
    }
  });
};
