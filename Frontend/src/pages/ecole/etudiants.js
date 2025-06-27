import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import FormComponent from '@/components/FormComponent';
import Table from '@/components/Table';
import axiosInstance from '@/axiosInstance/axiosInstance';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import * as XLSX from 'xlsx';

export default function StudentsManagement() {
  const router = useRouter();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [students, setStudents] = useState([]);
  const [filteredStudents, setFilteredStudents] = useState([]);
  const [formData, setFormData] = useState({});
  const [editStudentId, setEditStudentId] = useState(null);
  const [ecoleId, setEcoleId] = useState(null);
  const [filieres, setFilieres] = useState([]);
  const [error, setError] = useState(null);
  const [file, setFile] = useState(null);
  const [showFileUploadMessage, setShowFileUploadMessage] = useState(true); // Show the message from the beginning
  const [compteEcoleId, setCompteEcoleId] = useState(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem("token");
      const id = localStorage.getItem('id');
      if (token && id) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        setCompteEcoleId(id);
      } else {
        router.push('/');
      }
    }
  }, [router]);

  useEffect(() => {
    if (compteEcoleId) {
      fetchEcoleId();
    }
  }, [compteEcoleId]);

  const fetchEcoleId = async () => {
    try {
      
      const response = await axiosInstance.get(`/compte-ecoles/${compteEcoleId}`);
      const ecoleId = response.data.ecoleId;
      setEcoleId(ecoleId);
      fetchStudentsByEcoleId(ecoleId);
      fetchFilieresByEcoleId(ecoleId);
    } catch (error) {
      console.error('Error fetching Ecole ID:', error);
      setError('Erreur lors de la récupération de l\'ID de l\'école. Veuillez réessayer.');
    }
  };

  const fetchStudentsByEcoleId = async (ecoleId) => {
    try {
      const response = await axiosInstance.get(`/api/etudiants/ecole/${ecoleId}`);
      setStudents(response.data);
      setFilteredStudents(response.data);
    } catch (error) {
      console.error('Error fetching students:', error);
      setError('Erreur lors de la récupération des étudiants. Veuillez réessayer.');
    }
  };

  const fetchFilieresByEcoleId = async (ecoleId) => {
    try {
      const response = await axiosInstance.get(`/api/filieres/ecole/${ecoleId}`);
      setFilieres(response.data);
    } catch (error) {
      console.error('Error fetching filieres:', error);
      setError('Erreur lors de la récupération des filières. Veuillez réessayer.');
    }
  };

  const handleSearch = (query) => {
    if (query.trim() === '') {
      setFilteredStudents(students);
      return;
    }
    const filtered = students.filter(student =>
      student.nom.toLowerCase().includes(query.toLowerCase()) ||
      student.prenom.toLowerCase().includes(query.toLowerCase()) ||
      student.email.toLowerCase().includes(query.toLowerCase())
    );
    setFilteredStudents(filtered);
  };

  const handleCreateStudent = async (studentData) => {
    try {
      const selectedFiliere = filieres.find(filiere => filiere.nomFiliere === studentData.filiereNom);

      if (!selectedFiliere) {
        setError('Filière non trouvée. Veuillez sélectionner une filière valide.');
        return;
      }

      await axiosInstance.post(`/api/admins/send-password/${studentData.email}/${studentData.motDePasse}`)
      
      const studentPayload = {
        nom: studentData.nom,
        prenom: studentData.prenom,
        email: studentData.email,
        tel: studentData.telephone,
        motDePasse: studentData.motDePasse,
        codeEtu: studentData.codeEtu,
        statutEtudiant: studentData.statutEtudiant,
        ecoleId: ecoleId,
        filiereId: selectedFiliere.idFiliere,
      };

      const response = await axiosInstance.post('/api/etudiants', studentPayload);

      const updatedStudents = [...students, response.data];
      const updatedFilteredStudents = [...filteredStudents, response.data];

      setStudents(updatedStudents);
      setFilteredStudents(updatedFilteredStudents);
      setIsModalOpen(false);
      setError(null);

    } catch (error) {
      setError('Erreur lors de la création de l\'étudiant. Veuillez réessayer.');
    }
  };

  const handleEditStudent = (id) => {
    const studentToEdit = students.find(student => student.idEtu === id);
    setFormData({
      ...studentToEdit,
      filiereNom: filieres.find(filiere => filiere.idFiliere === studentToEdit.filiereId)?.nomFiliere || '',
    });
    setEditStudentId(id);
    setIsModalOpen(true);
    setError(null);
  };

  const handleSaveEdit = async (studentData) => {
    try {
      const selectedFiliere = filieres.find(filiere => filiere.nomFiliere === studentData.filiereNom);
      if (!selectedFiliere) {
        setError('Filière non trouvée. Veuillez sélectionner une filière valide.');
        return;
      }

      const response = await axiosInstance.put(`/api/etudiants/${editStudentId}`, {
        nom: studentData.nom,
        prenom: studentData.prenom,
        email: studentData.email,
        tel: studentData.telephone,
        motDePasse: studentData.motDePasse,
        codeEtu: studentData.codeEtu,
        statutEtudiant: studentData.statutEtudiant,
        ecoleId: ecoleId,
        filiereId: selectedFiliere.idFiliere,
      });
      const updatedStudents = students.map(student =>
        student.idEtu === editStudentId ? response.data : student
      );
      setStudents(updatedStudents);
      setFilteredStudents(updatedStudents);
      setIsModalOpen(false);
      setEditStudentId(null);
      setError(null);
    } catch (error) {
      console.error('Error updating student:', error);
      setError('Erreur lors de la mise à jour de l\'étudiant. Veuillez réessayer.');
    }
  };

  const handleFileUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
      setFile(file);
      processFile(file);
    }
  };

  const processFile = (file) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      const data = new Uint8Array(e.target.result);
      const workbook = XLSX.read(data, { type: 'array' });
      const sheetName = workbook.SheetNames[0];
      const worksheet = workbook.Sheets[sheetName];
      const json = XLSX.utils.sheet_to_json(worksheet);

      // Assuming the Excel file has columns: nom, prenom, email, telephone, motDePasse, codeEtu, statutEtudiant, filiereNom
      const studentsFromFile = json.map(row => ({
        nom: row.nom,
        prenom: row.prenom,
        email: row.email,
        telephone: row.telephone,
        motDePasse: row.motDePasse,
        codeEtu: row.codeEtu,
        statutEtudiant: row.statutEtudiant,
        filiereNom: row.filiereNom,
      }));

      // Now you can send these students to the backend
      handleBulkCreateStudents(studentsFromFile);
    };
    reader.readAsArrayBuffer(file);
  };

  const handleBulkCreateStudents = async (studentsData) => {
    try {
      const responses = await Promise.all(
        studentsData.map(async (studentData) => {
          // Check if the filiere exists
          let selectedFiliere = filieres.find(filiere => filiere.nomFiliere === studentData.filiereNom);

          // If the filiere does not exist, create it
          if (!selectedFiliere) {
            try {
              const newFiliereResponse = await axiosInstance.post('/api/filieres', {
                nomFiliere: studentData.filiereNom,
                ecoleId: ecoleId, // Use the ecoleId from the state
              });

              // Add the new filiere to the filieres list
              setFilieres((prevFilieres) => [...prevFilieres, newFiliereResponse.data]);

              // Use the newly created filiere
              selectedFiliere = newFiliereResponse.data;
            } catch (error) {
              console.error('Error creating filiere:', error);
              throw new Error(`Erreur lors de la création de la filière: ${studentData.filiereNom}`);
            }
          }

          // Create the student with the selected filiere
          return axiosInstance.post('/api/etudiants', {
            nom: studentData.nom,
            prenom: studentData.prenom,
            email: studentData.email,
            tel: studentData.telephone,
            motDePasse: studentData.motDePasse,
            codeEtu: studentData.codeEtu,
            statutEtudiant: studentData.statutEtudiant,
            ecoleId: ecoleId,
            filiereId: selectedFiliere.idFiliere,
          });
        })
      );

      // Add the new students to the students list
      const newStudents = responses.map(response => response.data);
      setStudents((prevStudents) => [...prevStudents, ...newStudents]);
      setFilteredStudents((prevFilteredStudents) => [...prevFilteredStudents, ...newStudents]);
      setError(null);
    } catch (error) {
      console.error('Error creating students:', error);
      setError('Erreur lors de la création des étudiants. Veuillez réessayer.');
    }
  };

  const studentFields = [
    { name: 'nom', placeholder: 'Nom', type: 'text' },
    { name: 'prenom', placeholder: 'Prénom', type: 'text' },
    { name: 'email', placeholder: 'Email', type: 'email' },
    { name: 'telephone', placeholder: 'Téléphone', type: 'text' },
    { name: 'motDePasse', placeholder: 'Mot de passe', type: 'password' },
    { name: 'codeEtu', placeholder: 'Code Étudiant', type: 'text' },
    { name: 'statutEtudiant', placeholder: 'Statut Étudiant', type: 'text' },
    {
      name: 'filiereNom',
      placeholder: 'Filière',
      type: 'select',
      options: filieres.map(filiere => ({ value: filiere.nomFiliere, label: filiere.nomFiliere })),
    },
  ];

  const getFiliereName = (filiereId) => {
    const filiere = filieres.find(filiere => filiere.idFiliere === filiereId);
    return filiere ? filiere.nomFiliere : 'N/A';
  };

  return (
    <Layout role="university">
      <h1 className="text-3xl font-bold mb-12 mt-12">Gestion des Étudiants</h1>
      <div className="flex justify-end items-center mb-6">
        <div className="flex gap-4">
          <label className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 cursor-pointer">
            Choisir un fichier Excel
            <input
              type="file"
              accept=".xlsx, .csv"
              onChange={handleFileUpload}
              className="hidden"
            />
          </label>
          <button
            onClick={() => {
              setFormData({});
              setIsModalOpen(true);
            }}
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
          >
            Créer un étudiant
          </button>
        </div>
      </div>

      {showFileUploadMessage && (
        <div className="bg-yellow-100 border border-yellow-400 text-yellow-700 px-4 py-3 rounded relative mb-4" role="alert">
          <span className="block sm:inline">
            <strong>Note :</strong> Cette implémentation suppose que le fichier Excel/CSV contient des colonnes spécifiques (<code>nom</code>, <code>prenom</code>, <code>email</code>, <code>telephone</code>, <code>motDePasse</code>, <code>codeEtu</code>, <code>statutEtudiant</code>, <code>filiereNom</code>). Vous devrez peut-être ajuster les noms des colonnes en fonction de la structure de votre fichier.
          </span>
        </div>
      )}

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
          <span className="block sm:inline">{error}</span>
        </div>
      )}

      <Table
        columns={['ID', 'Nom', 'Prénom', 'Email', 'Téléphone', 'Code Étudiant', 'Statut', 'Filière']}
        columnKeys={['idEtu', 'nom', 'prenom', 'email', 'tel', 'codeEtu', 'statutEtudiant','filiere']}
        items={filteredStudents.map(student => ({
          ...student,
          filiere: getFiliereName(student.filiereId),
        }))}
        buttons={['Modifier']}
        actions={[handleEditStudent]}
        idParam="idEtu"
      />

      <FormComponent
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditStudentId(null);
        }}
        onSubmit={editStudentId ? handleSaveEdit : handleCreateStudent}
        fields={studentFields}
        title={editStudentId ? "Modifier l&apos;étudiant" : "Créer un nouvel étudiant"}
        submitButtonText={editStudentId ? "Enregistrer" : "Créer"}
        prefillData={formData}
      />
    </Layout>
  );
}