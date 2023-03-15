import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from '../../AppNavbar';
import { Link } from 'react-router-dom';
import AuthService from '../../services/AuthService';

const PetList = () => {

	const [pets, setPets] = useState([]);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		setLoading(true);

		fetch('api/pets', {
			headers: {
				'Authorization': AuthService.getAuthToken()
			}
		})
			.then(response => response.json())
			.then(data => {
				setPets(data);
				setLoading(false);
			})
	}, []);

	const remove = async (id) => {
		await fetch(`/api/pet/${id}`, {
			method: 'DELETE',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				'Authorization': AuthService.getAuthToken()
			}
		}).then(() => {
			let updatedPets = [...pets].filter(i => i.id !== id);
			setPets(updatedPets);
		});
	}

	if (loading) {
		return <p>Loading...</p>;
	}

	const petList = pets.map(pet => {
		return <tr key={pet.id}>
			<td style={{ whiteSpace: 'nowrap' }}>{pet.name}</td>
			<td style={{ whiteSpace: 'nowrap' }}>{pet.type}</td>
			<td style={{ whiteSpace: 'nowrap' }}>{pet.userFullname}</td>
			<td>
				<ButtonGroup>
					<Button size="sm" style={{width:"80px"}} color="primary" tag={Link} to={"/pets/" + pet.id}>Edit</Button>
					<Button size="sm" style={{width:"80px",marginLeft:"1px"}} color="danger" onClick={() => remove(pet.id)}>Delete</Button>
				</ButtonGroup>
			</td>
		</tr>
	});

	return (
		<div>
			<AppNavbar />
			<Container fluid>
				<div className="float-end">
					<Button color="success" tag={Link} to="/pets/new">Add Pet</Button>
				</div>
				<h3>Pets</h3>
				<Table className="mt-4">
					<thead>
						<tr>
							<th width="20%">Name</th>
							<th width="20%">Type</th>
							<th width="20%">User</th>
							<th width="10%">Actions</th>
						</tr>
					</thead>
					<tbody>
						{petList}
					</tbody>
				</Table>
			</Container>
		</div>
	);
};

export default PetList;