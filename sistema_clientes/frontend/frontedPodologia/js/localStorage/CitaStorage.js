import { Cita } from "../clases/cita.js";

const STORAGE_KEY = "citasStorage";

export class CitaStorage {
  constructor() {
    if (CitaStorage._instance) {
      return CitaStorage._instance;
    }
    CitaStorage._instance = this;
  }

  esCitaValida(obj) {
    const clienteId = Number(obj.clienteId);
    const servicioId = Number(obj.servicioId);
    const fechaCitaValida =
      typeof obj.fechaCita === "string" && obj.fechaCita.trim().length > 0;
    const estadoValido = typeof obj.estadoCita === "string";

    return (
      Number.isInteger(clienteId) &&
      clienteId > 0 &&
      Number.isInteger(servicioId) &&
      servicioId > 0 &&
      fechaCitaValida &&
      estadoValido
    );
  }

  guardar(cita) {
    const copia = { ...cita };

    copia.clienteId = Number(copia.clienteId);
    copia.servicioId = Number(copia.servicioId);

    if (!this.esCitaValida(copia)) {
      console.warn("❌ No se guardó cita inválida:", copia);
      return;
    }

    const citas = this.obtenerTodos();

    if (!copia.id) {
      copia.id = Date.now() + Math.random();
    }

    const index = citas.findIndex((c) => String(c.id) === String(copia.id));

    if (index >= 0) {
      citas[index] = copia;
    } else {
      citas.push(copia);
    }

    localStorage.setItem(STORAGE_KEY, JSON.stringify(citas));
  }

  guardarTodos(listaCitas) {
    console.log("📦 Leyendo desde localStorage:", listaCitas);

    const citasConvertidas = listaCitas
      .map((obj) => {
        const clienteId = obj.cliente?.id ?? obj.clienteId;
        const servicioId = obj.servicio?.id ?? obj.servicioId;

        const cita = {
          ...obj,
          clienteId: Number(clienteId),
          servicioId: Number(servicioId),
        };

        if (!cita.id) {
          cita.id = Date.now() + Math.random();
        }

        // Si deseas mantener limpio el storage:
        delete cita.cliente;
        delete cita.servicio;

        return cita;
      })
      .filter((cita) => this.esCitaValida(cita));

    if (citasConvertidas.length !== listaCitas.length) {
      console.warn("⚠️ Se descartaron citas inválidas al guardarTodos.");
    }

    localStorage.setItem(STORAGE_KEY, JSON.stringify(citasConvertidas));
    console.log("📋 Guardando en localStorage estas citas:", citasConvertidas);
  }

  obtenerTodos() {
    const data = localStorage.getItem(STORAGE_KEY);
    const lista = data ? JSON.parse(data) : [];

    const citasValidas = [];

    lista.forEach((obj, index) => {
      if (!this.esCitaValida(obj)) {
        console.warn(`🚫 Cita inválida descartada en posición ${index}:`, obj);
        return;
      }

      console.log("🧾 Intentando reconstruir cita con:", obj);

      try {
        const cita = new Cita(
          Number(obj.clienteId),
          Number(obj.servicioId),
          obj.fechaCita,
          obj.estadoCita,
          obj.observaciones,
          obj.detalles || []
        );
        cita.id = obj.id;
        citasValidas.push(cita);
      } catch (error) {
        console.warn(
          `❌ Error al reconstruir cita en posición ${index}:`,
          error.message,
          obj
        );
      }
    });

    return citasValidas;
  }

  eliminarPorId(id) {
    const citas = this.obtenerTodos();
    const nuevas = citas.filter((c) => String(c.id) !== String(id));
    localStorage.setItem(STORAGE_KEY, JSON.stringify(nuevas));
  }

  eliminarPorClienteId(clienteId) {
    const citas = this.obtenerTodos();
    const nuevas = citas.filter(
      (c) => String(c.clienteId) !== String(clienteId)
    );
    localStorage.setItem(STORAGE_KEY, JSON.stringify(nuevas));
  }

  vaciar() {
    localStorage.removeItem(STORAGE_KEY);
  }
}

export const citaStorage = new CitaStorage();
