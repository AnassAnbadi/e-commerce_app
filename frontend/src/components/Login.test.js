import { render, screen } from '@testing-library/react';
import Login from './Login'; // Adaptez le chemin vers votre composant
import { BrowserRouter } from 'react-router-dom';

test('renders login form', () => {
  render(
    <BrowserRouter>
      <Login />
    </BrowserRouter>
  );
  // Vérifie si le texte "Login" ou un bouton existe
  const linkElement = screen.getByText(/Login/i); // Ou cherchez un label/bouton spécifique
  expect(linkElement).toBeInTheDocument();
});