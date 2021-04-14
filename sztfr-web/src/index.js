import React, { Suspense } from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from "./components/App";
import reportWebVitals from './reportWebVitals';
import i18n from './i18n/i18n';

ReactDOM.render(
    <Suspense fallback="loading">
      <App />
    </Suspense>,
  document.getElementById('root')
);

reportWebVitals();
