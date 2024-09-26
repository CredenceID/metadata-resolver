import credenceidLogo from './assets/credenceid.svg'
import './App.css'
import InputField from './InputField'

function App() {

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

          <div>
            <h2>Trusted Issuer Registry</h2>
            <div className='input-wrap'>
              <InputField 
                name="getIssuer"
                btnText="Check"
                placeholder="danubetech.com"
                label="Check if Issuer domain is trusted" 
              />
            </div>

            <InputField 
              name="addIssuer"
              btnText="Add"
              placeholder="danubetech.com"
              label="Add Issuer domain to Trusted Registry" 
            />

            <InputField 
              name="removeIssuer"
              btnText="Remove"
              placeholder="danubetech.com"
              label="Remove Issuer domain from Trusted Registry" 
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
            />
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