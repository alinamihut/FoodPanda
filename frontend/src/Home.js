
import './components/Home.css';
import React, {Component} from "react";
import Button from "react-bootstrap/Button";
import 'bootstrap/dist/css/bootstrap.min.css';
class Home extends Component {

  constructor(props) {
    super(props);
   // this.f1= this.f1.bind(this);
    this.state = {
      count:1
    }
  }

  render() {
    return (
    <div className="App" class="centered">
      <h1>    Welcome to Food Panda! <br /> </h1>

      <Button className= "button"  variant="light" size="lg"  type="submit" href={"/login"} > Login</Button>
        <Button   className= "button"  variant="light" size="lg" type="submit" href={"/register"}>Register </Button>

    </div>)
  }
}

export default Home;
