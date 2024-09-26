import { useState } from 'react';
import credenceidLogo from './assets/credenceid.svg'
import './App.css'
import InputField from './InputField'

function App() {

  const [APIStatusCode, setAPIStatusCode] = useState("")
  const [responseData, setResponseData] = useState({})

  function clearResponse() {
    setAPIStatusCode("")
    setResponseData({})
  }

  return (
    <>
      <div className="wrapper">
        <header>
          <div className='logo-wrap'>
            <img src={credenceidLogo} alt="logo" className='logo' />
          </div>
        </header>

        <main className='main'>
          <h1 className='title'>W3C VC Metadata Resolver</h1>

          <div className="main-content-wrap">
            <div className="form-content-wrap">
              <div>
                <h2>Trusted Issuer Registry</h2>
                <div className='input-wrap'>
                  <InputField 
                    name="getIssuer"
                    btnText="Check"
                    placeholder="danubetech.com"
                    label="Check if Issuer domain is trusted" 

                    setAPIStatusCode={setAPIStatusCode}
                    setResponseData={setResponseData}
                  />
                </div>

                <InputField 
                  name="addIssuer"
                  btnText="Add"
                  placeholder="danubetech.com"
                  label="Add Issuer domain to Trusted Registry" 

                  setAPIStatusCode={setAPIStatusCode}
                  setResponseData={setResponseData}
                />

                <InputField 
                  name="removeIssuer"
                  btnText="Remove"
                  placeholder="danubetech.com"
                  label="Remove Issuer domain from Trusted Registry" 

                  setAPIStatusCode={setAPIStatusCode}
                  setResponseData={setResponseData}
                />
              </div>

              <div className="separator"></div>

              <div>
                <h2>Web DID Resolver</h2>

                <InputField 
                  name="performDIDWebResolution"
                  label="Perform DID Web resolution" 
                  placeholder="did:web:danubetech.com"
                  btnText="Submit"

                  setAPIStatusCode={setAPIStatusCode}
                  setResponseData={setResponseData}
                />
              </div>
            </div>
            
            <div className="response-wrap">
              <div className="response-header-wrap">
              <div className="response-title-wrap">
                  <span>Response</span>
                  <span className={`${APIStatusCode == 200? "response-success-code" : "response-error-code"}`}>{APIStatusCode}</span>
                  <span className={`${APIStatusCode == 200? "response-success-code" : "response-error-code"}`}>
                      {APIStatusCode == 200 && "Success"}
                      {(APIStatusCode != "" && APIStatusCode != 200) && "Error"}
                  </span>
              </div>
              <div className="clear-btn-wrap" onClick={clearResponse}>
                  <span>Clear</span>
              </div>
              </div>

              <div className="response-body-wrap">
                  {responseData && <pre>{JSON.stringify(responseData, null, 2)}</pre>}
              </div> 
            </div>
          </div>
        </main>

        <footer>
          <p className="footer-text">Contact us</p>
          <a href="mailto:connect@credenceid.com">connect@credenceid.com</a>
        </footer>
      </div>
    </>
  )
}

export default App