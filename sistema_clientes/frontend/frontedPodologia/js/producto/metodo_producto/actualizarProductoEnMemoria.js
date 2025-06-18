import { productoStorage } from "../../localStorage/productoStorage.js";

export const actualizarProductoEnMemoria = () => {
  return productoStorage.obtenerProductos();
};
