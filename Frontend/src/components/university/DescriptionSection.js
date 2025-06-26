import { useState } from 'react';

export default function DescriptionSection({ data, onUpdate }) {
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState(data);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onUpdate(formData);
    setIsEditing(false);
  };

  if (isEditing) {
    return (
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          className="border border-gray-300 rounded px-4 py-2 w-full"
        />
        <input
          type="number"
          name="foundationYear"
          value={formData.foundationYear}
          onChange={handleChange}
          className="border border-gray-300 rounded px-4 py-2 w-full"
        />
        <input
          type="text"
          name="city"
          value={formData.city}
          onChange={handleChange}
          className="border border-gray-300 rounded px-4 py-2 w-full"
        />
        <input
          type="text"
          name="country"
          value={formData.country}
          onChange={handleChange}
          className="border border-gray-300 rounded px-4 py-2 w-full"
        />
        <textarea
          name="description"
          value={formData.description}
          onChange={handleChange}
          className="border border-gray-300 rounded px-4 py-2 w-full h-32"
        />
        <div>
          <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded mr-2">
            Save
          </button>
          <button
            type="button"
            onClick={() => setIsEditing(false)}
            className="bg-gray-300 text-black px-4 py-2 rounded"
          >
            Cancel
          </button>
        </div>
      </form>
    );
  }

  return (
    <div className="space-y-4">
      <h2 className="text-2xl font-bold">{data.name}</h2>
      <p>Founded: {data.foundationYear}</p>
      <p>Location: {data.city}, {data.country}</p>
      <p>Majors: {data.majors.join(', ')}</p>
      <p>{data.description}</p>
      <button
        onClick={() => setIsEditing(true)}
        className="bg-blue-500 text-white px-4 py-2 rounded"
      >
        Edit
      </button>
    </div>
  );
}

