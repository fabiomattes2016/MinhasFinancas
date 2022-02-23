import ApiService from "../apiService";

class UsuarioService extends ApiService {
  constructor() {
    super("/usuarios");
  }

  autenticar(credenciais) {
    return this.post("/login", credenciais);
  }

  saldo(usuarioId) {
    return this.get(`/${usuarioId}/saldo`);
  }
}

export default UsuarioService;
