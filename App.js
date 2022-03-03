/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */
import React from 'react';
import Connection from "./src/components/Connection";
import * as Sentry from "@sentry/react-native";

const App= () => {
      Sentry.init({
            dsn: "https://0dd435aee1234ccdb68eefae030b3110@debug.sariska.io/35",
          });
      throw new Error("My first Sentry error!");
      return (<Connection />);
      
};

export default App;
