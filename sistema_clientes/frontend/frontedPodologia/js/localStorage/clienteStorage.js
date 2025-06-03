// ClienteStorage.js
import { Cliente } from "../clases/cliente.js";

const STORAGE_KEY = "clientes";

export class ClienteStorage {
  //static #instancia;

  constructor() {
    if (ClienteStorage._instancia) {
      return ClienteStorage._instancia;
    }
    ClienteStorage._instancia = this;
  }

  guardarCliente(cliente) {
    const clientes = this.obtenerClientes();

    // Prioriza coincidencia por idCliente, luego por id
    const index = clientes.findIndex((c) =>
      c.idCliente ? c.idCliente === cliente.idCliente : c.id === cliente.id
    );

    if (index >= 0) {
      clientes[index] = cliente; // actualizar
    } else {
      clientes.push(cliente); // agregar nuevo
    }

    localStorage.setItem(STORAGE_KEY, JSON.stringify(clientes));
  }

  obtenerClientes() {
    const data = localStorage.getItem(STORAGE_KEY);
    const lista = data ? JSON.parse(data) : [];

    return lista
      .filter(
        (obj) => typeof obj.nombre === "string" && obj.nombre.trim() !== ""
      )
      .map((obj) => {
        const cliente = new Cliente(
          obj.nombre,
          obj.apellido,
          obj.correo,
          obj.celular,
          obj.dni
        );
        cliente.id = obj.id;
        cliente.idCliente = obj.idCliente;
        return cliente;
      });
  }

  eliminarClientePorId(id) {
    const clientes = this.obtenerClientes();
    const filtrados = clientes.filter((c) => c.id !== id);
    localStorage.setItem(STORAGE_KEY, JSON.stringify(filtrados));
  }

  vaciarClientes() {
    localStorage.removeItem(STORAGE_KEY);
  }
}

export const clienteStorage = new ClienteStorage();
