import React, {useState} from "react";
import "../PokeCard.css";
import { typeColors } from "../utils/TypesColors";
import { PokemonSmall } from "../interfaces/Pokemon/PokemonSmall";
import shinyColor from '../images/shiny-color.png'
import shiny from '../images/shiny.png'
import {formatName} from '../utils/UtilityFunctions'


interface CardProps {
  pokemon: PokemonSmall
}

const PokeCard: React.FC<CardProps> = ({
  pokemon

}) => {
  const waveColor = typeColors[pokemon.types[0].type.name];
  const [showShiny, setShowShiny] = useState<Boolean>(false)

  const secondaryWaveColor = pokemon.types.length > 1
    ? typeColors[pokemon.types[1].type.name]
    : typeColors[pokemon.types[0].type.name];
  debugger
  return (
    <div className="card-container">
      <div className="pokemon-card">
        <div
          className="wave -one"
          style={{ background: secondaryWaveColor }}
        ></div>
        <div className="wave -two"></div>
        <div className="wave -three" style={{ background: waveColor }}></div>
        <div className="top-icon" onClick={()=>setShowShiny(!showShiny)}>
          <img src={ showShiny ? shiny : shinyColor} alt={"shiny-toggle"} />
        </div>
        <div className="pokemon-sprite">
          <img src={ showShiny ? pokemon.sprites.other.officialArtwork.frontShiny
             : pokemon.sprites.other.officialArtwork.frontDefault} alt={formatName(pokemon.name)} />
        </div>
        <div className="pokemon-info">
          <h2>{formatName(pokemon.name)}</h2>
          <p>{pokemon.types[0].type.name}</p>
          {pokemon.types.length > 1 && <p>{pokemon.types[1].type.name}</p>}
        </div>
      </div>

      <div className="pokemon-card">

        <div className="pokemon-minisprite">
          <img src={pokemon.sprites.frontDefault} alt={formatName(pokemon.name)} />
          <img src={pokemon.sprites.frontShiny} alt={formatName(pokemon.name)} />
        </div>
        <div className="pokemon-info">
          {pokemon.stats.map(stat=>{
            return(
              <p>{stat.stat.name} : {stat.baseStat}</p>
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default PokeCard;
