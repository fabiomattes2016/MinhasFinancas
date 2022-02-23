import React from "react";
import { withRouter } from "react-router-dom";

import UsuarioService from "../app/services/usuarioService";
import LocalStorageService from "../app/services/localStorageService";

class Home extends React.Component {
  state = {
    saldo: 0,
  };

  constructor() {
    super();
    this.service = new UsuarioService();
  }

  componentDidMount() {
    let usuario = LocalStorageService.obterItem("_usuario_logado");

    this.service
      .saldo(usuario.usuarioId)
      .then((res) => {
        let formatado = res.data.toLocaleString("pt-br", {
          style: "currency",
          currency: "BRL",
        });
        this.setState({ saldo: formatado });
      })
      .catch((err) => {
        console.log(err.response.data);
      });
  }

  prepararCadastro = () => {
    this.props.history.push("/cadastro");
  };

  render() {
    return (
      <div className="jumbotron">
        <h1 className="display-3">Bem vindo!</h1>
        <p className="lead">Esse é seu sistema de finanças.</p>
        <p className="lead">
          Seu saldo para o mês atual é de {this.state.saldo}
        </p>
        <hr className="my-4" />
        <p>
          E essa é sua área administrativa, utilize um dos menus ou botões
          abaixo para navegar pelo sistema.
        </p>
        <p className="lead">
          <button
            className="btn btn-primary btn-lg m-1"
            onClick={this.prepararCadastro}
          >
            <i className="fa fa-users"></i> Cadastrar Usuário
          </button>
          <a
            className="btn btn-danger btn-lg m-1"
            href="https://bootswatch.com/flatly/#"
            role="button"
          >
            <i className="fa fa-users"></i> Cadastrar Lançamento
          </a>
        </p>
      </div>
    );
  }
}

export default withRouter(Home);
