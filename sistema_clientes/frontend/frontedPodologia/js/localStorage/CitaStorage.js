// CitaStorage.js
import { Cita } from "../clases/cita.js";

const STORAGE_KEY = "citasStorage";

class CitaStorage {
  constructor() {
    if (CitaStorage._instance) {
      return CitaStorage._instance;
    }
    CitaStorage._instance = this;
  }

  guardar(cita) {
    const citas = this.obtenerTodos();
    const index = citas.findIndex((c) => c.id === cita.id);
    if (index >= 0) {
      citas[index] = cita;
    } else {
      citas.push(cita);
    }
    localStorage.setItem(STORAGE_KEY, JSON.stringify(citas));
  }

  obtenerTodos() {
    const data = localStorage.getItem(STORAGE_KEY);
    const lista = data ? JSON.parse(data) : [];
    return lista.map((obj) => {
      const cita = new Cita(
        Number(obj.clienteId),
        Number(obj.servicioId),
        obj.fechaCita,
        obj.estadoCita,
        obj.observaciones,
        obj.detalles || []
      );
      cita.id = obj.id;
      return cita;
    });
  }

  eliminarPorClienteId(clienteId) {
    const citas = this.obtenerTodos();
    const nuevas = citas.filter((c) => c.clienteId !== clienteId);
    localStorage.setItem(STORAGE_KEY, JSON.stringify(nuevas));
  }

  vaciar() {
    localStorage.removeItem(STORAGE_KEY);
  }
}

// Exporta la única instancia (Singleton)
export const citaStorage = new CitaStorage();
