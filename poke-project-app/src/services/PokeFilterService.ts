import axios from 'axios';
import {PokemonSmall} from '../interfaces/Pokemon/PokemonSmall'

class PokemonService {
  baseUrl: string

  constructor() {
    this.baseUrl = 'http://localhost:8081/pokemon'; // Update with your API endpoint
  }

  getAllPokemon() {
    return axios.get(`${this.baseUrl}/all`)
      .then((response) => response.data)
      .catch((error) => {
        throw error;
      });
  }

  getPokemon(id:number): Promise<PokemonSmall> {
    return axios.get(`${this.baseUrl}/${id}`)
      .then((response) => {return response.data})
      .catch((error) => {
        throw error;
      });
  }

  getPokemonByRegion(region: string): Promise<PokemonSmall[]> {
    return axios.get(`${this.baseUrl}/filter-params?region=${region.toUpperCase()}`)
      .then((response) => {return response.data})
      .catch((error) => {
        throw error;
      });
  }
}

export default new PokemonService();
