import React from 'react';

const Card = ({ title, specifications, buttons, extraContent, imageSrc }) => {
  const getButtonColor = (label) => {
    const lowerLabel = label.toLowerCase();
    if (['validate', 'accept', 'apply'].includes(lowerLabel)) return 'bg-green-500 hover:bg-green-600';
    if (['refuse', 'reject'].includes(lowerLabel)) return 'bg-gray-500 hover:bg-gray-600';
    if (lowerLabel === 'pending') return 'bg-orange-500 hover:bg-orange-600';
    return 'bg-blue-500 hover:bg-blue-600'; // Default color
  };

  return (
    <div className="w-full aspect-w-5 aspect-h-4">
      <div className="bg-white rounded-lg shadow-md p-6 w-full h-full flex flex-col">
        {/* Image container */}
        {imageSrc && (
          <div className="flex justify-center items-center mb-4">
            <div className="w-16 h-16 rounded-full overflow-hidden">
              <img
                src={imageSrc}
                alt="Card"
                className="w-full h-full object-cover"
              />
            </div>
          </div>
        )}
        <h2 className="text-xl font-semibold mb-4 line-clamp-2 text-center">{title}</h2>
        <div className="grid grid-cols-2 gap-4 mb-6 flex-grow">
          {specifications.map((spec, index) => (
            <div key={index} className="flex flex-col">
              <span className="font-medium text-sm text-gray-600 mb-1">{spec.label}</span>
              {spec.isLink ? (
                <a href={spec.value} className="text-blue-500 hover:underline text-sm truncate">
                  {spec.value}
                </a>
              ) : (
                <span className="text-sm truncate">{spec.value}</span>
              )}
            </div>
          ))}
        </div>
        {extraContent && (
          <div className="mb-6">
            {extraContent}
          </div>
        )}
        {buttons && buttons.length > 0 && (
          <div className="flex justify-center space-x-4 mt-auto">
            {buttons.map((button, index) => {
              const lowerLabel = button.label.toLowerCase();
              let buttonColor;
              switch (lowerLabel) {
                case 'postuler':
                case 'valider':
                case 'validé':
                case 'rejoindre réunion':
                case 'évaluer':
                  buttonColor = 'bg-green-500 hover:bg-green-600';
                  break;
                case 'postulé':
                case 'annuler':
                  buttonColor = 'bg-gray-500 hover:bg-gray-600';
                  break;
                case 'demande en attente':
                case 'refusé temporairement':
                  buttonColor = 'bg-orange-500 hover:bg-orange-600';
                  break;
                case 'refuser':
                case 'refusé':
                  buttonColor = 'bg-red-500 hover:bg-red-600';
                  break;
                default:
                  buttonColor = 'bg-blue-500 hover:bg-blue-600'; // Default color
              }
              return (
                <button
                  key={index}
                  onClick={button.onClick}
                  className={`${buttonColor} text-white font-bold py-2 px-4 rounded-full transition duration-300 ease-in-out`}
                >
                  {button.label}
                </button>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
};

export default Card;