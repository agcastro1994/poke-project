interface PokemonSprites {
    frontDefault: string;
    frontShiny: string;
    frontFemale: string;
    frontShinyFemale: string;
    backDefault: string;
    backShiny: string;
    backFemale: string;
    backShinyFemale: string;
    other: {
      'official-artwork': AdditionalArtwork;
      home: AdditionalArtwork;
      dreamWorld: AdditionalArtwork;
    };
  }
  
  interface AdditionalArtwork {
    frontDefault: string;
  }
  
  interface StatExternalDTO {
    id: number;
    name: string;
  }
  
  interface PokemonStatExternalDTO {
    stat: StatExternalDTO;
    effort: number;
    baseStat: number;
  }
  
  interface TypeExternalDTO {
    id: number;
    name: string;
  }
  
  interface PokemonTypeExternalDTO {
    slot: number;
    type: TypeExternalDTO;
  }
  
  interface PokemonSmallDTO {
    id: number;
    name: string;
    height: number;
    weight: number;
    sprites: PokemonSprites;
    stats: PokemonStatExternalDTO[];
    types: PokemonTypeExternalDTO[];
  }