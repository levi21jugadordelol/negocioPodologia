import { BASE_URL } from "../config/configuracion.js";

export async function enviarDetalleApi(idCita, detalle) {
  const URL_API = `${BASE_URL}/citas/${idCita}/detalles`;
  try {
    const response = await fetch(URL_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(detalle),
    });

    if (!response.ok)
      throw new Error(`Error al guardar detalle: ${response.status} `);

    const detalleGuardado = await response.json();

    console.log("Respuesta completa:", detalleGuardado);

    const { idServicio } = detalleGuardado;

    console.log(detalleGuardado);

    alert("✅ Detalle guardado correctamente.");
    console.log({ exito: true, mensaje: detalleGuardado.mensaje });
    return idServicio;
    // return { exito: true, mensaje: detalleGuardado.mensaje };
    // Puedes cerrar el modal o limpiar el formulario si deseas
  } catch (e) {
    console.error("❌ Error al enviar cita:", e.message);
    alert("❌ Error al guardar cita");
    return { error: true, mensaje: e.message };
  }
}
