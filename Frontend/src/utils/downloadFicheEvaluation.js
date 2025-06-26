import axiosInstance from '@/axiosInstance/axiosInstance';
import { jsPDF } from 'jspdf';

// Function to fetch evaluation data
async function fetchEvaluation(evaluationId) {
    const response = await axiosInstance.get(`/api/evaluations/${evaluationId}`);
    return response.data;
}

// Function to fetch stage data
async function fetchStage(stageId) {
    const response = await axiosInstance.get(`/api/stages/${stageId}`);
    return response.data;
}

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

// Function to generate and download the evaluation PDF
function generateAndDownloadEvaluationPdf(evaluation, stage, etudiant, ecole, encadrant, entreprise) {
    const doc = new jsPDF();

    // Set Times New Roman font
    doc.setFont("Times", "normal");

    // Add the title
    doc.setFontSize(18);
    doc.setFont("Times", "bold");
    doc.text(`Fiche d'Évaluation de Stage`, 10, 20);

    // Add evaluation information (emphasized)
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur l'Évaluation:`, 10, 30);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Note: ${evaluation.note}`, 10, 40);
    doc.text(`Compétences: ${evaluation.competances}`, 10, 50);
    doc.text(`Commentaire: ${evaluation.commentaire}`, 10, 60);

    // Add stage information
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur le Stage:`, 10, 80);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Titre du Stage: ${stage.titre}`, 10, 90);
    doc.text(`Description: ${stage.description}`, 10, 100);
    doc.text(`Date de Début: ${stage.dateDebut}`, 10, 110);
    doc.text(`Date de Fin: ${stage.dateFin}`, 10, 120);
    doc.text(`Durée: ${stage.duree}`, 10, 130);
    doc.text(`Localisation: ${stage.localisation}`, 10, 140);

    // Add student information
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur l'Étudiant:`, 10, 160);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Nom: ${etudiant.nom}`, 10, 170);
    doc.text(`Prénom: ${etudiant.prenom}`, 10, 180);
    doc.text(`Email: ${etudiant.email}`, 10, 190);
    doc.text(`Téléphone: ${etudiant.tel}`, 10, 200);

    // Add school information
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur l'École:`, 10, 220);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Nom de l'École: ${ecole.nomEcole}`, 10, 230);
    doc.text(`Ville: ${ecole.villeEcole}`, 10, 240);
    doc.text(`Adresse: ${ecole.adresseEcole}`, 10, 250);

    // Add school logo if it exists
    if (ecole.logo) {
        try {
            const logoBase64 = `data:image/png;base64,${btoa(
                String.fromCharCode(...new Uint8Array(ecole.logo))
            )}`;
            doc.addImage(logoBase64, 'PNG', 150, 220, 40, 20); // Adjust position and size
        } catch (error) {
            console.warn("Failed to add school logo. Skipping logo insertion:", error);
        }
    }

    // Add supervisor information
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur l'Encadrant:`, 10, 270);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Nom: ${encadrant.nom}`, 10, 280);
    doc.text(`Prénom: ${encadrant.prenom}`, 10, 290);
    doc.text(`Email: ${encadrant.email}`, 10, 300);
    doc.text(`Téléphone: ${encadrant.telephone}`, 10, 310);

    // Add company information
    doc.setFontSize(14);
    doc.setFont("Times", "bold");
    doc.text(`Informations sur l'Entreprise:`, 10, 330);
    doc.setFontSize(12);
    doc.setFont("Times", "normal");
    doc.text(`Nom de l'Entreprise: ${entreprise.nomEntreprise}`, 10, 340);
    doc.text(`Ville: ${entreprise.villeEntreprise}`, 10, 350);
    doc.text(`Adresse: ${entreprise.adresseEntreprise}`, 10, 360);
    doc.text(`Domaine: ${entreprise.domaineEntreprise}`, 10, 370);

    // Add company logo if it exists
    if (entreprise.logo) {
        try {
            const logoBase64 = `data:image/png;base64,${btoa(
                String.fromCharCode(...new Uint8Array(entreprise.logo))
            )}`;
            doc.addImage(logoBase64, 'PNG', 150, 330, 40, 20); // Adjust position and size
        } catch (error) {
            console.warn("Failed to add company logo. Skipping logo insertion:", error);
        }
    }

    // Save the PDF with a specific filename
    doc.save(`fiche_evaluation_stage_${evaluation.idEvaluation}.pdf`);
}

// Main function
async function getFicheEvaluation(evaluationDto) {
    // Fetch evaluation details
    const evaluation = await fetchEvaluation(evaluationDto.idEvaluation);

    // Fetch stage details
    const stage = await fetchStage(evaluationDto.stageId);

    // Fetch student details
    const etudiant = await fetchEtudiant(stage.etudiantId);

    // Fetch school details
    const ecole = await fetchEcole(etudiant.ecoleId);

    // Fetch supervisor details
    const encadrant = await fetchEncadrant(evaluationDto.encadrantId);

    // Fetch company details
    const entreprise = await fetchEntreprise(encadrant.entrepriseId);

    // Generate and download the PDF
    generateAndDownloadEvaluationPdf(evaluation, stage, etudiant, ecole, encadrant, entreprise);
}

// Export the function as the default export
export default getFicheEvaluation;