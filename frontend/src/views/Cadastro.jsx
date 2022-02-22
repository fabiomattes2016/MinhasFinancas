import React from "react";
import { withRouter } from "react-router-dom";

import Card from "../components/Card";
import CardBody from "../components/CardBody";
import CardFooter from "../components/CardFooter";

class Cadastro extends React.Component {
  state = {
    nome: "",
    email: "",
    senha: "",
    confirmaSenha: "",
  };

  salvar = (e) => {
    e.preventDefault();
    console.log(this.state);
  };

  preparLogin = () => {
    this.props.history.push("/login");
  };

  render() {
    return (
      <form>
        <Card title="Cadastro de usuários">
          <CardBody>
            <div className="form-group">
              <label htmlFor="nome">Nome: *</label>
              <input
                type="text"
                name="nome"
                id="nome"
                className="form-control"
                value={this.state.nome}
                onChange={(e) => this.setState({ nome: e.target.value })}
              />
            </div>
            <div className="form-group">
              <label htmlFor="email">E-mail: *</label>
              <input
                type="email"
                name="email"
                id="email"
                className="form-control"
                value={this.state.email}
                onChange={(e) => this.setState({ email: e.target.value })}
              />
              <small id="email" className="form-text text-muted">
                Não divulgamos seu e-mail
              </small>
            </div>
            <div className="form-group">
              <label htmlFor="senha">Senha: *</label>
              <input
                type="password"
                name="senha"
                id="senha"
                className="form-control"
                value={this.state.senha}
                onChange={(e) => this.setState({ senha: e.target.value })}
              />
            </div>
            <div className="form-group">
              <label htmlFor="senha">Confirmar a senha: *</label>
              <input
                type="password"
                name="confirmaSenha"
                id="confirmaSenha"
                className="form-control"
                value={this.state.confirmaSenha}
                onChange={(e) =>
                  this.setState({ confirmaSenha: e.target.value })
                }
              />
            </div>
          </CardBody>
          <CardFooter>
            <button
              type="button"
              className="btn btn-success m-1"
              onClick={this.salvar}
            >
              Salvar
            </button>
            <button
              type="button"
              className="btn btn-danger m-1"
              onClick={this.preparLogin}
            >
              Voltar
            </button>
          </CardFooter>
        </Card>
      </form>
    );
  }
}

export default withRouter(Cadastro);
