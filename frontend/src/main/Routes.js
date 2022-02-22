import React from "react";

import { Route, Switch, HashRouter } from "react-router-dom";

import Login from "../views/Login";
import Cadastro from "../views/Cadastro";

function Routes() {
  return (
    <HashRouter>
      <Switch>
        <Route path="/login" component={Login} />
        <Route path="/cadastro" component={Cadastro} />
      </Switch>
    </HashRouter>
  );
}

export default Routes;
