import { DetalleServicio } from "../clases/detalleServicio.js";
import { enviarDetalleApi } from "../conexion/enviarDetalleApi.js";

const d = document;

/**
 * Enviar el detalle de servicio con productos utilizados sin cantidad.
 * @param {number} idCita - ID de la cita a la que pertenece este detalle.
 *  @param {Array<{productoId: number}>} productosUtilizados - Productos agregados en el modal.
 */

export const postDataDetails = async (idCita, productosUtilizados) => {
  //aca se va obtener y trabnajar con los queryselectro del dom
  //se va llenar los combos

  const $formDetalle = d.getElementById("form_detalle");
  const duracionInput = d.getElementById("modal_duracion_detalle");
  console.log(
    "🧪 Value desde JS:",
    d.getElementById("modal_duracion_detalle").value
  );

  // Llenar todos los combos de productos visibles
  /*const selectsProductos = d.querySelectorAll(".producto_id");
  console.log("Encontrados:", selectsProductos.length);
  selectsProductos.forEach((select) => {
    fillComboProductFromBackend(select); // ✅ ahora sí pasamos cada <select> individual
  }); */

  const duracion = Number(duracionInput.value);
  console.log("🧪 Input duración (elemento):", duracionInput);
  console.log("🧪 Valor crudo:", duracionInput.value);

  if (isNaN(duracion) || duracion <= 0) {
    alert("Duración total debe ser un número válido y positivo");
    return;
  }

  const servicioId = parseInt(d.querySelector("#modal_servicio_id").value);

  //obteniendo motivo
  const motivo = d.querySelector("#modal_motivo").value.trim();

  if (productosUtilizados.length === 0) {
    alert("Debe agregar al menos un producto utilizado");
    return;
  }

  // Capturar productos utilizados
  /* const productosUtilizados = [];
  d.querySelectorAll(".producto_item").forEach((item) => {
    const idProducto = parseInt(item.querySelector(".producto_id").value);

    if (!isNaN(idProducto) && idProducto > 0) {
      //productosUtilizados.push({ productoId: idProducto });
      productosUtilizados.push({
        // producto: { idProducto: idProducto },
        productoId: idProducto,
      });
    }
  });  */

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

    console.log("respuesta del backend: ", respuestaDetalle);

    if (respuestaDetalle.error) {
      alert("❌ Error al enviar al backend: " + respuestaDetalle.mensaje);
    } else {
      /* alert("✅ detalle enviado exitosamente.");
      d.querySelector("#servicio_guardado_id").textContent = idServicioGuardado;
      $formDetalle.reset(); */
      alert("✅ detalle enviado exitosamente.");
      // Limpia estado y UI
      productosUtilizados.length = 0;
      d.getElementById("lista_productos").innerHTML = "";
      $formDetalle.reset();
    }
  } catch (e) {
    console.error("Error:", e);
    alert("Ocurrió un error al guardar el detalle.");
  }
  // $formDetalle.reset();
};
