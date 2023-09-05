/* Register.js */
import React, {Component} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import './Home.css';
class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
                firstName: '', lastName: '', username: '', password: '', confirmPassword: ''
        }
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async handleSubmit(event){
        event.preventDefault();
        await this.setState({
            firstName: event.target.elements.firstName.value,
            lastName: event.target.elements.lastName.value,
            username: event.target.elements.username.value,
            password: event.target.elements.password.value,
            confirmPassword: event.target.elements.confirmPassword.value
        })
         const data = {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            username: this.state.username,
            password: this.state.password

        }
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        };
       // console.log(data, "11");
        if (this.state.password!==this.state.confirmPassword){
            alert('Passwords do not match!');

        }
        if ( event.nativeEvent.submitter.innerText === "Register as customer") {
            console.log(event.nativeEvent.submitter.innerText);
            fetch('http://localhost:8080/sd_assignment2/customer/create', requestOptions)
                .then(
                    response => {if (response.status === 400) {
                        alert ('Username already exists!');}
                            else if (response.status === 201){
                            alert ('User created successfully!');
                        }
             });}
                    else if ( event.nativeEvent.submitter.innerText === "Register as administrator") {
            fetch('http://localhost:8080/sd_assignment2/admin/create', requestOptions)
                .then(
                    response => {if (response.status === 400) {
                        alert ('Username already exists!');}
                    else if (response.status === 201){
                        alert ('User created successfully!');
                    }
                    });
        }



    }

    render() {

        return (

            <div className = "form" align="center" >
                <h1>  Sign up </h1>
                <Form onSubmit={this.handleSubmit}>
                    <div>
                        <label>First Name:
                        <input className="textfield"
                            type="text"
                            name="firstName"
                            // onChange={this.handleChange}
                        />
                        </label>
                    </div>
                    <div>
                    <label>Last Name:
                        <input className="textfield"
                            type="text"
                            name="lastName"
                            // onChange={this.handleChange}
                        />
                    </label>
                    </div>
                    <div>
                    <label> Username:
                        <input className="textfield"
                            type="text"
                            name="username"
                            // onChange={this.handleChange}
                        />
                    </label>
                    </div>
                    <div>
                    <label>Password:
                        <input className="textfield"
                            type="password"
                            name="password"
                            // onChange={this.handleChange}
                        />
                    </label>
                    </div>
                    <div>
                    <label>Confirm Password:
                        <input className="textfield"
                            type="password"
                            name="confirmPassword"
                        />
                    </label>
                    </div>
                    <div>
                    <Button  className= "button"  value= "customer" variant="secondary" size="lg" type="submit" >Register as customer   </Button> </div>
                    <div>
                    <Button className= "button"   value= "admin" variant="secondary"  size="lg" type="submit" >Register as administrator</Button> </div>


                </Form>
                <Button  className= "button"  variant="secondary"  size="lg" type="submit" href={"/login"} >
                    Login Page
                </Button>
            </div>

        );
    }
}
export default Register;