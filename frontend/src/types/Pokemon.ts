// TODO refactor all to pokemon external DTOs
// PokemonExternalDTO
export type Pokemon = {
  id: number;
  name: string;
  baseExperience: number;
  height: number;
  isDefault: boolean;
  order: number;
  weight: number;
  abilities: PokemonAbility[];
  moves: PokemonMove[];
  sprites: PokemonSprites;
  stats: PokemonStat[];
  types: PokemonType[];
  // private List<PokemonStatExternalDTO> stats;
  // private List<PokemonTypeExternalDTO> types;
}
// PokemonAbilityExternaDTO
export type PokemonAbility = {
  isHidden: string;
  slot: number;
  ability: Ability;
}
// AbilityExternalDTO
export type Ability = {
  id: number;
  name: string;
}
// PokemonMoveDTO
export type PokemonMove = {
  move: Move; // private NamedApiResource<MoveDTO> 
}

// NamedApiResource<Move>
export type Move = {
  id: number;
  name: string;
  accuracy: number;
  effectChance: number;
  pp: number;
  priority: number;
  power: number;
  damageClass: MoveDamageClass; // private NamedApiResource<MoveDamageClass>
  type: Type; // private NamedApiResource<TypeDTO> 
}

export type MoveDamageClass = {
  id: number;
  name: string;
  moves: Move[]; // private List<NamedApiResource<Move>>
}

export type PokemonSprites = {
  frontDefault: string;
  frontShiny: string;
  frontFemale: string;
  frontShinyFemale: string;
  backDefault: string;
  backShiny: string;
  backFemale: string;
  backShinyFemale: string;
  other: OtherSprites;
}

export type OtherSprites = {
  officialArtwork: AdditionalArtwork;
  home: AdditionalArtwork;
  dreamWorld: AdditionalArtwork;
}

export type AdditionalArtwork = {
  frontDefault: string;
  frontShiny: string;
  frontFemale: string;
  frontShinyFemale: string;
}

export type PokemonStat = {
  stat: Stat; // private StatExternalDTO 
  effort: number;
  baseStat: number;
}

export type Stat = {
  // id: number;
  name: string;
}

export type PokemonType = {
  slot: number;
  type: Type;
}

// Pokemons Types. Ex: fire, leaf, etc.
export type Type = {
  id: number;
  name: string;
  damageRelations: TypeRelations;
}

export type TypeRelations = {
  noDamageTo: Type[];
  halfDamageT: Type[];
  doubleDamageTo: Type[];
  noDamageFrom: Type[];
  halfDamageFrom: Type[];
  doubleDamageFrom: Type[];
}

type OElementTypeInformation = {
  name: string;
  imgUrl: string;
}

// These const values are for matching with the Pokemon Type's names. The images are gonna be held inside the public assets folder
export const ElementTypeInformation: OElementTypeInformation[] = [
  { name: 'Bug', imgUrl: '' },
  { name: 'Dark', imgUrl: '' },
  { name: 'Dragon', imgUrl: '' },
  { name: 'Electr', imgUrl: '' },
  { name: 'Fairy', imgUrl: '' },
  { name: 'Fight', imgUrl: '' },
  { name: 'Fire', imgUrl: '' },
  { name: 'Flying', imgUrl: '' },
  { name: 'Ghost', imgUrl: '' },
  { name: 'Grass', imgUrl: '' },
  { name: 'Ground', imgUrl: '' },
  { name: 'Ice', imgUrl: '' },
  { name: 'Normal', imgUrl: '' },
  { name: 'Poison', imgUrl: '' },
  { name: 'Psychc', imgUrl: '' },
  { name: 'Rock', imgUrl: '' },
  { name: 'Steel', imgUrl: '' },
  { name: 'Water', imgUrl: '' },
]
