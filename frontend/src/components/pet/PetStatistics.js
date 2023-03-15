import React, { useEffect, useState } from 'react';
import { Container, Table } from 'reactstrap';
import AppNavbar from '../../AppNavbar';
import AuthService from '../../services/AuthService';

const PetList = () => {

	const [petstatistic, setPetstatistic] = useState([]);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		setLoading(true);

		fetch('api/petstatistics', {
			headers: {
				'Authorization': AuthService.getAuthToken()
			}
		})
			.then(response => response.json())
			.then(data => {
				setPetstatistic(data);
				setLoading(false);
			})
	}, []);


	if (loading) {
		return <p>Loading...</p>;
	}

	const petList = petstatistic.map(pet => {
		return <tr key={pet.type}>
			<td style={{ whiteSpace: 'nowrap' }}>{pet.type}</td>
			<td style={{ whiteSpace: 'nowrap' }}>{pet.count}</td>
		</tr>
	});

	return (
		<div>
			<AppNavbar />
			<Container fluid>
				<h3>Pets Statistics</h3>
				<Table className="mt-4">
					<thead>
						<tr>
							<th width="20%">Type</th>
							<th width="20%">Total</th>
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