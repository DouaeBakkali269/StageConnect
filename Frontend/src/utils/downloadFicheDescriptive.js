import axiosInstance from '@/axiosInstance/axiosInstance';
import { jsPDF } from 'jspdf';

// Function to fetch student data
async function fetchEtudiant(etudiantId) {
    const response = await axiosInstance.get(`/api/etudiants/${etudiantId}`);
    return response.data;
}

// Function to fetch school data
async function fetchEcole(ecoleId) {
    const response = await axiosInstance.get(`/api/ecoles/${ecoleId}`);
    return response.data;
}

// Function to fetch supervisor data
async function fetchEncadrant(encadrantId) {
    const response = await axiosInstance.get(`/api/encadrants/${encadrantId}`);
    return response.data;
}

// Function to fetch company data
async function fetchEntreprise(entrepriseId) {
    const response = await axiosInstance.get(`/api/entreprises/${entrepriseId}`);
    return response.data;
}

// Function to generate and download PDF
function generateAndDownloadPdf(stage, etudiant, ecole, encadrant, entreprise) {
    const doc = new jsPDF();

    // Set Times New Roman font
    doc.setFont("Times", "normal");

    // Add the title
    doc.setFontSize(18);
    doc.setFont("Times", "bold");
    doc.text(`Fiche Descriptive de Stage`, 10, 20);

    // Add stage information
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur le Stage:`, 10, 30);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Titre du Stage: ${stage.titre}`, 10, 40);
    doc.text(`Description: ${stage.description}`, 10, 50);
    doc.text(`Date de Début: ${stage.dateDebut}`, 10, 60);
    doc.text(`Date de Fin: ${stage.dateFin}`, 10, 70);
    doc.text(`Durée: ${stage.duree}`, 10, 80);
    doc.text(`Localisation: ${stage.localisation}`, 10, 90);
    doc.text(`Montant de Rémunération: ${stage.montantRemuneration}`, 10, 100);
    doc.text(`Statut: ${stage.statut}`, 10, 110);
    doc.text(`Type: ${stage.type}`, 10, 120);

    // Add student information
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur l'Étudiant:`, 10, 130);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Nom: ${etudiant.nom}`, 10, 140);
    doc.text(`Prénom: ${etudiant.prenom}`, 10, 150);
    doc.text(`Email: ${etudiant.email}`, 10, 160);
    doc.text(`Téléphone: ${etudiant.tel}`, 10, 170);

    // Add school information
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur l'École:`, 10, 190);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Nom de l'École: ${ecole.nomEcole}`, 10, 200);
    doc.text(`Ville: ${ecole.villeEcole}`, 10, 210);
    doc.text(`Adresse: ${ecole.adresseEcole}`, 10, 220);

    // Add school logo if it exists
    if (ecole.logo) {
        try {
            const logoBase64 = `data:image/png;base64,${btoa(
                String.fromCharCode(...new Uint8Array(ecole.logo))
            )}`;
            doc.addImage(logoBase64, 'PNG', 150, 190, 40, 20); // Adjust position and size
        } catch (error) {
            console.warn("Failed to add school logo. Skipping logo insertion:", error);
        }
    }

    // Add supervisor information
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur l'Encadrant:`, 10, 240);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Nom: ${encadrant.nom}`, 10, 250);
    doc.text(`Prénom: ${encadrant.prenom}`, 10, 260);
    doc.text(`Email: ${encadrant.email}`, 10, 270);
    doc.text(`Téléphone: ${encadrant.telephone}`, 10, 280);

    // Add company information
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur l'Entreprise:`, 10, 300);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Nom de l'Entreprise: ${entreprise.nomEntreprise}`, 10, 310);
    doc.text(`Ville: ${entreprise.villeEntreprise}`, 10, 320);
    doc.text(`Adresse: ${entreprise.adresseEntreprise}`, 10, 330);
    doc.text(`Domaine: ${entreprise.domaineEntreprise}`, 10, 340);

    // Add company logo if it exists
    if (entreprise.logo) {
        try {
            const logoBase64 = `data:image/png;base64,${btoa(
                String.fromCharCode(...new Uint8Array(entreprise.logo))
            )}`;
            doc.addImage(logoBase64, 'PNG', 150, 300, 40, 20); // Adjust position and size
        } catch (error) {
            console.warn("Failed to add company logo. Skipping logo insertion:", error);
        }
    }

    // Save the PDF with a specific filename
    doc.save(`fiche_descriptive_stage_${stage.idStage}.pdf`);
}

// Main function
async function getFicheDescriptiveDeStage(stageDto) {
    const etudiant = await fetchEtudiant(stageDto.etudiantId);
    const ecole = await fetchEcole(etudiant.ecoleId);
    const encadrant = await fetchEncadrant(stageDto.encadrantId);
    const entreprise = await fetchEntreprise(encadrant.entrepriseId);

    // Generate and download the PDF
    generateAndDownloadPdf(stageDto, etudiant, ecole, encadrant, entreprise);
}

// Export the function as the default export
export default getFicheDescriptiveDeStage;