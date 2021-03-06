import React from "react";
import NavBarItem from "./NavBarItem";

class NavBar extends React.Component {
  render() {
    return (
      <div className="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
        <div className="container">
          <a
            href="/"
            className="navbar-brand"
            style={{ fontSize: "20px", fontWeight: "bolder" }}
          >
            Minhas Finanças
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarResponsive"
            aria-controls="navbarResponsive"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarResponsive">
            <ul className="navbar-nav">
              <NavBarItem href="/#/home" label="Home" />
              <NavBarItem href="/#/cadastro" label="Usuários" />
              <NavBarItem href="/#/" label="Lançamentos" />
              <NavBarItem href="/#/login" label="Login" />
            </ul>
          </div>
        </div>
      </div>
    );
  }
}

export default NavBar;
