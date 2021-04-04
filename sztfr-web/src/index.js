import React, { Suspense } from 'react';
import ReactDOM from 'react-dom';
import './styles/index.css';
import App from "./components/App";
import reportWebVitals from './reportWebVitals';
import i18n from './i18n/i18n';

ReactDOM.render(
  <React.StrictMode>
    <Suspense fallback="loading">
      <App />
    </Suspense>
  </React.StrictMode>,
  document.getElementById('root')
);

reportWebVitals();
