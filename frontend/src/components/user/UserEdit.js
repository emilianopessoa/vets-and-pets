import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../../AppNavbar';
import AuthService from '../../services/AuthService';

const UserEdit = () => {
	const navigate = useNavigate();
	const initialFormState = {
		fullname: '',
		username: '',
		role: 'User',
		password: ''
	};
	const [user, setUser] = useState(initialFormState);
	const { id } = useParams();

	useEffect(() => {
		if (id !== 'new') {
			fetch(`/api/user/${id}`, {
				headers: {
					'Authorization': AuthService.getAuthToken()
				}
			}).then(response => response.json()).then(data => setUser(data));
		}
	}, [id, setUser]);

	const handleChange = (event) => {
		const { name, value } = event.target
		setUser({ ...user, [name]: value })
	}

	const handleSubmit = async (event) => {
		event.preventDefault();

		await fetch(`/api/user${user.id ? `/${user.id}` : ''}`, {
			method: (user.id) ? 'PUT' : 'POST',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				'Authorization': AuthService.getAuthToken()
			},
			body: JSON.stringify(user)
		});
		setUser(initialFormState);
		navigate('/users');
	}

	const title = <h2>{user.id ? 'Edit User' : 'Add User'}</h2>;

	return (<div>
		<AppNavbar />
		<Container>
			{title}
			<Form onSubmit={handleSubmit}>
				<FormGroup>
					<Label for="fullname">Full Name</Label>
					<Input required="true" type="text" name="fullname" id="fullname" value={user.fullname || ''}
						onChange={handleChange} autoComplete="fullname" />
				</FormGroup>
				<FormGroup>
					<Label for="username">Username</Label>
					<Input disabled readOnly required="true" type="text" name="username" id="username" value={user.username || ''}
						onChange={handleChange} autoComplete="username" />
				</FormGroup>
				<FormGroup>
					<Label for="password">Password</Label>
					<Input disabled={AuthService.getCurrentUser().role !== "Admin"} required="true" type="password" name="password" id="password" value={user.password || ''}
						onChange={handleChange} autoComplete="password" />
				</FormGroup>
				<FormGroup>
					<Label for="role">Type</Label>
					<Input disabled={AuthService.getCurrentUser().role !== "Admin"} required="role" type="select" name="role" id="role" value={user.role || ''} onChange={handleChange}>
						<option value="">Select User Role</option>
						<option value="Admin">Admin</option>
						<option value="User">User</option>
					</Input>
				</FormGroup>
				<FormGroup>
					<Button color="primary" type="submit">Save</Button>{' '}
					<Button color="secondary" tag={Link} to="/users">Cancel</Button>
				</FormGroup>
			</Form>
		</Container>
	</div>
	)
};

export default UserEdit;