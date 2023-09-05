import React, {Component} from "react";
import './Home.css';
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

import Select from 'react-select';
import CheckBox from "react-native-web";
export default class AdminProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            location: '',
            deliveryZones:[],
            selectedDeliveryZones:[],
            administrator:localStorage.getItem('admin'),
            foodCategories:[],
            menuItemName:'',
            menuItemPrice:'',
            menuItemDescription:'',
            selectedFoodCategory:'',
            restaurantName:''};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmitAddRestaurant = this.handleSubmitAddRestaurant.bind(this);
        this.handleSubmitAddMenuItem = this.handleSubmitAddMenuItem.bind(this);
        this.handleDropdownChange = this.handleDropdownChange.bind(this);
        this.handleSubmitViewMenu = this.handleSubmitViewMenu.bind(this);
    }

     componentDidMount() {
        fetch(
            'http://localhost:8080/sd_assignment2/deliveryzone/get',)
            .then(async response => {
                const deliveryZonesFromApi = await response.json();

              this.setState({
                    deliveryZones: deliveryZonesFromApi
                });
            })
            .catch(error => {
                console.log(error);
            });

        fetch(
            'http://localhost:8080/sd_assignment2/restaurant/' + localStorage.getItem('admin')).
            then(async response => {
            const restaurantName = await response.json();

            this.setState({
                restaurantName: restaurantName
            });
        })
            .catch(error => {
                console.log(error);
            });

        fetch(
            'http://localhost:8080/sd_assignment2/restaurant/categories',)
            .then(async response => {
                const categoriesFromApi = await response.json();

                this.setState({
                    foodCategories: categoriesFromApi
                });
            })
            .catch(error => {
                console.log(error);
            });

    }

    handleChange(event) {
        var item = event.target.value;
        let zones = this.state.selectedDeliveryZones;
        zones.push(item);
        this.setState({
            selectedDeliveryZones: zones,
        });
        console.log(this.state.selectedDeliveryZones);

    }

    handleDropdownChange(e) {
        this.setState({ selectedFoodCategory: e.target.value });
    }

    handleSubmitViewMenu(e){
        localStorage.setItem('restaurant', this.state.restaurantName.name);
        console.log(this.state.restaurantName);
    }

    async handleSubmitAddRestaurant(event) {
        event.preventDefault();

        await this.setState({
            name: event.target.elements.name.value,
            location: event.target.elements.location.value,
            restaurantName:event.target.elements.name.value
        })


        const data = {
            name: this.state.name,
            location: this.state.location,
            admin: localStorage.getItem('admin'),
            deliveryZones: this.state.selectedDeliveryZones,


        }
        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        };

            fetch('http://localhost:8080/sd_assignment2/restaurant/create', requestOptions)
                .then(
                    response => {
                        if (response.status === 500 || response.status === 400) {
                            alert('This admin already has a restaurant!');
                        } else if (response.status === 201) {
                            alert('Restaurant created successfully!');
                        }
                    });
        localStorage.setItem('restaurant', this.state.restaurantName.name);

        }

    async handleSubmitAddMenuItem(event) {
        event.preventDefault();
        console.log(this.state.restaurantName);
        await this.setState({
            menuItemName: event.target.elements.name2.value,
            menuItemPrice: event.target.elements.price.value,
            menuItemDescription: event.target.elements.description.value,

        })
        console.log(event.target.elements.name2.value)
        const data2 = {
            name: this.state.menuItemName,
            description: this.state.menuItemDescription,
            price: this.state.menuItemPrice,
            category: this.state.selectedFoodCategory,
            admin: localStorage.getItem('admin'),

        }
        console.log(data2.price + "+++")
        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data2)
        };

        fetch('http://localhost:8080/sd_assignment2/restaurant/addmenuitem', requestOptions)
            .then(
                response => {
                    if (response.status === 400 || response.status ===500) {
                        alert('Menu item could not be added!');

                    } else if (response.status === 201) {
                        alert('Menu item added successfully!');
                    }
                });
    }


    render() {
        return (
            <div className="App" >
                <h1>Administrator Profile  for {localStorage.getItem('admin')}<br /> </h1>
                <h2>Admin for {this.state.restaurantName.name} </h2>
                <Form className="form-line" onSubmit={this.handleSubmitAddRestaurant}>
                    <label>Name:
                        <input className="textfield"
                               type="text"
                               name="name"
                            // onChange={this.handleChange}
                        />
                    </label>
                    <br />
                    <label>Location:
                        <input className="textfield"
                               type="text"
                               name="location"
                            // onChange={this.handleChange}
                        />
                    </label>
                    <br />
                   <label> Delivery Zones: </label>
                    {
                        this.state.deliveryZones.map(item => (
                            <li>
                                <label>
                                    <input
                                        type="checkbox"
                                        value={item}
                                        onChange={this.handleChange}
                                    /> {item}
                                </label>
                            </li>
                        ))
                    }

                    <div>
                    <Button  className= "button"  value= "customer" variant="secondary" size="lg" type="submit" >Add restaurant </Button> </div>


            </Form>

                <Form className="form-line" onSubmit={this.handleSubmitAddMenuItem}>
                    <label>Name:
                        <input className="textfield"
                               type="text"
                               name="name2"
                            // onChange={this.handleChange}
                        />
                    </label>
                    <label>Price:
                        <input className="textfield"
                               type="text"
                               name="price"
                            // onChange={this.handleChange}
                        />
                    </label>
                    <label>Description:
                        <input className="textfield"
                               type="text"
                               name="description"
                            // onChange={this.handleChange}
                        />
                    </label>

                    <label> Category: </label>
                    <select onChange={this.handleDropdownChange}>
                        {this.state.foodCategories.map(optn => (
                            <option>{optn}</option>
                        ))}

                    </select>
                    <div>
                        <Button  className= "button"  value= "customer" variant="secondary" size="lg" type="submit" >Add menu item </Button> </div>


                </Form>

                <div>
                    <Button className= "button" value= "customer" variant="secondary" size="lg" type="submit" onClick={(e) => this.handleSubmitViewMenu(e) }  href={"/viewmenu"}>View Menu </Button> </div>

                <Button   className="button" value= "customer" variant="secondary" size="lg" type="submit"  >View Orders</Button>
            </div>);
    }
}