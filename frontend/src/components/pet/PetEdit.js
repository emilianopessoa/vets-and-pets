import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../../AppNavbar';
import AuthService from '../../services/AuthService';

const PetEdit = () => {
	const initialFormState = {
		name: '',
		type: '',
		username: AuthService.getCurrentUser().username,
		userFullname: AuthService.getCurrentUser().fullname,
	};
	const [pet, setPet] = useState(initialFormState);
	const navigate = useNavigate();
	const { id } = useParams();

	useEffect(() => {
		if (id !== 'new') {
			fetch(`/api/pet/${id}`, {
			headers: {
				'Authorization': AuthService.getAuthToken()
			}
		}).then(response => response.json()).then(data => setPet(data));
		}
	}, [id, setPet]);

	const handleChange = (event) => {
		const { name, value } = event.target
		setPet({ ...pet, [name]: value })
	}

	const handleSubmit = async (event) => {
		event.preventDefault();

		await fetch(`/api/pet${pet.id ? `/${pet.id}` : ''}`, {
			method: (pet.id) ? 'PUT' : 'POST',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				'Authorization': AuthService.getAuthToken()
			},
			body: JSON.stringify(pet)
		});
		setPet(initialFormState);
		navigate('/pets');
	}

	const title = <h2>{pet.id ? 'Edit Pet' : 'Add Pet'}</h2>;

	return (<div>
		<AppNavbar />
		<Container>
			{title}
			<Form onSubmit={handleSubmit}>
				<FormGroup>
					<Label for="name">Name</Label>
					<Input required="true" type="text" name="name" id="name" value={pet.name || ''}
						onChange={handleChange} autoComplete="name" />
				</FormGroup>
				<FormGroup>
					<Label for="type">Type</Label>
					<Input required="true" type="select" name="type" id="type" value={pet.type || ''} onChange={handleChange}>
						<option value="">Select Pet Type</option>
						<option value="Dog">Dog</option>
						<option value="Cat">Cat</option>
						<option value="Bird">Bird</option>
					</Input>
				</FormGroup>
				<FormGroup>
					<Label for="user">User</Label>
					<Input readOnly required="true" type="text" name="user" id="user" value={pet.userFullname || ''}
						onChange={handleChange} autoComplete="user" />
				</FormGroup>

				<FormGroup>
					<Button color="primary" type="submit">Save</Button>{' '}
					<Button color="secondary" tag={Link} to="/pets">Cancel</Button>
				</FormGroup>
			</Form>
		</Container>
	</div>
	)
};

export default PetEdit;