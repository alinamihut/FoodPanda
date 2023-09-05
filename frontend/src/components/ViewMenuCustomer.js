import React, {Component} from "react";
import './Home.css';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

export default class ViewMenu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            restaurant:localStorage.getItem('restaurant'),
            menuItemsList:[],
            selectedMenuItems:[]}

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmitOrder = this.handleSubmitOrder.bind(this);

    }
    async componentDidMount() {
        fetch('http://localhost:8080/sd_assignment2/restaurant/menuitems'+'/' + this.state.restaurant)
            .then(async response => {
                const menuItemsFromApi = await response.json();
                this.setState({
                    menuItemsList: menuItemsFromApi});})
            .catch(error => {
                console.log(error);
            });


    }

    handleChange(event) {
        var item = event.target.value;
        let menuItems = this.state.selectedMenuItems;
        menuItems.push(item);
        this.setState({
            selectedMenuItems: menuItems,
        });
        console.log(this.state.selectedMenuItems);



    }

    async handleSubmitOrder(event) {
        event.preventDefault();
        const data = {
            restaurant: this.state.restaurant,
            customer: localStorage.getItem('customer'),
            menuItems: this.state.selectedMenuItems
        }

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        };
        fetch('http://localhost:8080/sd_assignment2/restaurant/sendemail', requestOptions)
            .then(
                response => {if (response.status === 400) {
                    alert ('Couldnt place order!');}
                else if (response.status === 201){
                    alert ('Order placed successfully!');
                }
                });


    }
    render() {
        console.log(this.state.menuItemsList)
        return (
            <div className="App" className="centered">
                <h1 >Menu for restaurant {localStorage.getItem('restaurant')}</h1>
                <table>
                    <thead>
                    <tr>
                        <th>Menu Item Name</th>
                        <th>Description</th>
                        <th>Category</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody>

                    {this.state.menuItemsList?.map((item, i) => (
                        <tr key={i}>
                            <td>{item.name}</td>
                            <td>{item.description}</td>
                            <td>{item.category}</td>
                            <td>{item.price}</td>

                        </tr>
                    ))}
                    </tbody>
                </table>
                <h2> Make an order: </h2>
                {
                    this.state.menuItemsList?.map(item => (
                        <li>
                            <label>
                                <input
                                    type="checkbox"
                                    value={item.name}
                                    onChange={this.handleChange}
                                /> {item.name}
                            </label>
                        </li>
                    ))
                }
               
                <h2> My cart: </h2>

                {
                        <ul>
                            {this.state.selectedMenuItems.map( item => {return <li >{item}</li>})}
                        </ul>

                }
                <Button   value= "customer" variant="secondary" size="lg" type="submit" onClick={this.handleSubmitOrder} >Place order</Button>

            </div>);
    }
}