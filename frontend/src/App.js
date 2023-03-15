import React from "react";
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import UserList from './components/user/UserList';
import UserEdit from './components/user/UserEdit';
import UserLogin from './components/user/UserLogin';
import UserRegister from './components/user/UserRegister';
import PetList from './components/pet/PetList';
import PetEdit from './components/pet/PetEdit';
import PetStatistics from './components/pet/PetStatistics';

const App = () => {
	return (
		<Router>
			<Routes>
				<Route exact path="/" element={<Home />} />
				<Route path='/users' exact={true} element={<UserList />} />
				<Route path='/users/:id' element={<UserEdit />} />
				<Route path='/pets' exact={true} element={<PetList />} />
				<Route path='/pets/:id' element={<PetEdit />} />
				<Route path="/login" element={<UserLogin />} />
				<Route path="/register" element={<UserRegister />} />
				<Route path="/statistics" element={<PetStatistics />} />
			</Routes>
		</Router>
	)
}

export default App;