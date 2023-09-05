import React, {Component} from "react";
import './Home.css';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

export default class CustomerProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            restaurantsList:[],
            selectedRestaurant:'',
            restaurantsNames:[]
        };

        this.handleDropdownChange = this.handleDropdownChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
     async componentDidMount() {
        fetch('http://localhost:8080/sd_assignment2/restaurant/viewrestaurants')
            .then(async response => {
                const restaurantsFromApi = await response.json();
                this.setState({
                    restaurantsList: restaurantsFromApi});})
            .catch(error => {
                console.log(error);
            });
    }

    renderRestaurants(restaurant, index) {
        return <li key={index}>{restaurant.name}</li>
    }

     handleDropdownChange(e) {
        this.setState({ selectedRestaurant: e.target.value });

        console.log(this.state.selectedRestaurant)

    }

    handleSubmit(e){
       // this.setState({ selectedRestaurant: e.target.value });
        localStorage.setItem('restaurant', this.state.selectedRestaurant);
        console.log(this.state.selectedRestaurant)
    }

    render() {
        console.log(this.state.restaurantsList);

        return (
            <div className="App" className="centered">
                <h1 >Customer Profile <br /> </h1>
                <h2>Available restaurants: <br /> </h2>
                <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Location</th>
                    <th>Delivery Zones</th>
                </tr>
                </thead>
                <tbody>

                {this.state.restaurantsList.map((item, i) => (
                    <tr key={i}>
                        <td>{item.name}</td>
                        <td>{item.location}</td>
                        <td>{item.deliveryZones}</td>
                    </tr>
                ))}
                </tbody>
                    </table>

                <h2> View menu for restaurant: </h2>

                <select onChange={this.handleDropdownChange}>
                    {this.state.restaurantsList.map(optn => (
                        <option>{optn.name}</option>
                    ))}

                </select>
                <br />
                <Button  value= "customer" variant="secondary" size="lg" type="submit" onClick={(e) => this.handleSubmit(e) } href={"/viewmenucustomer"} >View menu</Button>


            </div>);
    }
}