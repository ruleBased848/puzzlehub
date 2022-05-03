import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Game from './pages/Game';
import SignUp from './pages/SignUp';
import SignIn from './pages/SignIn';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Game />} />
        <Route path="signup" element={<SignUp />} />
        <Route path="signin" element={<SignIn />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;