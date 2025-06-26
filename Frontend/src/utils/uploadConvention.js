import axiosInstance from '@/axiosInstance/axiosInstance';

async function uploadConvention(stageId) {
    // Create a form container
    const formContainer = document.createElement('div');
    formContainer.style.position = 'fixed';
    formContainer.style.top = '50%';
    formContainer.style.left = '50%';
    formContainer.style.transform = 'translate(-50%, -50%)';
    formContainer.style.backgroundColor = '#ffffff';
    formContainer.style.padding = '20px';
    formContainer.style.borderRadius = '8px';
    formContainer.style.boxShadow = '0 4px 12px rgba(0, 0, 0, 0.15)';
    formContainer.style.zIndex = '1000';
    formContainer.style.width = '300px';
    formContainer.style.textAlign = 'center';

    // Create a label
    const label = document.createElement('label');
    label.textContent = 'Téléverser la Convention de Stage (PDF, max 4 Go)';
    label.style.display = 'block';
    label.style.marginBottom = '16px';
    label.style.fontSize = '14px';
    label.style.color = '#333333';

    // Create a drag-and-drop area
    const dropArea = document.createElement('div');
    dropArea.style.border = '2px dashed #cccccc';
    dropArea.style.borderRadius = '8px';
    dropArea.style.padding = '20px';
    dropArea.style.marginBottom = '16px';
    dropArea.style.cursor = 'pointer';
    dropArea.textContent = 'Glissez-déposez un fichier ici ou cliquez pour parcourir';
    dropArea.style.color = '#666666';

    // Create a file input (hidden)
    const input = document.createElement('input');
    input.type = 'file';
    input.accept = 'application/pdf';
    input.style.display = 'none';

    // Create a file name display area
    const fileNameDisplay = document.createElement('div');
    fileNameDisplay.style.marginTop = '8px';
    fileNameDisplay.style.fontSize = '12px';
    fileNameDisplay.style.color = '#333333';

    // Create a submit button
    const submitButton = document.createElement('button');
    submitButton.textContent = 'Soumettre';
    submitButton.style.marginTop = '16px';
    submitButton.style.padding = '8px 16px';
    submitButton.style.backgroundColor = '#333333';
    submitButton.style.color = '#ffffff';
    submitButton.style.border = 'none';
    submitButton.style.borderRadius = '4px';
    submitButton.style.cursor = 'pointer';
    submitButton.disabled = true; // Disabled by default until a file is selected

    // Create an error message container
    const errorMessage = document.createElement('div');
    errorMessage.style.color = '#ff0000';
    errorMessage.style.fontSize = '12px';
    errorMessage.style.marginTop = '8px';
    errorMessage.style.display = 'none';

    // Create a close button
    const closeButton = document.createElement('button');
    closeButton.textContent = 'Fermer';
    closeButton.style.marginTop = '8px';
    closeButton.style.padding = '8px 16px';
    closeButton.style.backgroundColor = '#cccccc';
    closeButton.style.color = '#333333';
    closeButton.style.border = 'none';
    closeButton.style.borderRadius = '4px';
    closeButton.style.cursor = 'pointer';

    // Append elements to the form container
    formContainer.appendChild(label);
    formContainer.appendChild(dropArea);
    formContainer.appendChild(fileNameDisplay);
    formContainer.appendChild(input);
    formContainer.appendChild(submitButton);
    formContainer.appendChild(closeButton);
    formContainer.appendChild(errorMessage);

    // Add the form container to the document body
    document.body.appendChild(formContainer);

    // Handle file selection via browsing
    dropArea.onclick = () => input.click();
    input.onchange = (event) => handleFile(event.target.files[0]);

    // Handle drag-and-drop functionality
    dropArea.ondragover = (event) => {
        event.preventDefault();
        dropArea.style.borderColor = '#333333';
    };

    dropArea.ondragleave = () => {
        dropArea.style.borderColor = '#cccccc';
    };

    dropArea.ondrop = (event) => {
        event.preventDefault();
        dropArea.style.borderColor = '#cccccc';

        // Get the dropped file
        const file = event.dataTransfer.files[0];
        if (file) {
            // Update the file input with the dropped file
            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(file);
            input.files = dataTransfer.files;

            // Handle the file
            handleFile(file);
        }
    };

    // Function to handle file validation and display file name
    const handleFile = (file) => {
        if (file) {
            // Display the file name
            fileNameDisplay.textContent = `Fichier sélectionné : ${file.name}`;

            // Check file size (max 4 GB)
            if (file.size > 4294967296) {
                errorMessage.textContent = 'La taille du fichier dépasse la limite de 4 Go.';
                errorMessage.style.display = 'block';
                submitButton.disabled = true; // Disable submit button if file is too large
            } else if (file.type !== 'application/pdf') {
                errorMessage.textContent = 'Veuillez sélectionner un fichier PDF.';
                errorMessage.style.display = 'block';
                submitButton.disabled = true; // Disable submit button if file is not a PDF
            } else {
                errorMessage.style.display = 'none';
                submitButton.disabled = false; // Enable submit button if file is valid
            }
        }
    };

    // Handle form submission
    submitButton.onclick = async () => {
        const file = input.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('file', file);

            try {
                const response = await axiosInstance.post(`/stages/${stageId}/upload-convention`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });

                if (response.status === 200) {
                    // Success message
                    errorMessage.textContent = 'Convention de stage téléversée avec succès.';
                    errorMessage.style.color = '#00aa00';
                    errorMessage.style.display = 'block';

                    // Close the form after 2 seconds
                    setTimeout(() => {
                        if (document.body.contains(formContainer)) {
                            document.body.removeChild(formContainer);
                        }
                    }, 1000);
                } else {
                    errorMessage.textContent = 'Erreur lors du téléversement de la convention de stage.';
                    errorMessage.style.display = 'block';
                }
            } catch (error) {
                console.error('Erreur:', error);
                errorMessage.textContent = 'Erreur lors du téléversement de la convention de stage.';
                errorMessage.style.display = 'block';
            }
        } else {
            errorMessage.textContent = 'Veuillez sélectionner un fichier avant de soumettre.';
            errorMessage.style.display = 'block';
        }
    };

    // Close the form when the close button is clicked
    closeButton.onclick = () => {
        if (document.body.contains(formContainer)) {
            document.body.removeChild(formContainer);
        }
    };
}

export default uploadConvention;