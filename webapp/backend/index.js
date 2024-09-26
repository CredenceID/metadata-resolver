const express = require("express");
const cors = require("cors");
const path = require("path");
const axios = require('axios');
const http = require('http');

const { PORT, BASE_URL } = require("./config");

const app = express();
app.use(cors());
app.use(express.json());

app.use(express.static(path.join(__dirname, "../frontend/dist")));

app.get("/registry", async (req, res) => {
    const param = req.query.param;
    console.log("These is the request query: ", param)

    try {
      const URL = `${BASE_URL}/registry/${param}`;
      console.log("URL: ", URL);

      const responseData = await axios.get(URL);
      console.log("These is the response data: ", responseData)
  
      return res.status(responseData.status).json(responseData.data)

    } catch (error) {
      console.log("The error: ", error)

      if (error.response) {
        return res.status(error.response.status).json(error.response.data)
      } else {
        return res.status(error.status).json(error.message)
      }
    }
  })

app.post("/registry",  async (req, res) => {
  const param = req.query.param;
  console.log("These is the request query: ", param);

  try {
    const URL = `${BASE_URL}/registry/${param}`;
    console.log("URL: ", URL);

    const responseData = await axios.post(URL, null);
    console.log("These is the response data: ", responseData.status)

    return res.status(responseData.status).json();

  } catch (error) {
    console.log("The error: ", error)

    if (error.response) {
      return res.status(error.response.status).json(error.response.data)
    } else {
      return res.status(error.status).json(error.message)
    }
  }
})

app.delete("/registry",  async (req, res) => {
  const param = req.query.param;
  console.log("These is the request query: ", param);

  try {
    const URL = `${BASE_URL}/registry/${param}`;
    console.log("URL: ", URL);

    const responseData = await axios.delete(URL);
    console.log("These is the response data: ", responseData)

    return res.status(responseData.status).json()
  } catch (error) {
    console.log("The error: ", error)

    if (error.response) {
      return res.status(error.response.status).json(error.response.data)
    } else {
      return res.status(error.status).json(error.message)
    }
  }
})

app.get("/identifiers", async (req, res) => {
  const param = req.query.param;
  console.log("These is the request query: ", param);

  try {
    const URL = `${BASE_URL}/identifiers/${param}`;
    console.log("URL: ", URL);

    const responseData = await axios.get(URL);
    console.log("These is the response data: ", responseData)
    
    return res.status(responseData.status).json(responseData.data)

  } catch (error) {
    console.log("The error: ", error)

    if (error.response) {
      return res.status(error.response.status).json(error.response.data)
    } else {
      console.log("Network or unexpected error: ", error.message);
      return res.status(error.status).json(error.message)
    }
  }
})

app.get("*", (req, res) => {
    res.sendFile(
      path.resolve(__dirname, "../frontend", "dist", "index.html")
    );
  });

const httpServer = http.createServer(app);
console.log(`Listening on port: ${PORT}`)
httpServer.listen(PORT);

process.on("SIGTERM", () => {
    console.info("SIGTERM signal received.");
    process.exit(0);
});