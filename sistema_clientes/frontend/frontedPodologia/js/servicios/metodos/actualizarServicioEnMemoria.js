import { servicioStorage } from "../../localStorage/servicioStorage.js";

export const actualizarServicioEnMemoria = () => {
  //return servicioStorage.
  return servicioStorage.obtenerServicios();
};
