import React, { useState } from 'react';
import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink } from 'reactstrap'
import { useNavigate } from 'react-router-dom';
import AuthService from "./services/AuthService";

const AppNavbar = () => {
	let navigate = useNavigate();
	const [isOpen, setIsOpen] = useState(false);

	const logout = () => {
		AuthService.logout();
		navigate('/');
		window.location.reload();
	};

	return (
		<Navbar color="dark" dark expand="md">
			<div style={{ position: 'relative' }}>
				<div>
					<h4 className="navbar-text text-white">Vets and Pets</h4>
				</div>
				{AuthService.getCurrentUser() && (
					<div
						className="navbar-text text-white"
						style={{
							position: 'absolute',
							top: 27,
							left: 0,
							fontSize: 13,
							width: '300px',
						}}
					>
						Logged in as: {AuthService.getCurrentUser().fullname}
					</div>
				)}
			</div>
			<NavbarToggler onClick={() => { setIsOpen(!isOpen) }} />
			<Collapse isOpen={isOpen} navbar>
				<Nav className="justify-content-end" style={{ width: "100%" }} navbar>
					<NavItem>
						<NavLink href="/">Home</NavLink>
					</NavItem>
					{!AuthService.getCurrentUser() && (
						<>
							<NavItem>
								<NavLink href="/register">Register</NavLink>
							</NavItem>
							<NavItem>
								<NavLink href="/login">Login</NavLink>
							</NavItem>
						</>
					)}
					{AuthService.getCurrentUser() && (
						<>
							<NavItem>
								<NavLink href="/pets">Pets</NavLink>
							</NavItem>/
							{AuthService.getCurrentUser() && AuthService.getCurrentUser().role === 'Admin' && (
								<>
									<NavItem>
										<NavLink href="/statistics">Pets Statistics</NavLink>
									</NavItem>
								</>
							)}
							<NavItem>
								<NavLink href="/users">Users</NavLink>
							</NavItem>
							<NavItem>
								<NavLink onClick={logout}>Logout</NavLink>
							</NavItem>
						</>
					)}
				</Nav>

			</Collapse>
		</Navbar>
	);
};

export default AppNavbar;