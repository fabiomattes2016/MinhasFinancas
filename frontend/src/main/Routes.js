import React from "react";

import { Route, Switch, HashRouter } from "react-router-dom";

import Login from "../views/Login";
import Cadastro from "../views/Cadastro";
import Home from "../views/Home";

function Routes() {
  return (
    <HashRouter>
      <Switch>
        <Route path="/login" component={Login} />
        <Route path="/cadastro" component={Cadastro} />
        <Route path="/home" component={Home} />
      </Switch>
    </HashRouter>
  );
}

export default Routes;
