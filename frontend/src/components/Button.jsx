import React from "react";

class Button extends React.Component {
  render() {
    return (
      <button type={this.props.tipo} className={this.props.classe}>
        {this.props.label}
      </button>
    );
  }
}

export default Button;
