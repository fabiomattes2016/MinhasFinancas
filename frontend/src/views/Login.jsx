import React from "react";
import { withRouter } from "react-router-dom";

import UsuarioService from "../app/services/usuarioService";
import LocalStorageService from "../app/services/localStorageService";

import Card from "../components/Card";
import CardBody from "../components/CardBody";
import CardFooter from "../components/CardFooter";

class Login extends React.Component {
  state = {
    email: "",
    senha: "",
    mensagemErro: null,
  };

  constructor() {
    super();
    this.service = new UsuarioService();
  }

  login = async (e) => {
    e.preventDefault();

    this.service
      .autenticar({
        email: this.state.email,
        senha: this.state.senha,
      })
      .then((res) => {
        let usuario = res.data;
        LocalStorageService.addItem("_usuario_logado", usuario.id);
        this.props.history.push("/home");
      })
      .catch((err) => {
        this.setState({ mensagemErro: err.response.data });
      });
  };

  prepareCadastrar = () => {
    this.props.history.push("/cadastro");
  };

  render() {
    return (
      <div className="row">
        <div
          className="col-md-6"
          style={{ position: "relative", left: "300px" }}
        >
          <div className="bs-docs-section">
            <form>
              <Card title="Login">
                <CardBody>
                  <div className="row text-center">
                    <span className="text-danger">
                      {this.state.mensagemErro}
                    </span>
                  </div>
                  <div className="form-group">
                    <div className="form-group">
                      <label htmlFor="email">E-mail: *</label>
                      <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="Digite seu e-mail."
                        className="form-control"
                        value={this.state.email}
                        onChange={(e) =>
                          this.setState({ email: e.target.value })
                        }
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="senha">Senha: *</label>
                      <input
                        type="password"
                        id="senha"
                        name="senha"
                        placeholder="Digite a sua senha."
                        className="form-control"
                        value={this.state.senha}
                        onChange={(e) =>
                          this.setState({ senha: e.target.value })
                        }
                      />
                    </div>
                  </div>
                </CardBody>
                <CardFooter>
                  <button
                    type="button"
                    className="btn btn-success m-1"
                    onClick={this.login}
                  >
                    Login
                  </button>
                  <button
                    type="button"
                    className="btn btn-danger m-1"
                    onClick={this.prepareCadastrar}
                  >
                    Cadastrar
                  </button>
                </CardFooter>
              </Card>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(Login);
