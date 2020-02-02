import React from 'react';
import { Route, Switch } from 'react-router-dom';
import LoginPage from './pages/LoginPage';

export const App: React.FC = () => {
  return (
    <>
      <Switch>
        <Route path="/" component={LoginPage} />
      </Switch>
    </>
  );
};

export default App;
