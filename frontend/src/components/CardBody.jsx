import React from "react";

class CardBody extends React.Component {
  render() {
    return (
      <div className="card-body">
        <div className="row">
          <div className="col-lg-12">
            <div className="bs-component">
              <fieldset>{this.props.children}</fieldset>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default CardBody;
