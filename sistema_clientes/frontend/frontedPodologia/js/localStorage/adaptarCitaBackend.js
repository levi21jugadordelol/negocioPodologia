export function adaptarCitaBackend(citaRaw) {
  return {
    id: citaRaw.id,
    clienteId: citaRaw.clienteId,
    servicioId: citaRaw.servicio?.idServicio ?? citaRaw.servicioId,
    fechaCita: citaRaw.fechaCita,
    estadoCita: citaRaw.estadoCita,
    observaciones: citaRaw.observaciones,
    detalles: citaRaw.detalles || [],
    // puedes incluir nombreCliente o servicio completo si lo necesitas para mostrar en UI
  };
}
