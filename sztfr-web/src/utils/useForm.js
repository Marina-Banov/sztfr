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
      if (required?.value && (!value || !value.length)) {
        newErrors.fields.push(key);
        newErrors.messages.push(required.message);
      }

      const pattern = validation?.pattern;
      if (pattern?.value && !RegExp(pattern.value).test(value)) {
        newErrors.fields.push(key);
        newErrors.messages.push(pattern.message);
      }

      const custom = validation?.custom;
      if (custom?.isValid && !custom.isValid(value)) {
        newErrors.fields.push(key);
        newErrors.messages.push(custom.message);
      }
    }

    setErrors(newErrors);
  }, [dirty, data, validationRules]);

  useEffect(() => {
    validate();
  }, [validate]);

  const handleSubmit = (e) => {
    setDirty(true);
    e.preventDefault();
    validate();
    if (errors.fields.length === 0) {
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
