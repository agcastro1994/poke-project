import React, { ChangeEvent,useState } from 'react';
import '../App.css';
import charizard from '../images/charizards.svg';
import PokemonFilterService from '../services/PokeFilterService';
import { PokemonSmall } from '../interfaces/Pokemon/PokemonSmall';
import PokeCard from './PokeCard';
import SearchBar from './SearchBar';

function PokeSearch() { 

    const getPokemon = () => {
        if(!isNaN(Number(search))){
            PokemonFilterService.getPokemon(Number(search)).then(poke => {
                const pokeList: PokemonSmall[] = [poke]
                setPokemon(pokeList)
            })

        }
        else{
            PokemonFilterService.getPokemonByRegion(search).then(pokeList => {
                setPokemon(pokeList)
            })
        }
        
    }

    const [search, setSearch] = useState<string>("")
    const [pokemon, setPokemon] = useState<PokemonSmall[]>([]);

    const onInputChange = (input: string) => {
        const searchValue = input;
        setSearch(searchValue);
    }

    return (
        <div className="App">
        <header className="App-header">
        <img src={charizard} className="App-logo" alt="logo" />
            <p style={{marginTop: "1.5em"}}>
            Poke project 
            </p>

            <SearchBar onChange={(input: string) => onInputChange(input)} onSearch={()=> getPokemon()} value={search}/>

            
            
            {pokemon.length > 0 && 
                pokemon.length === 1 ?
                    pokemon.map((poke)=>{
                        return(<PokeCard 
                            pokemon={poke}
                        />)
                    })
                :      
                <div className="pokemon-grid"> 
                    {
                        pokemon.map((poke)=>{
                            return(<PokeCard 
                                pokemon={poke}
                            />)
                        })
                    }
                </div>
                
            }
  
        </header>
        </div>
  );

}

export default PokeSearch;