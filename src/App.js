import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Game from './Game';
import SignUp from './SignUp';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Game />} />
        <Route path="signup" element={<SignUp />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;