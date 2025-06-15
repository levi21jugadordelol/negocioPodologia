export const mapCliente = (clienteRaw) => ({
  nombre: clienteRaw.nombreCliente || clienteRaw.nombre || "",
  apellido: clienteRaw.apellidoCliente || clienteRaw.apellido || "",
  correo: clienteRaw.correoCliente || clienteRaw.correo || "",
  celular: clienteRaw.cellCliente || clienteRaw.celular || "",
  dni: clienteRaw.dniCliente || clienteRaw.dni || "",
  id: clienteRaw.idCliente || clienteRaw.id || "",
});
