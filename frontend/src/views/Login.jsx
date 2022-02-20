import React from "react";
import Button from "../components/Button";
import Card from "../components/Card";
import CardBody from "../components/CardBody";
import CardFooter from "../components/CardFooter";
import InputEmail from "../components/InputEmail";
import InputPassword from "../components/InputPassword";

class Login extends React.Component {
  render() {
    return (
      <div className="container">
        <div className="row">
          <div
            className="col-md-6"
            style={{ position: "relative", left: "300px" }}
          >
            <div className="bs-docs-section">
              <form>
                <Card title="Login">
                  <CardBody>
                    <div className="form-group">
                      <div className="form-group">
                        <label htmlFor="email">E-mail: *</label>
                        <InputEmail
                          id="email"
                          name="email"
                          placeholder="Digite seu e-mail."
                        />
                      </div>
                      <div className="form-group">
                        <label htmlFor="senha">Senha: *</label>
                        <InputPassword
                          id="senha"
                          name="senha"
                          placeholder="Digite a sua senha."
                        />
                      </div>
                    </div>
                  </CardBody>
                  <CardFooter>
                    <Button
                      tipo="button"
                      classe="btn btn-success m-1"
                      label="Login"
                    />
                    <Button
                      tipo="button"
                      classe="btn btn-danger m-1"
                      label="Cadastrar"
                    />
                  </CardFooter>
                </Card>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Login;
