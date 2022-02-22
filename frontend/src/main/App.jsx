import React from "react";
import "../App.css";
import NavBar from "../components/NavBar";

import Routes from "./Routes";

class App extends React.Component {
  render() {
    return (
      <>
        <NavBar />
        <div className="container">
          <Routes />
        </div>
      </>
    );
  }
}

export default App;
