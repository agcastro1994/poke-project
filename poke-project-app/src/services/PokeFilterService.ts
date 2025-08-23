import axios, {AxiosResponse} from 'axios';
import {PokemonSmall} from '../interfaces/Pokemon/PokemonSmall'

class PokemonService {
  baseUrl: string

  constructor() {
    this.baseUrl = 'http://localhost:8081/pokemon'; // Update with your API endpoint
  }

    //Not working properly at the moment
    async getAllPokemon<T>(): Promise<T> {
        const response = await axios.get<T>(`${this.baseUrl}/all`);
        return response.data;
    }



    async getPokemon<T>(id: number): Promise<T> {
        const response = await axios.get<T>(`${this.baseUrl}/${id}`);
        return response.data;
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
