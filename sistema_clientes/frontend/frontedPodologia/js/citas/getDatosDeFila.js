export const getDatosDeFila = (fila) => {
  const inputFecha = fila.querySelector(".fecha-cita");
  const selectServicio = fila.querySelector("td:nth-child(3) select");
  const selectEstado = fila.querySelector("td:nth-child(4) select");
  const inputObs = fila.querySelector("td:nth-child(5) input");
  const tdCliente = fila.querySelector("td:nth-child(2)");
  const rawClienteId = tdCliente?.textContent?.trim();

  const idCliente = parseInt(rawClienteId, 10);
  if (isNaN(idCliente)) {
    throw new Error("ID del cliente no es válido");
  }

  return {
    idCliente,
    fecha: inputFecha?.value,
    servicio: parseInt(selectServicio?.value || "0"),
    estado: selectEstado?.value,
    observaciones: inputObs?.value,
  };
};
