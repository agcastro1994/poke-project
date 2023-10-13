import axios from 'axios';

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

  getPokemon(id:number) {
    return axios.get(`${this.baseUrl}/${id}`)
      .then((response) => console.log(response.data))
      .catch((error) => {
        throw error;
      });
  }
}

export default new PokemonService();
