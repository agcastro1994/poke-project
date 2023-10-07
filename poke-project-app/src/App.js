import charizard from './charizards.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={charizard} className="App-logo" alt="logo" />
        <p>
          Poke project 
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
