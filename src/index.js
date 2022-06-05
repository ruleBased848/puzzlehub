import { signIn } from "./states";
import React from "react";
import { createRoot } from "react-dom/client";
import App from "./App";
import "./index.css";

createRoot(document.getElementById("root")).render(<App />);
fetch("/members/cookieauthentication")
  .then((response) => response.json())
  .then((json) => signIn.set(json.ok));