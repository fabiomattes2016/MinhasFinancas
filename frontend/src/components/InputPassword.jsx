import React from "react";

class InputPassword extends React.Component {
  render() {
    return (
      <input
        type="password"
        className="form-control"
        id={this.props.id}
        name={this.props.name}
        placeholder={this.props.placeholder}
      />
    );
  }
}

export default InputPassword;
