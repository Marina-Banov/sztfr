import { useEffect, useState } from "react";

export default function useForm(initialValues) {
  const [inputs, setInputs] = useState(initialValues);

  const handleInputChange = (event) => {
    if (typeof event === "SyntheticInputEvent") {
      event.persist();
    }
    setFormField(event.target.name, event.target.value);
  };

  const setFormField = (name, value) => {
    setInputs((inputs) => ({ ...inputs, [name]: value }));
  };

  useEffect(() => {
    console.log(inputs);
  });

  return { setFormField, handleInputChange, form: inputs };
}
