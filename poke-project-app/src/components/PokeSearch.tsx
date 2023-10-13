import React, { ChangeEvent,useState } from 'react';
import '../App.css';
import charizard from '../images/charizards.svg';
import PokemonFilterService from '../services/PokeFilterService';

function PokeSearch() { 

    const getPokemon = (e: React.FormEvent<HTMLFormElement>, id: number) => {
        e.preventDefault()
        PokemonFilterService.getPokemon(id)
    }

    const [value, setValue] = useState<number>(1);

    const onNumberChange = (e: ChangeEvent<HTMLInputElement>) => {
        // In general, use Number.isNaN over global isNaN as isNaN will coerce the value to a number first
        // which likely isn't desired
        const value = !Number.isNaN(e.target.valueAsNumber) ? e.target.valueAsNumber : 0;

        setValue(value);
    }

    return (
        <div className="App">
        <header className="App-header">
        <img src={charizard} className="App-logo" alt="logo" />
            <p style={{marginTop: "2em"}}>
            Poke project 
            </p>
            <form onSubmit={(e)=>getPokemon(e,value)}>
                <input
                type="number"
                value={value ?? 1}
                onChange={onNumberChange}
            />
                <button type="submit">Mandale mecha capo</button>
            </form>
        </header>
        </div>
  );

}

export default PokeSearch;