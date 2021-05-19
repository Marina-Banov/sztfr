import { useEffect, useState } from "react";
import isFormValid from "utils/isFormValid";

export default function useForm(initialValues, validationRules, onSubmit) {
  const [submitted, setSubmitted] = useState(false);
  const [data, setData] = useState(initialValues);
  const [errors, setErrors] = useState({ messages: [], fields: [] });

  const handleInputChange = (event) => {
    if (typeof event === "SyntheticInputEvent") {
      event.persist();
    }
    setFormField(event.target.name, event.target.value);
  };

  const setFormField = (name, value) => {
    setData((inputs) => ({ ...inputs, [name]: value }));
  };

  useEffect(() => {
    if (submitted) {
      setErrors(isFormValid(data, validationRules));
    }
  }, [data, submitted, validationRules]);

  const handleSubmit = (e) => {
    e.preventDefault();
    setSubmitted(true);
    const newErrors = isFormValid(data, validationRules);
    setErrors(newErrors);
    if (newErrors.fields.length === 0) {
      onSubmit();
    }
  };

  return {
    data,
    handleInputChange,
    setFormField,
    handleSubmit,
    errors,
  };
}
