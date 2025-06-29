import { Cita } from "./clases/cita.js";
import { enviarCitaApi } from "./conexion/enviarCitaApi.js";
import { citaStorage } from "./localStorage/CitaStorage.js";
import {
  desactivarBotonGuardar,
  desactivarCamposFila,
} from "./citas/metodos/desactivar_button_input.js";

export const datosCita = [];

export const getDataFromCita = async () => {
  citaStorage.vaciar();
  datosCita.length = 0;

  const d = document;
  const filas = d.querySelectorAll("#tabla-citas tbody tr");

  console.log("fila tendria: ", filas);
  if (!filas) return null;

  for (const fila of filas) {
    if (fila.dataset.id) {
      console.log("⏩ Fila ya procesada, se omite. ID:", fila.dataset.id);
      continue; // Saltar fila ya guardada
    }

    const inputFecha = fila.querySelector(".fecha-cita");
    console.log("input fecha tendria: ", inputFecha);
    const selectServicio = fila.querySelector("td:nth-child(3) select");
    console.log("select servicio tendria: ", selectServicio);
    //const selectTipo = fila.querySelector("td:nth-child(4) select");
    const selectEstado = fila.querySelector("td:nth-child(4) select");
    console.log("select estado tendria: ", selectEstado);
    const inputObs = fila.querySelector("td:nth-child(5) input");
    console.log("input obs tendria: ", inputObs);

    const tdCliente = fila.querySelector("td:nth-child(2)");
    const idCliente = parseInt(tdCliente?.dataset.idcliente, 10);

    if (
      !inputFecha ||
      !inputFecha.value ||
      !selectServicio ||
      !selectServicio.value ||
      !selectEstado ||
      !selectEstado.value
    ) {
      console.warn("⚠️ Fila con campos incompletos. Se omite.");
      continue;
    }

    // === Consolas de verificación ===
    console.log("📅 Fecha ingresada:", inputFecha?.value);
    console.log("👤 ID del cliente:", idCliente);
    console.log("💼 Servicio seleccionado:", selectServicio?.value);
    //console.log("📌 Tipo de cita:", selectTipo?.value);
    console.log("📄 Estado de cita:", selectEstado?.value);
    console.log("📝 Observaciones:", inputObs?.value);

    try {
      console.log(
        "============== INICIANDO PROCESO DE UNA FILA =============="
      );

      console.log("Tipo de idCliente:", typeof idCliente, "valor:", idCliente);

      console.log("Texto crudo del TD:", tdCliente?.textContent);
      console.log("Texto limpio:", tdCliente?.textContent.trim());
      console.log("parseInt del texto:", idCliente);

      const cita = new Cita(
        idCliente, // clienteId
        parseInt(selectServicio?.value || "0"), // servicioId
        inputFecha?.value || "", // fechaCita
        selectEstado?.value || "", // estadoCita
        inputObs?.value || "", // observaciones
        [] // detalles (si hay)
      );

      console.log("✅ Cita formada correctamente:", cita);
      datosCita.push(cita);

      console.log("📤 JSON que se enviará a la API:", cita.toBackendJson());

      //Llamas a la API
      const respuesta = await enviarCitaApi(cita.toBackendJson());

      console.log("📥 Respuesta cruda de la API:", respuesta);

      if (respuesta.exito && respuesta.idCita) {
        // ✅ Guardar el ID en la fila para luego editar
        cita.idCita = respuesta.idCita;
        citaStorage.guardar(cita);
        fila.dataset.id = respuesta.idCita;
        console.log("🆔 ID asignado a la fila:", respuesta.idCita);

        // 🔥 Agrega esto:
        const btnGuardar = fila.querySelector(".btn-guardar-cita"); // asegúrate de que esta clase sea la correcta
        if (btnGuardar) {
          btnGuardar.dataset.idCita = respuesta.idCita;
          btnGuardar.dataset.servicioId = selectServicio.value;
          btnGuardar.dataset.servicioNombre =
            selectServicio.options[selectServicio.selectedIndex].textContent;

          // 🧪 Verificamos todo lo que tiene el botón
          console.log("📦 Dataset del botón después de guardar:");
          console.log("🆔 idCita:", btnGuardar.dataset.idCita);
          console.log("💼 servicioId:", btnGuardar.dataset.servicioId);
          console.log("📛 servicioNombre:", btnGuardar.dataset.servicioNombre);
          console.log("🔍 Botón completo:", btnGuardar.outerHTML);
        } else {
          console.warn("❌ No se encontró el botón guardar en la fila");
        }

        //desactivamos el button guardar al ver que se guardo cita
        desactivarBotonGuardar();

        //ponemos inhabilitadas los edit en los input de cita
        desactivarCamposFila();
      } else {
        console.warn("⚠️ No se recibió un `idCita` válido en la respuesta:");
        console.warn("⚠️ No se recibió un idCita válido");
      }

      console.log("cita creada correctamente: ", respuesta.mensaje);

      console.log(
        "🛑 Campos desactivados y botón deshabilitado para esta fila"
      );
      console.log("============== FIN DE PROCESO DE FILA ==============\n");
    } catch (e) {
      alert("❌ Error al crear cita: " + e.message);
      console.error("Detalle del error:", e);
    }
  }
  // 🔁 Limpieza total del UI tras procesar todas las filas
  document.querySelector("#tabla-citas tbody").innerHTML = "";
};
