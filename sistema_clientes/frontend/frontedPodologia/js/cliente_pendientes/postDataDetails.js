import { DetalleServicio } from "../clases/detalleServicio.js";
import { enviarDetalleApi } from "../conexion/enviarDetalleApi.js";

const d = document;

export const postDataDetails = async (idCita) => {
  //aca se va obtener y trabnajar con los queryselectro del dom
  //se va llenar los combos

  const $formDetalle = d.getElementById("form_detalle");

  const servicioId = parseInt(d.querySelector("#modal_servicio_id").value);
  const duracion = parseInt(d.querySelector("#modal_duracion").value);
  const motivo = d.querySelector("#modal_motivo").value.trim();

  // Capturar productos utilizados
  const productosUtilizados = [];
  d.querySelectorAll(".producto_item").forEach((item) => {
    const idProducto = parseInt(item.querySelector(".producto_id").value);
    const cantidad = parseInt(item.querySelector(".cantidad_utilizada").value);

    if (!isNaN(idProducto) && !isNaN(cantidad) && cantidad > 0) {
      productosUtilizados.push({
        productoId: idProducto,
        cantidadUtilizada: cantidad,
      });
    }
  });

  console.log("📦 Datos a enviar del detalle:", {
    servicioId,
    duracion,
    motivo,
    productosUtilizados,
  });

  try {
    const detalle = new DetalleServicio(
      servicioId,
      duracion,
      motivo,
      productosUtilizados
    );

    const respuestaDetalle = await enviarDetalleApi(
      idCita,
      detalle.toBackendJson()
    );

    console.log("respuesta: ", respuestaDetalle);

    if (respuestaDetalle.error) {
      alert("❌ Error al enviar al backend: " + respuestaDetalle.mensaje);
    } else {
      alert("✅ detalle enviado exitosamente.");
      d.querySelector("#servicio_guardado_id").textContent = idServicioGuardado;
      $formDetalle.reset();
    }
  } catch (e) {
    console.error("Error:", e);
    alert("Ocurrió un error al guardar el detalle.");
  }
  $formDetalle.reset();
};
