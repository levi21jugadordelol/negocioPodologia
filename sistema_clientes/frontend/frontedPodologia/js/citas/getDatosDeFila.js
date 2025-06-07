export const getDatosDeFila = (fila) => {
  console.log("🧪 fila recibida en getDatosDeFila:", fila);

  const inputFecha = fila.querySelector(".fecha-cita");
  const selectServicio = fila.querySelector("td:nth-child(3) select");
  const selectEstado = fila.querySelector("td:nth-child(4) select");
  const inputObs = fila.querySelector("td:nth-child(5) input");
  const tdCliente = fila.querySelector("td:nth-child(2)");

  console.log("📌 contenido tdCliente:", tdCliente?.textContent);

  const rawClienteId = tdCliente?.textContent?.trim();
  console.log("📌 ID Crudo:", rawClienteId);

  if (!rawClienteId || !/^\d+$/.test(rawClienteId)) {
    throw new Error("ID del cliente vacío o con caracteres inválidos");
  }

  const idCliente = parseInt(rawClienteId, 10);
  if (isNaN(idCliente) || idCliente <= 0) {
    throw new Error("ID del cliente no es válido");
  }

  const datos = {
    idCliente,
    fecha: inputFecha?.value,
    servicio: parseInt(selectServicio?.value || "0"),
    estado: selectEstado?.value,
    observaciones: inputObs?.value,
  };

  console.log("📦 Datos generados por getDatosDeFila:", datos);

  return datos;
};
