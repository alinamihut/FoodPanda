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
        this.handleSubmitExportMenu = this.handleSubmitExportMenu.bind(this);

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
        let menuItems = this.state.menuItemsList.name;
        menuItems.push(item);
        this.setState({
            selectedMenuItems: menuItems,
        });
        console.log(this.state.selectedMenuItems);



    }

    async handleSubmitExportMenu(event) {
        event.preventDefault();

         fetch('http://localhost:8080/sd_assignment2/restaurant/exportmenu/' + this.state.restaurant)
             .then(
                 response => {if (response.status === 400) {
                     alert ('Couldnt export menu!');}
                 else if (response.status === 201){
                     alert ('Menu exported successfully!');
                 }
                 });}



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
                <div> <Button  className= "button"  value= "customer" variant="secondary" size="lg" type="submit" onClick={(e) => this.handleSubmitExportMenu(e)} > Export Menu To PDF </Button> </div>


            </div>);
    }
}