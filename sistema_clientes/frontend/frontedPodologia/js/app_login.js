import { event_login } from "./usuario/auth/event_auth.js";

const d = document;

d.addEventListener("DOMContentLoaded", () => {
  event_login(".btn_login");
});
