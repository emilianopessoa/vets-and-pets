import React, { useState } from 'react';
import { Button, Form, FormGroup, Label, Input, Alert } from 'reactstrap';
import AppNavbar from '../../AppNavbar';
import { useNavigate } from 'react-router-dom';
import AuthService from '../../services/AuthService';

const Login = () => {
	let navigate = useNavigate();

	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [fullname, setFullname] = useState('');
	const [loading, setLoading] = useState(false);
	const [message, setMessage] = useState('');
	const [fullnameError, setFullnameError] = useState(false);
	const [usernameError, setUsernameError] = useState(false);
	const [passwordError, setPasswordError] = useState(false);

	const onChangeFullname = (e) => {
		setFullnameError(false);
		setFullname(e.target.value);
	};

	const onChangeUsername = (e) => {
		setUsernameError(false);
		setUsername(e.target.value);
	};

	const onChangePassword = (e) => {
		setPasswordError(false);
		setPassword(e.target.value);
	};

	const handleLogin = (e) => {
		e.preventDefault();
		setMessage('');
		setLoading(true);

		if (!username) {
			setUsernameError(true);
		}

		if (!password) {
			setPasswordError(true);
		}

		if (username && password && fullname) {
			AuthService.register(fullname, username, password).then(
				() => {
					navigate('/');
					window.location.reload();
				},
				(error) => {
					const resMessage =
						(error.response &&
							error.response.data &&
							error.response.data.message) ||
						error.message ||
						error.toString();

					setLoading(false);
					setMessage(resMessage);
				}
			);
		} else {
			setLoading(false);
		}
	};

	return (
		<div>
			<AppNavbar />
			<div style={{ padding: "10% 10%" }} className="col-md-12">
				<div style={{ padding: "20px" }} className="card card-container">
					<center><h2>Create a new account</h2></center>
					<Form onSubmit={handleLogin}>
						<FormGroup>
							<Label for="fullname">Full Name</Label>
							<Input
								type="text"
								name="fullname"
								id="fullname"
								value={fullname}
								onChange={onChangeFullname}
								invalid={usernameError}
							/>
						</FormGroup>
						<FormGroup>
							<Label for="username">Username</Label>
							<Input
								type="text"
								name="username"
								id="username"
								value={username}
								onChange={onChangeUsername}
								invalid={usernameError}
							/>
						</FormGroup>
						<FormGroup>
							<Label for="password">Password</Label>
							<Input
								type="password"
								name="password"
								id="password"
								value={password}
								onChange={onChangePassword}
								invalid={passwordError}
							/>
						</FormGroup>
						<FormGroup>
							<Button color="primary" block disabled={loading}>
								{loading && (
									<span className="spinner-border spinner-border-sm"></span>
								)}
								<span>Register</span>
							</Button>
						</FormGroup>
						{message && (
							<Alert color="danger" role="alert">
								{message}
							</Alert>
						)}
					</Form>
				</div>
			</div>
		</div>
	);
};

export default Login;
