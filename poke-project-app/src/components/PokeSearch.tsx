import React, { ChangeEvent,useState } from 'react';
import '../App.css';
import charizard from '../images/charizards.svg';
import PokemonFilterService from '../services/PokeFilterService';
import { PokemonSmall } from '../interfaces/Pokemon/PokemonSmall';
import PokeCard from './PokeCard';

function PokeSearch() { 

    const getPokemon = (e: React.FormEvent<HTMLFormElement>, id: number) => {
        e.preventDefault()
        if(region !== ""){
            PokemonFilterService.getPokemonByRegion(region).then(pokeList => {
                setPokemon(pokeList)
            })
        }
        else{
            PokemonFilterService.getPokemon(id).then(poke => {
                const pokeList: PokemonSmall[] = [poke]
                setPokemon(pokeList)
            })
        }
        
    }

    const [value, setValue] = useState<number>(1);
    const [region, setRegion] = useState<string>("")
    const [pokemon, setPokemon] = useState<PokemonSmall[]>([]);

    const onNumberChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value =  e.target.valueAsNumber;
        setValue(value);
    }

    const onRegionChange = (e: ChangeEvent<HTMLInputElement>) => {
        const regionSelected =  e.target.value;
        setRegion(regionSelected);
    }

    return (
        <div className="App">
        <header className="App-header">
        <img src={charizard} className="App-logo" alt="logo" />
            <p style={{marginTop: "1.5em"}}>
            Poke project 
            </p>
            <form onSubmit={(e)=>getPokemon(e,value)}>
                <label htmlFor="pokedexNumber">Pokedex Number:</label>
                <input
                disabled={region !== ""}
                min={0} 
                id={"pokedexNumber"}               
                type="number"
                value={value ?? 1}
                onChange={onNumberChange}                
                />
                <label htmlFor="region">Region:</label>
                <input 
                id={"region"}                      
                type="text"
                value={region}
                onChange={onRegionChange}
                />

                <button type="submit">Mandale mecha capo</button>
            </form>
            
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