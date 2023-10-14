  export interface PokemonSprites {
    frontDefault: string;
    frontShiny: string;
    frontFemale: string;
    frontShinyFemale: string;
    backDefault: string;
    backShiny: string;
    backFemale: string;
    backShinyFemale: string;
    other: OtherArtworks
  }

  export interface OtherArtworks{
    officialArtwork: AdditionalArtwork;
    home: AdditionalArtwork;
    dreamWorld: AdditionalArtwork;
  }
  
  export interface AdditionalArtwork {
    frontDefault: string;
    frontShiny: string;
    frontFemale: string;
    frontShinyFemale: string;
  }
  
  export interface Stat{
    id: number;
    name: string;
  }
  
  export  interface PokemonStat {
    stat: Stat;
    effort: number;
    baseStat: number;
  }
  
  export interface Type {
    id: number;
    name: string;
  }
  
  export interface PokemonType {
    slot: number;
    type: Type;
  }
  
  export interface PokemonSmall {
    id: number;
    name: string;
    height: number;
    weight: number;
    sprites: PokemonSprites;
    stats: PokemonStat[];
    types: PokemonType[];
  }