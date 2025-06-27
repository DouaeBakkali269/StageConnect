import { useState, useEffect, useMemo } from 'react';

export default function FormComponent({ isOpen, onClose, onSubmit, fields, title, submitButtonText, prefillData = null }) {
  // Compute initial form data only when prefillData changes and is not null
  const initialFormData = useMemo(() => {
    if (!prefillData) {
      // If prefillData is null or undefined, initialize with empty values
      return fields.reduce((acc, field) => {
        acc[field.name] = '';
        return acc;
      }, {});
    }
    // If prefillData is provided, use it to prefill the form
    return fields.reduce((acc, field) => {
      acc[field.name] = prefillData[field.name] || '';
      return acc;
    }, {});
  }, [prefillData, fields]); // Recompute when prefillData or fields change

  // Initialize formData state with initialFormData
  const [formData, setFormData] = useState(initialFormData);

  // Update formData only when initialFormData changes (e.g., when prefillData changes)
  useEffect(() => {
    setFormData(initialFormData);
  }, [initialFormData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center p-4">
      <div className="bg-white rounded-lg shadow-lg w-full max-w-2xl max-h-[90vh] overflow-y-auto">
        <div className="p-6">
          <h2 className="text-2xl font-bold mb-6">{title}</h2>
          <form onSubmit={handleSubmit}>
            <div className="space-y-4">
              {fields.map((field) => (
                <div key={field.name}>
                  <label className="block text-gray-700 text-sm font-medium mb-2">
                    {field.placeholder} {field.required && <span className="text-red-500">*</span>}
                  </label>
                  {field.type === 'select' ? (
                    <select
                      name={field.name}
                      value={formData[field.name] || ''}
                      onChange={handleChange}
                      className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                      required={field.required || false}
                    >
                      <option value="">Select {field.placeholder}</option>
                      {field.options.map((option) => (
                        <option key={option.value} value={option.value}>
                          {option.label}
                        </option>
                      ))}
                    </select>
                  ) : field.type === 'textarea' ? (
                    <textarea
                      name={field.name}
                      value={formData[field.name] || ''}
                      onChange={handleChange}
                      placeholder={field.placeholder}
                      required={field.required || false}
                      className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                      rows={4}
                    />
                  ) : (
                    <input
                      type={field.type || 'text'}
                      name={field.name}
                      value={formData[field.name] || ''}
                      onChange={handleChange}
                      placeholder={field.placeholder}
                      required={field.required || false}
                      min={field.min}
                      max={field.max}
                      className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                  )}
                </div>
              ))}
            </div>
            <div className="flex justify-end mt-6">
              <button
                type="button"
                onClick={onClose}
                className="bg-gray-300 text-gray-700 px-4 py-2 rounded-lg mr-2 hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500"
              >
                Cancel
              </button>
              <button
                type="submit"
                className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                {submitButtonText || 'Submit'}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}