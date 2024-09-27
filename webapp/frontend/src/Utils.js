import axios from 'axios';
import { BASE_URL } from './URLConfig';

export async function getIssuer(param) {
    return axios.get(`${BASE_URL}/registry`, {
        params: { 
            'param': param
        },
    });
}

export async function addIssuer(param) {
    return axios.post(`${BASE_URL}/registry`, null, 
        {
            params: {  'param': param }
        });
}

export async function removeIssuer(param) {
    return axios.delete(`${BASE_URL}/registry`, {
        params: { 
            'param': param
        },
    });
}

export async function performDIDWebResolution(param) {
    return axios.get(`${BASE_URL}/identifiers`, {
        params: { 
            'param': param
        },
    });
}