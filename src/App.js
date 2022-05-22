import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Game from './pages/Game';
import SignUp from './pages/SignUp';
import SignIn from './pages/SignIn';
import Upload from './pages/Upload';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Game />} />
        <Route path="index.html" element={<Game />} />
        <Route path="signup" element={<SignUp />} />
        <Route path="signin" element={<SignIn />} />
        <Route path="upload" element={<Upload />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;