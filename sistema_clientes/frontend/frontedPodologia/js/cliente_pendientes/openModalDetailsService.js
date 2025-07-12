import { postDataDetails } from "./postDataDetails.js";
import { comboProductBeforeEvent } from "./ComboProductBeforeEvent.js";
import { fillComboProductFromBackend } from "../conexion/fillComboProductFromBackend.js";
import { getDataFromCita } from "../getDatafromCita.js";
//let currentCitaId = null;

const d = document;
export let productosUtilizados = [];

let currentCitaId = null;
let currentServicioId = null;
let currentServicioNombre = null;
let currentEstadoCita = null;

const abrirModalDetalles = async ({
  idCita,
  servicioId,
  servicioNombre,
  modal_details,
}) => {
  console.log("🔄 Llamando getDataFromCita...");
  await getDataFromCita();

  currentCitaId = idCita;
  currentServicioId = servicioId;
  currentServicioNombre = servicioNombre;

  if (!currentCitaId || isNaN(Number(currentCitaId))) {
    console.error("❌ ID de cita inválido:", currentCitaId);
    alert("❌ ID de cita no es válido");
    return false;
  }

  if (!currentServicioId || isNaN(Number(currentServicioId))) {
    console.error("❌ ID de servicio inválido:", currentServicioId);
    alert("❌ ID del servicio no es válido");
    return false;
  }

  console.log("🧼 Limpiando HTML del modal...");
  d.getElementById("lista_productos").innerHTML = "";
  d.getElementById("modal_servicio_id").value = currentServicioId;
  d.getElementById("modal_servicio_nombre").value = currentServicioNombre;

  const $modalDetails = d.querySelector(modal_details);
  $modalDetails.classList.remove("translate");

  productosUtilizados.length = 0;

  console.log("🔽 Llenando combo productos...");
  await fillComboProductFromBackend(d.getElementById("select_producto"));
  comboProductBeforeEvent();

  console.log("🟡 Modal abierto con estado:", {
    currentCitaId,
    currentServicioId,
    currentServicioNombre,
    currentEstadoCita,
  });

  return true;
};

export const openModalDetailsService = (
  buton_open_details,
  modal_details,
  btn_guardar_terminado,
  close_modal,
  btn_save_details
) => {
  d.addEventListener("click", async (e) => {
    // 🟢 Abrir modal desde botón de detalles
    if (e.target.matches(buton_open_details)) {
      console.log("🟢 Botón detalles clicado");

      await abrirModalDetalles({
        idCita: e.target.dataset.idCita,
        servicioId: e.target.dataset.servicioId,
        servicioNombre: e.target.dataset.servicioNombre,
        modal_details,
      });
    }

    // 🟡 Botón "Guardar como Atendida"
    if (e.target.matches(btn_guardar_terminado)) {
      console.log("🟡 Guardar como Atendida clicado");

      await getDataFromCita(); // <-- 🔁 Esto asegura que el dataset esté lleno
      console.log("🟡 Guardar como Atendida clicado");

      const btn = e.target.closest(btn_guardar_terminado);
      if (!btn) {
        console.warn(
          "⚠️ No se encontró el botón dentro del contexto esperado."
        );
        return;
      }

      console.log("📦 Valores recibidos desde botón guardar como atendida:", {
        idCita: btn.dataset.idCita,
        servicioId: btn.dataset.servicioId,
        servicioNombre: btn.dataset.servicioNombre,
      });

      const fila = btn.closest("tr");
      const selects = fila.querySelectorAll("select");
      const selectEstado = selects[1];

      if (!selectEstado) {
        console.warn("⚠️ No se encontró el select de estado.");
        return;
      }

      const estadoSeleccionadoTexto = selectEstado.selectedOptions[0].text;
      currentEstadoCita = estadoSeleccionadoTexto.toUpperCase();

      console.log("📋 Estado seleccionado:", currentEstadoCita);

      if (["ATENDIDA", "PROGRAMADA", "CANCELADA"].includes(currentEstadoCita)) {
        const ok = await abrirModalDetalles({
          idCita: btn?.dataset.idCita,
          servicioId: btn?.dataset.servicioId,
          servicioNombre: btn?.dataset.servicioNombre,
          modal_details,
        });

        if (!ok) {
          console.warn("⚠️ No se pudo abrir el modal (datos inválidos)");
          return;
        }
      } else {
        alert(`❌ Estado actual no permitido: ${estadoSeleccionadoTexto}`);
        console.warn(
          "❌ Estado no permitido para abrir modal:",
          currentEstadoCita
        );
        return;
      }
    }

    // 🔴 Cerrar modal
    if (e.target.matches(close_modal)) {
      console.log("🔴 Cerrar modal clicado");
      const $modalProducto = d.querySelector(modal_details);
      $modalProducto.classList.add("translate");
    }

    // 🟢 Guardar detalle (productos, duración, motivo)
    if (e.target.matches(btn_save_details)) {
      e.preventDefault();
      console.log("🟢 Guardar detalles clicado");

      const duracion = parseInt(
        d.getElementById("modal_duracion_detalle").value
      );
      const motivo = d.getElementById("modal_motivo").value;

      if (!currentCitaId || !currentServicioId || !currentServicioNombre) {
        console.error("❌ Datos incompletos antes de guardar detalle:", {
          currentCitaId,
          currentServicioId,
          currentServicioNombre,
        });
        alert("❌ No se han cargado correctamente los datos del servicio.");
        return;
      }

      console.log("📤 Enviando datos a postDataDetails:", {
        currentCitaId,
        productosUtilizados,
        duracion,
        motivo,
        currentServicioId,
        currentServicioNombre,
      });

      postDataDetails(
        Number(currentCitaId),
        productosUtilizados,
        duracion,
        motivo,
        Number(currentServicioId),
        currentServicioNombre
      );
    }
  });
};
