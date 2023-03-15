import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from '../../AppNavbar';
import { Link } from 'react-router-dom';
import AuthService from '../../services/AuthService';

const UserList = () => {
	const [users, setUsers] = useState([]);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		setLoading(true);
		fetch('api/users', {
			headers: {
				'Authorization': AuthService.getAuthToken()
			}
		}).then(response => response.json())
			.then(data => {
				setUsers(data);
				setLoading(false);
			})
	}, []);

	const remove = async (id) => {
		await fetch(`/api/user/${id}`, {
			method: 'DELETE',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				'Authorization': AuthService.getAuthToken()
			}
		}).then(() => {
			let updatedUsers = [...users].filter(i => i.id !== id);
			setUsers(updatedUsers);
		});
	}

	if (loading) {
		return <p>Loading...</p>;
	}

	const userList = users.map(user => {
		return <tr key={user.id}>
			<td style={{ whiteSpace: 'nowrap' }}>{user.fullname}</td>
			<td style={{ whiteSpace: 'nowrap' }}>{user.username}</td>
			<td style={{ whiteSpace: 'nowrap' }}>{user.role}</td>
			<td>
				<ButtonGroup>
					<Button size="sm" style={{ width: "80px" }} color="primary" tag={Link} to={"/users/" + user.id}>Edit</Button>
					<Button size="sm" style={{ width: "80px", marginLeft: "1px" }} color="danger" onClick={() => remove(user.id)}>Delete</Button>
				</ButtonGroup>
			</td>
		</tr>
	});

	return (
		<div>
			<AppNavbar />
			<Container fluid>
				{AuthService.getCurrentUser().role !== "Admin" && (
					<div className="float-end">
						<Button color="success" tag={Link} to="/users/new">
							Add User
						</Button>
					</div>
				)}
				<div className="float-end">
					<Button color="success" tag={Link} to="/users/new">Add User</Button>
				</div>
				<h3>Users</h3>
				<Table className="mt-4">
					<thead>
						<tr>
							<th width="20%">Full Name</th>
							<th width="20%">Username</th>
							<th width="20%">Role</th>
							<th width="10%">Actions</th>
						</tr>
					</thead>
					<tbody>
						{userList}
					</tbody>
				</Table>
			</Container>
		</div>
	);
};

export default UserList;