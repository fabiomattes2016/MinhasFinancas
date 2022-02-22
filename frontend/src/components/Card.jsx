import React from "react";

class Card extends React.Component {
  render() {
    return (
      <div className="card mb-3">
        <h4 className="card-header text-center">{this.props.title}</h4>
        {this.props.children}
      </div>
    );
  }
}

export default Card;
