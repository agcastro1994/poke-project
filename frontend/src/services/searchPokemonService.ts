export const searchPokemon = async () => {
  return await fetch('http://localhost:8081/pokemon')
  .then(response => response.json())
  .then(data => console.log("Pokemon list",data))
}