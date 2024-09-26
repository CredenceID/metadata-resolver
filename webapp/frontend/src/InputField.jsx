import { useState } from 'react';
import './InputField.css'
import { addIssuer, getIssuer, performDIDWebResolution, removeIssuer } from './Utils';

function InputField({name, label, placeholder, btnText}) {

    const [value, setValue] = useState("")
    const [APIStatusCode, setAPIStatusCode] = useState("")
    const [showResponse, setShowResponse] = useState(false)
    const [responseData, setResponseData] = useState({})

    async function callAPI(name) {
        setResponseData({})

        try {
            let response;

            switch(name) {
                case "getIssuer": 
                    response = await getIssuer(value);
                    break;
                
                case "addIssuer":
                    response = await addIssuer(value);
                    break;

                case "removeIssuer":
                    response = await removeIssuer(value);
                    break;

                case "performDIDWebResolution":
                    response = await performDIDWebResolution(value);
                    break;
            }

            console.log("Response data: ", response.data)
            setShowResponse(true)
            setAPIStatusCode(response.status)
            setResponseData(response.data)
        } catch (error) {
            console.log("Error: ", error)
            console.log("Error response: ", error.response)
            console.log("Error Data: ", error.response.data)
            setShowResponse(true)
            setAPIStatusCode(error.response.status)
            setResponseData(error.response.data)
        }
    }

    function handleInput(event) {
        const { value } = event.target;
        setValue(value)
    }

    function clearResponse() {
        setValue("")
        setAPIStatusCode("")
        setShowResponse(false)
        setResponseData({})
    }

    return (
        <div className="input-compo-wrap">

            <div className='input-field-wrap'>
                <label htmlFor="input-text">{label}</label>
                <div className='input-btn-wrap'>
                    <input 
                        id='input-text' 
                        type="text"
                        value={value}
                        name={name}
                        placeholder={placeholder} 
                        className='input-field'
                        onChange={(e) => handleInput(e)}
                    />

                    <button 
                        name={name}
                        type='button' 
                        className='input-btn'
                        onClick={(e) => callAPI(e.target.name)}
                        >
                            {btnText}
                    </button>
                </div>
            </div>

            { showResponse &&
                <div className="response-wrap">
                    <div className="response-header-wrap">
                    <div className="response-title-wrap">
                        <span>Response</span>
                        <span className={`${APIStatusCode == 200? "response-success-code" : "response-error-code"}`}>{APIStatusCode}</span>
                        <span className={`${APIStatusCode == 200? "response-success-code" : "response-error-code"}`}>
                            {APIStatusCode == 200 && "Success"}
                            {APIStatusCode != 200 && "Error"}
                        </span>
                    </div>
                    <div className="clear-btn-wrap" onClick={clearResponse}>
                        <span>Clear</span>
                    </div>
                    </div>

                    <div className="response-body-wrap">
                        {responseData && <pre>{JSON.stringify(responseData, null, 2)}</pre>}
                        {/* <pre>{responseData}</pre> */}
                    </div> 
                </div>
            }
        </div>  
    )
}

export default InputField;