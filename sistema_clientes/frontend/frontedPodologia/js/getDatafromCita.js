import { Cita } from "./clases/cita.js";
import { enviarCitaApi } from "./conexion/enviarCitaApi.js";
import { citaStorage } from "./localStorage/CitaStorage.js";
import {
  desactivarBotonGuardar,
  desactivarCamposFila,
} from "./citas/metodos/desactivar_button_input.js";

export const datosCita = [];

export const getDataFromCita = async () => {
  //alert("hola desde el metodo getDataFromCita");
  const d = document;
  const filas = d.querySelectorAll("#tabla-citas tbody tr");
  console.log("fila tendria: ", filas);
  if (!filas) return null;

  datosCita.length = 0;

  for (const fila of filas) {
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
    //const idCliente = parseInt(tdCliente?.textContent.trim(), 10);
    const textoCrudo = tdCliente?.textContent.replace(/\D/g, "");
    const idCliente = parseInt(textoCrudo, 10);

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
        fila.dataset.id = respuesta.idCita;
        console.log("🆔 ID asignado a la fila:", respuesta.idCita);
      } else {
        console.warn("⚠️ No se recibió un `idCita` válido en la respuesta:");
        console.warn("⚠️ No se recibió un idCita válido");
      }

      console.log("cita creada correctamente: ", respuesta.mensaje);

      //desactivamos el button guardar al ver que se guardo cita
      desactivarBotonGuardar();

      //ponemos inhabilitadas los edit en los input de cita
      desactivarCamposFila();

      console.log(
        "🛑 Campos desactivados y botón deshabilitado para esta fila"
      );
      console.log("============== FIN DE PROCESO DE FILA ==============\n");
    } catch (e) {
      alert("❌ Error al crear cita: " + e.message);
      console.error("Detalle del error:", e);
    }
  }

  //Guardar el arreglo completo en localStorage para persistencia
  datosCita.forEach((cita) => citaStorage.guardar(cita));

  console.log("✅ Datos guardados en localStorage");
};
