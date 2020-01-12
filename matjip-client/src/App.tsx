import React from 'react';
import './App.css';
import DefaultButton from './components/base/DefaultButton';

const App: React.FC = () => {
  return (
    <div>
      <h1>Hello React.js</h1>
      <DefaultButton>Styled Material-UI</DefaultButton>
    </div>
  );
};

export default App;
