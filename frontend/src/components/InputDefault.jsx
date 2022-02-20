import React from "react";

class InputDefault extends React.Component {
  render() {
    return (
      <input
        type="text"
        className="form-control"
        id={this.props.id}
        name={this.props.name}
        placeholder={this.props.placeholder}
      />
    );
  }
}

export default InputDefault;
