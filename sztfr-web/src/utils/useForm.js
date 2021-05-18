import { useCallback, useEffect, useState } from "react";

export default function useForm(initialValues, validationRules, onSubmit) {
  const [dirty, setDirty] = useState(false);
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

  const validate = useCallback(() => {
    if (!validationRules || !dirty) {
      return;
    }

    const newErrors = { messages: [], fields: [] };

    for (const key in validationRules) {
      const value = data[key];
      const validation = validationRules[key];

      const required = validation?.required;
      if (
        required &&
        (!value || value === "" || value === {} || value.length === 0)
      ) {
        newErrors.fields.push(key);
        if (!newErrors.messages.includes("validation.required"))
          newErrors.messages.push("validation.required");
      }

      const custom = validation?.isValid;
      if (custom && !custom(data.location)) {
        newErrors.fields.push(key);
        if (!newErrors.messages.includes("validation.required"))
          newErrors.messages.push("validation.required");
      }
    }

    setErrors(newErrors);
  }, [validationRules, dirty, data]);

  useEffect(() => {
    validate();
  }, [validate]);

  // TODO this is annoying
  const handleSubmit = (e) => {
    setDirty(true);
    e.preventDefault();
    if (!validationRules || !dirty) {
      return;
    }

    const newErrors = { messages: [], fields: [] };

    for (const key in validationRules) {
      const value = data[key];
      const validation = validationRules[key];

      const required = validation?.required;
      if (
        required &&
        (!value || value === "" || value === {} || value.length === 0)
      ) {
        newErrors.fields.push(key);
        if (!newErrors.messages.includes("validation.required"))
          newErrors.messages.push("validation.required");
      }

      const custom = validation?.isValid;
      if (custom && !custom(data.location)) {
        newErrors.fields.push(key);
        if (!newErrors.messages.includes("validation.required"))
          newErrors.messages.push("validation.required");
      }
    }

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
