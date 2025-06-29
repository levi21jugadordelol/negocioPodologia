import { postDataDetails } from "./postDataDetails.js";
import { comboProductBeforeEvent } from "./ComboProductBeforeEvent.js";
import { fillComboProductFromBackend } from "../conexion/fillComboProductFromBackend.js";
import { getDataFromCita } from "../getDatafromCita.js";
//let currentCitaId = null;

const d = document;
export let productosUtilizados = [];

// Variables de estado actuales
let currentCitaId = null;
let currentServicioId = null;
let currentServicioNombre = null;
let currentEstadoCita = null;

// 🔧 Función para abrir modal y cargar datos
const abrirModalDetalles = async ({
  idCita,
  servicioId,
  servicioNombre,
  modal_details,
}) => {
  await getDataFromCita();

  currentCitaId = idCita;
  currentServicioId = servicioId;
  currentServicioNombre = servicioNombre;

  if (!currentCitaId || isNaN(Number(currentCitaId))) {
    alert("❌ ID de cita no es válido");
    return false;
  }

  if (!currentServicioId || isNaN(Number(currentServicioId))) {
    alert("❌ ID del servicio no es válido");
    return false;
  }

  d.getElementById("lista_productos").innerHTML = "";
  d.getElementById("modal_servicio_id").value = currentServicioId;
  d.getElementById("modal_servicio_nombre").value = currentServicioNombre;

  const $modalDetails = d.querySelector(modal_details);
  $modalDetails.classList.remove("translate");

  productosUtilizados.length = 0;
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
    // 🟢 Abrir modal desde botón de detalles directamente
    if (e.target.matches(buton_open_details)) {
      await abrirModalDetalles({
        idCita: e.target.dataset.idCita,
        servicioId: e.target.dataset.servicioId,
        servicioNombre: e.target.dataset.servicioNombre,
        modal_details,
      });
    }

    // 🟡 Botón "Guardar como Atendida"
    if (e.target.matches(btn_guardar_terminado)) {
      const btn = e.target.closest(btn_guardar_terminado);
      if (!btn) return;

      const fila = btn.closest("tr");
      const selects = fila.querySelectorAll("select");
      const selectEstado = selects[1];

      if (!selectEstado) {
        console.warn("⚠️ No se encontró el select de estado.");
        return;
      }

      const estadoSeleccionadoTexto = selectEstado.selectedOptions[0].text;
      currentEstadoCita = estadoSeleccionadoTexto.toUpperCase();

      if (["ATENDIDA", "PROGRAMADA", "CANCELADA"].includes(currentEstadoCita)) {
        const ok = await abrirModalDetalles({
          idCita: btn?.dataset.idCita,
          servicioId: btn?.dataset.servicioId,
          servicioNombre: btn?.dataset.servicioNombre,
          modal_details,
        });

        if (!ok) return;
      } else {
        alert(`❌ Estado actual: ${estadoSeleccionadoTexto}`);
        return;
      }
    }

    // 🔴 Cerrar modal
    if (e.target.matches(close_modal)) {
      const $modalProducto = d.querySelector(modal_details);
      $modalProducto.classList.add("translate");
    }

    // 🟢 Guardar detalle (productos, duración, motivo)
    // 🟢 Guardar detalle (productos, duración, motivo)
    if (e.target.matches(btn_save_details)) {
      e.preventDefault();

      const duracion = parseInt(
        d.getElementById("modal_duracion_detalle").value
      );
      const motivo = d.getElementById("modal_motivo").value;

      if (!currentCitaId || !currentServicioId || !currentServicioNombre) {
        alert("❌ No se han cargado correctamente los datos del servicio.");
        return;
      }

      // ✅ Guardar sin importar el estado
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
