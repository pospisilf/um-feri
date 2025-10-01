const chalk = require('chalk');
const _ = require('lodash');
const express = require('express');

const app = express();
const port = 3000;

// Example endpoint
app.get('/', (req, res) => {
  const message = _.capitalize('Hello world from the Advance Internet Security project proof of concept!');
  res.send(chalk.green(message));
});

// Start the server
app.listen(port, () => {
  console.log(chalk.blue(`Server is running at http://localhost:${port}`));
});
