# poke-project

This application is meant to help you build a winning Pokemon team.

Search a Pokemon from the list, choose it to get an indeep sight into its detailed information.

Then choose an enemy to check who has the best winnning chance.

## How to run the poke-project
Requirements:
- JDK 17
- node >18.14.1

Start the backend application
```
mvn clean install [-DskipTests]
cd ./backend/target
mvn sping-boot:start
```

Start the frontend application
```
cd ./frontend
npm run dev
```

## How do it work ?

--> trae un pokemon especifico

```
http://localhost:8081/pokemon/{nro_pokedex}  
```

---> Ejemplo que utiliza y aplica todos los filtros que tengo hasta ahora ..funciona con cualquier combinacion de esos filtros (puedes usar 1, 2..todos etc):

```
http://localhost:8081/pokemon/filter-params?region=KANTO&has_move=thunder-punch&primary_stat=special-attack&type=fire&lower_stat=defense&ability=flame-body
```

Filtros:
region=
type=
has_move=
primary_stat=
lower_stat=
ability=

ese ejemplo particular devuelve un unico pokemon que cumple con todo que es Magmar

