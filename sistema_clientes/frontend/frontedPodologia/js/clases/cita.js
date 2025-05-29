export class Cita {
  constructor(
    clienteId,
    servicioId,
    tipoCita,
    fechaCita,
    estadoCita,
    observaciones = "",
    detalles = []
  ) {
    this._validarId(clienteId);
    this._validarCadena(tipoCita, "Tipo de cita");
    this._validarFecha(fechaCita);
    this._validarCadena(estadoCita, "Estado de cita");

    this.clienteId = clienteId;
    this.servicioId = servicioId;
    this.tipoCita = tipoCita;
    this.fechaCita = this._formatearFechaISO(fechaCita);
    this.estadoCita = estadoCita;
    this.observaciones = observaciones;
    this.detalles = detalles;
  }

  toBackendJson() {
    return {
      clienteId: this.clienteId,
      servicioId: this.servicioId,
      tipoCita: this.tipoCita,
      fechaCita: this.fechaCita, // en formato ISO LocalDateTime
      estadoCita: this.estadoCita,
      observaciones: this.observaciones,
      //detalles: this.detalles.map((det) => det.toBackendJson?.() ?? det),
    };
  }

  // === Validaciones internas ===
  _validarId(id) {
    if (typeof id !== "number" || id <= 0 || !Number.isInteger(id)) {
      throw new Error("El ID del cliente debe ser un número entero positivo");
    }
  }

  _validarCadena(valor, campo) {
    if (!valor || typeof valor !== "string") {
      throw new Error(`${campo} debe ser una cadena no vacía`);
    }
  }

  _validarFecha(fecha) {
    if (!fecha || isNaN(new Date(fecha).getTime())) {
      throw new Error("La fecha de la cita no es válida");
    }
  }

  _formatearFechaISO(fecha) {
    // Si recibe un objeto Date, lo convierte a "yyyy-MM-ddTHH:mm:ss"
    const f = new Date(fecha);
    return f.toISOString().slice(0, 19); // elimina la "Z" al final
  }
}
