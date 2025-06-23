export const mapCita = (citaRaw) => ({
  id: citaRaw.id || citaRaw.idCita || "",
  fechaCita: citaRaw.fechaCita || "",
  estadoCita: citaRaw.estadoCita || "",
  observaciones: citaRaw.observaciones || "",
  nombreCliente: citaRaw.nombreCliente || "",
  clienteId: citaRaw.clienteId || "",
  facturaId: citaRaw.facturaId || "",
  createdAt: citaRaw.createdAt || "",
  servicio: citaRaw.servicio || {}, // puede incluir id y nombre
  detalles: citaRaw.detalles || [], // lista de DetalleDto
});
