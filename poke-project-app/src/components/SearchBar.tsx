import React, { useState } from 'react';
import { FaSearch } from 'react-icons/fa';
interface SearchBarProp{
    onSearch: ()=>void
    onChange: (value:string)=>void
    value: string
}
export default function SearchBar(props: SearchBarProp) {
  const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    props.onSearch();
  };
  return (
    <form onSubmit={(e)=>handleSearch(e)} style={{minWidth: "35%"}}>
    <div className="search-bar">
      <input
        type="text"
        placeholder="Ingresa una region o el numero de Pokemon"
        value={props.value}
        onChange={(e) => props.onChange(e.target.value)}
      />
      <button className="search-button" type='submit'>
        <FaSearch />
      </button>
    </div>
    </form>
  );
}