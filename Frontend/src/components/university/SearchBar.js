import { useState } from 'react';

export default function SearchBar({ onSearch }) {
  const [query, setQuery] = useState('');

  const handleInputChange = (e) => {
    const newQuery = e.target.value;
    setQuery(newQuery);
    onSearch(newQuery);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSearch(query);
  };

  return (
    <form onSubmit={handleSubmit} className="flex">
      <input
        type="text"
        value={query}
        onChange={handleInputChange}
        placeholder="Search by name, last name, or role"
        className="border border-gray-300 rounded-full px-4 py-2 w-96"
      />
      <button
        type="submit"
        className="bg-black text-white px-6 py-2 rounded-full -ml-14 hover:bg-gray-900"
      >
        Search
      </button>
    </form>
  );
}
