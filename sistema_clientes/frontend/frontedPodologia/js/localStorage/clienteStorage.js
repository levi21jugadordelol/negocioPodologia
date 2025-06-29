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

    const id = cliente.idCliente || cliente.id;

    const index = clientes.findIndex(
      (c) =>
        (c.idCliente && String(c.idCliente) === String(id)) ||
        (c.id && String(c.id) === String(id))
    );

    // Garantiza consistencia de claves
    cliente.idCliente = id;
    cliente.id = id;

    if (index >= 0) {
      clientes[index] = cliente;
    } else {
      clientes.push(cliente);
    }

    localStorage.setItem(STORAGE_KEY, JSON.stringify(clientes));
  }

  obtenerClientes() {
    const data = localStorage.getItem(STORAGE_KEY);
    const lista = data ? JSON.parse(data) : [];

    return lista
      .map((obj) => {
        // Acepta tanto nombre como nombreCliente
        const nombre = obj.nombre || obj.nombreCliente || "";
        const apellido = obj.apellido || obj.apellidoCliente || "";
        const correo = obj.correo || obj.correoCliente || "";
        const celular = obj.celular || obj.cellCliente || "";
        const dni = obj.dni || obj.dniCliente || "";

        const cliente = new Cliente(nombre, apellido, correo, celular, dni);
        cliente.id = obj.idCliente || obj.id;
        cliente.idCliente = obj.idCliente || obj.id;

        return cliente;
      })
      .filter((c) => typeof c.nombre === "string" && c.nombre.trim() !== "");
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
