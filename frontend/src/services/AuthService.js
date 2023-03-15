const API_URL = "http://localhost:8080/api/auth/";

const register = (fullname, username, password) => {
	return fetch(API_URL + "signup", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({ fullname, username, password }),
	}).then(async (response) => {
		if (!response.ok) {
			const errorText = await response.text();
			const error = new Error(errorText);
			error.status = response.status;
			throw error;
		}
		return response.json();
	})
		.then((data) => {
			if (data.token) {
				localStorage.setItem("user", JSON.stringify(data));
			}
			return data;
		})
		.catch((error) => {
			console.error("Error:", error);
			throw error;
		});
};

const login = (username, password) => {
	return fetch(API_URL + "signin", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({ username, password }),
	})
		.then(async (response) => {
			if (!response.ok) {
				const errorText = await response.text();
				const error = new Error(errorText);
				error.status = response.status;
				throw error;
			}
			return response.json();
		})
		.then((data) => {
			if (data.token) {
				localStorage.setItem("user", JSON.stringify(data));
			}
			return data;
		})
		.catch((error) => {
			console.error("Error:", error);
			throw error;
		});
};

const logout = () => {
	localStorage.removeItem("user");
};

const getCurrentUser = () => {
	return JSON.parse(localStorage.getItem("user"));
};

const getAuthToken = () => {
	if (getCurrentUser() && getCurrentUser().token) {
		return 'Bearer ' + getCurrentUser().token;
	} else {
		return {};
	}
}

const validateToken = () => {
	return fetch(API_URL + "validate", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: getCurrentUser().token,
	}).then(async (response) => {
		if (!response.ok) {
			const errorText = await response.text();
			const error = new Error(errorText);
			error.status = response.status;
			throw error;
		}
		return response.json();
	})
		.then((data) => {
			if (data.token) {
				localStorage.setItem("user", JSON.stringify(data));
			}
			return data;
		})
		.catch((error) => {
			console.error("Error:", error);
			throw error;
		});
}

const AuthService = {
	register,
	login,
	logout,
	getCurrentUser,
	getAuthToken,
	validateToken
};

export default AuthService;
