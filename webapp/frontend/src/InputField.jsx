import { useState } from 'react';
import './InputField.css'
import { addIssuer, getIssuer, performDIDWebResolution, removeIssuer } from './Utils';

function InputField({name, label, placeholder, btnText, setAPIStatusCode, setResponseData}) {

    const [value, setValue] = useState("")
    const [showResponse, setShowResponse] = useState(false)
    
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
            setAPIStatusCode(response.status)
            setResponseData(response.data)
        } catch (error) {
            console.log("Error: ", error)
            console.log("Error response: ", error.response)
            console.log("Error Data: ", error.response.data)
            setAPIStatusCode(error.response.status)
            setResponseData(error.response.data)
        }
    }

    function handleInput(event) {
        const { value } = event.target;
        setValue(value)
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
        </div>  
    )
}

export default InputField;