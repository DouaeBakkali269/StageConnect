import axios from 'axios';


const axiosInstance = axios.create({
  baseURL: 'http://localhost:8081', // Replace with your API's base URL
  timeout: 15000, // Optional: Set a timeout (in ms)
  headers: {
    'Content-Type': 'application/json', // Default content type
    Authorization: '', // Optional: Add an auth token
  },
});

export default axiosInstance;