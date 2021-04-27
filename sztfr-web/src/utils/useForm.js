import { useEffect, useState } from "react";

export default function useForm(initialValues) {
  const [inputs, setInputs] = useState(initialValues);

  const handleInputChange = (event) => {
    if (typeof event === "SyntheticInputEvent") {
      event.persist();
    }
    setInputs((inputs) => ({
      ...inputs,
      [event.target.name]: event.target.value,
    }));
  };

  useEffect(() => {
    console.log(inputs);
  });

  return { handleInputChange, form: inputs };
}
