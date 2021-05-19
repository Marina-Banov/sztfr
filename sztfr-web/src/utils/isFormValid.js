export default function isFormValid(form, validationRules) {
  const newErrors = { messages: [], fields: [] };

  if (!validationRules) {
    return newErrors;
  }

  for (const key in validationRules) {
    const value = form[key];
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
    if (custom && !custom(form)) {
      newErrors.fields.push(key);
      if (!newErrors.messages.includes("validation.required"))
        newErrors.messages.push("validation.required");
    }
  }

  return newErrors;
}
