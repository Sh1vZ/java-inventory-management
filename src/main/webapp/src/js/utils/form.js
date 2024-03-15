export const disableForm = (form) => {
  for (const element of form.elements) {
    element.disabled = true;
  }
};
export const enableForm = (form) => {
  for (const element of form.elements) {
    element.disabled = false;
  }
};
export const serializeForm = (form) => {
  const formData = new FormData(form);
  const formDataObject = {};
  for (let [key, value] of formData.entries()) {
    formDataObject[key] = value;
  }
  return formDataObject;
};