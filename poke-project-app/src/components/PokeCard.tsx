import React, { useState } from "react";
import "../PokeCard.css";
import { typeColors } from "../utils/TypesColors";
import { PokemonSmall } from "../interfaces/Pokemon/PokemonSmall";
import shinyColor from "../images/shiny-color.png";
import shiny from "../images/shiny.png";
import { formatName } from "../utils/UtilityFunctions";

interface CardProps {
  pokemon: PokemonSmall;
}

const PokeCard: React.FC<CardProps> = ({ pokemon }) => {
  const waveColor = typeColors[pokemon.types[0].type.name];

  const secondaryWaveColor =
    pokemon.types.length > 1
      ? typeColors[pokemon.types[1].type.name]
      : typeColors[pokemon.types[0].type.name];
  debugger;
  return (
    <div className="card-container">
      <div className="pokemon-card">
        <div
          className="wave -one"
          style={{ background: secondaryWaveColor }}
        ></div>
        <div className="wave -two"></div>
        <div className="wave -three" style={{ background: waveColor }}></div>
        <div className="pokemon-sprite">
          <img
            src={pokemon.sprites.other.officialArtwork.frontDefault}
            alt={formatName(pokemon.name)}
          />
        </div>
        <div className="pokemon-info">
          <h2>{formatName(pokemon.name)}</h2>
          <div className="type-container">
            <div
              className="type-pill"
              style={{ background: typeColors[pokemon.types[0].type.name] }}
            >
              {pokemon.types[0].type.name}
            </div>
            {pokemon.types.length > 1 && (
              <div
                className="type-pill"
                style={{ background: typeColors[pokemon.types[1].type.name] }}
              >
                {pokemon.types[1].type.name}
              </div>
            )}
          </div>
        </div>
      </div>

      <div className="pokemon-card">
        <div
          className="wave -one"
          style={{ background: secondaryWaveColor }}
        ></div>
        <div className="wave -two"></div>
        <div className="wave -three" style={{ background: waveColor }}></div>
        <div className="top-icon">
          <img src={shinyColor} alt={"shiny"} />
        </div>
        <div className="pokemon-sprite">
          <img
            src={pokemon.sprites.other.officialArtwork.frontShiny}
            alt={formatName(pokemon.name)}
          />
        </div>
        <div className="pokemon-info">
          <h2>{formatName(pokemon.name)}</h2>
          <div className="type-container">
            <div
              className="type-pill"
              style={{ background: typeColors[pokemon.types[0].type.name] }}
            >
              {pokemon.types[0].type.name}
            </div>
            {pokemon.types.length > 1 && (
              <div
                className="type-pill"
                style={{ background: typeColors[pokemon.types[1].type.name] }}
              >
                {pokemon.types[1].type.name}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default PokeCard;
