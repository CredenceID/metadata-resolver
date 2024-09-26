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
    console.log("These is the request query: ", req.query.param)

    try {
      const URL = `${BASE_URL}/registry/${param}`;
      console.log("URL: ", URL);

      const requestHeaders = {
        'Content-Type': 'application/json'
      };

      const responseData = await axios.get(URL);
      console.log("These is the response data: ", responseData)
  
      // Return the fetched data as JSON
      return res.status(200).json(responseData)

    } catch (error) {
      // Handling any error
      console.log('Error response caught in node backend: ', error.response)
      console.error('Error data caught in node backend: ', error.response?.data);
  
      // Status code and message to send back to the client
      const statusCode = error.response?.status || 500;
      const errorMessage = error.response?.data || { error: 'InternalServerError', message: 'An unexpected error occurred' };
  
      // Send the error response to the client
      return res.status(statusCode).json(error.response?.data);
    }
  })

app.post("/registry",  async (req, res) => {
  const param = req.query.param;
  console.log("These is the request query: ", req.query.param);

  try {
    const URL = `${BASE_URL}/registry/${param}`;
    console.log("URL: ", URL);
    console.log("Issuer data: ", req.body)

    const requestHeaders = {
      'Content-Type': 'application/json'
    };

    const responseData = await axios.post(URL, null);
    console.log("These is the response data: ", responseData.status)

    // Return the fetched data as JSON
    return res.status(200).json();

  } catch (error) {
    // console.log("Error response caught in node backend: ", error.response)
    console.error('Error data caught in node backend: ', error.response?.data);
    const statusCode = error.response?.status || 500;
    return res.status(statusCode).json(error);
  }
})

app.delete("/registry",  async (req, res) => {
  const param = req.query.param;
  console.log("These is the request query: ", req.query.param);

  try {
    const URL = `${BASE_URL}/registry/${param}`;
    console.log("URL: ", URL);

    const responseData = await axios.delete(URL);
    console.log("These is the response data: ", responseData)

    // Return the fetched data as JSON
    return res.status(200).json()
  } catch (error) {
    // Handling any error
    console.log("Error response caught in node backend: ", error.response)
    console.error('Error data caught in node backend: ', error.response?.data);

    // Status code and message to send back to the client
    const statusCode = error.response?.status || 500;
    return res.status(statusCode).json(error);
  }
})

app.get("/identifiers", async (req, res) => {
  const param = req.query.param;
  console.log("These is the request query: ", req.query.param);

  try {
    const URL = `${BASE_URL}/identifiers/${param}`;
    console.log("URL: ", URL);

    const responseData = await axios.get(URL);
    console.log("These is the response data: ", responseData)
    

    // Return the fetched data as JSON
    return res.status(200).json(responseData)
  } catch (error) {

    if (error.response) {
      console.log("Error caught on Backend: ", error.response)
      console.log("Error caught on Backend: ", error.response.data)
      return res.status(error.response.status).json({
        status: error.response.status,
        message: error.response.statusText,
        data: error.response.data,
      })
    } else {
      return res.status(500).json({
        status: 500,
        message: "An Error Occured while making the request",
        data: error.response.data
      })
    }

    // // Handling any error
    // console.log("Error response caught in node backend: ", error.response)
    // console.error('Error data caught in node backend: ', error.response?.data);

    // // Status code and message to send back to the client
    // const statusCode = error.response?.status || 500;
    // return res.status(statusCode).json(error);
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