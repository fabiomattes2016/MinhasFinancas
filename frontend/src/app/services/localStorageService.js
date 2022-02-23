class LocalStorageService {
  static addItem(chave, valor) {
    localStorage.setItem(chave, JSON.stringify({ usuarioId: valor }));
  }

  static obterItem(chave) {
    return JSON.parse(localStorage.getItem(chave));
  }
}

export default LocalStorageService;
