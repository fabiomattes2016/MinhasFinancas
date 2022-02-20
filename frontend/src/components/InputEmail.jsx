import React from "react";

class InputEmail extends React.Component {
  render() {
    return (
      <input
        type="email"
        className="form-control"
        id={this.props.id}
        name={this.props.name}
        placeholder={this.props.placeholder}
      />
    );
  }
}

export default InputEmail;
