import { Servicio } from "../clases/servicio.js";

const SERVICIO_STORAGE_KEY = "servicio_storage";

export class ServicioStorage {
  constructor() {
    if (ServicioStorage._instancia) return ServicioStorage._instancia;
    ServicioStorage._instancia = this;
  }

  guardarServicio(servicio) {
    const servicios = this.obtenerServicios();

    let id = servicio.idServicio || servicio.id;
    if (!id) {
      id = Date.now() + Math.random(); // ID único
    }

    servicio.id = id;
    servicio.idServicio = id;

    const index = servicios.findIndex(
      (s) => String(s.id) === String(id) || String(s.idServicio) === String(id)
    );

    if (index >= 0) {
      servicios[index] = servicio;
    } else {
      servicios.push(servicio);
    }

    localStorage.setItem(SERVICIO_STORAGE_KEY, JSON.stringify(servicios));
  }

  guardarTodosLosServicios(listaServicios) {
    const serviciosConvertidos = listaServicios.map((obj) => {
      const servicio = { ...obj };

      let id = servicio.idServicio || servicio.id;
      if (!id) {
        id = Date.now() + Math.random();
      }

      servicio.id = id;
      servicio.idServicio = id;

      return servicio;
    });

    localStorage.setItem(
      SERVICIO_STORAGE_KEY,
      JSON.stringify(serviciosConvertidos)
    );
  }

  obtenerServicios() {
    const data = localStorage.getItem(SERVICIO_STORAGE_KEY);
    const lista = data ? JSON.parse(data) : [];

    return lista
      .filter(
        (obj) =>
          (typeof obj.nombreServicio === "string" ||
            typeof obj.nombre === "string") &&
          (typeof obj.descripcionServicio === "string" ||
            typeof obj.descripcion === "string") &&
          !isNaN(obj.precioServicio ?? obj.precio) &&
          !isNaN(obj.duracionServicio ?? obj.duracion)
      )
      .map((obj) => {
        const nombre = obj.nombreServicio || obj.nombre;
        const descripcion = obj.descripcionServicio || obj.descripcion;
        const precio = obj.precioServicio ?? obj.precio;
        const duracion = obj.duracionServicio ?? obj.duracion;

        const servicio = new Servicio(nombre, precio, descripcion, duracion);
        servicio.id = obj.idServicio || obj.id;
        servicio.idServicio = obj.idServicio || obj.id;
        return servicio;
      });
  }

  eliminarServicioPorId(id) {
    const servicios = this.obtenerServicios();
    const filtrados = servicios.filter((s) => String(s.id) !== String(id));
    localStorage.setItem(SERVICIO_STORAGE_KEY, JSON.stringify(filtrados));
  }

  vaciarServicios() {
    localStorage.removeItem(SERVICIO_STORAGE_KEY);
  }
}

export const servicioStorage = new ServicioStorage();
