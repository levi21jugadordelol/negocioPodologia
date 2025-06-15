export const crearCelda = (texto) => {
  const td = document.createElement("td");
  td.textContent = texto;
  return td;
};
