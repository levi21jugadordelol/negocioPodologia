import { enviarDeleteClienteApi } from "../api_cliente/enviarDeleteClienteApi.js";

const d = document;

export const comienzaProcesoDeleteClienteFromTotal = async (idCliente) => {
  console.log(
    "el id de cliente en el metodo de proceso de delete cliente total es: ",
    idCliente
  );
  const respuesta = await enviarDeleteClienteApi(idCliente);
  console.log("cliente eliminado fue: ", respuesta);
  return respuesta;
};
