import { useState } from 'react';
import Image from 'next/image';

export default function ProfilePicture({ logo, onUpdate }) {
  const [isEditing, setIsEditing] = useState(false);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      // In a real application, you'd upload the file to your server here
      onUpdate(file);
      setIsEditing(false);
    }
  };

  return (
    <div className="relative">
      <Image
        src={logo}
        alt="University Logo"
        width={200}
        height={200}
        className="rounded-full"
      />
      <button
        onClick={() => setIsEditing(true)}
        className="absolute bottom-0 right-0 bg-blue-500 text-white p-2 rounded-full"
      >
        Edit
      </button>
      {isEditing && (
        <input
          type="file"
          accept="image/*"
          onChange={handleFileChange}
          className="absolute inset-0 opacity-0 cursor-pointer"
        />
      )}
    </div>
  );
}

