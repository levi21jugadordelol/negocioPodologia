import { traerTodoClientes } from "../api_cliente/traerTodoClientes.js";
import { enviandoListaTotalClientes } from "../enviandoListaTotalClientes.js";
import { mapCliente } from "./mapCliente.js";

const d = document;

export const traerClientesFromDb = async () => {
  try {
    const datosListClientes = await traerTodoClientes();
    console.log("la lista de los clientes de la db es : ", datosListClientes);

    const clientesTotalesMapeados = datosListClientes.map(mapCliente);
    console.log("clientes totales mapeados son: ", clientesTotalesMapeados);

    enviandoListaTotalClientes(clientesTotalesMapeados);
  } catch (error) {
    console.error("❌ Error al cargar total de clientes:", error.message);
    alert("❌ No se pudieron cargar los clientes totales de la DB.");
    return [];
  }
};
