import { useState } from 'react'

const AccountManagement = ({ title, accounts: initialAccounts }) => {
  const [accounts, setAccounts] = useState(initialAccounts)
  const [searchTerm, setSearchTerm] = useState('')
  const [showForm, setShowForm] = useState(false)
  const [newAccount, setNewAccount] = useState({ name: '', username: '', password: '' })

  const filteredAccounts = accounts.filter(
    account => 
      account.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      account.id.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const handleCreate = () => {
    const id = Math.random().toString(36).substr(2, 9)
    setAccounts([...accounts, { ...newAccount, id }])
    setNewAccount({ name: '', username: '', password: '' })
    setShowForm(false)
  }

  const handleDelete = (id) => {
    setAccounts(accounts.filter(account => account.id !== id))
  }

  const handleEdit = (id) => {
    // In a real application, you'd implement editing logic here
    console.log('Editing account', id)
  }

  return (
    <div>
      <h1 className="text-3xl font-bold mb-8">{title}</h1>
      <div className="mb-4 flex justify-between items-center">
        <input
          type="text"
          placeholder="Search by name or ID"
          className="border p-2 rounded"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button
          className="bg-black text-white px-4 py-2 rounded"
          onClick={() => setShowForm(true)}
        >
          Create New Account
        </button>
      </div>
      {showForm && (
        <div className="mb-4 p-4 border rounded">
          <input
            type="text"
            placeholder="Name"
            className="border p-2 rounded mb-2 w-full"
            value={newAccount.name}
            onChange={(e) => setNewAccount({ ...newAccount, name: e.target.value })}
          />
          <input
            type="text"
            placeholder="Username"
            className="border p-2 rounded mb-2 w-full"
            value={newAccount.username}
            onChange={(e) => setNewAccount({ ...newAccount, username: e.target.value })}
          />
          <input
            type="password"
            placeholder="Password"
            className="border p-2 rounded mb-2 w-full"
            value={newAccount.password}
            onChange={(e) => setNewAccount({ ...newAccount, password: e.target.value })}
          />
          <button
            className="bg-black text-white px-4 py-2 rounded"
            onClick={handleCreate}
          >
            Create
          </button>
        </div>
      )}
      <table className="w-full border-collapse">
        <thead>
          <tr className="bg-gray-200">
            <th className="border p-2">ID</th>
            <th className="border p-2">Name</th>
            <th className="border p-2">Username</th>
            <th className="border p-2">Password</th>
            <th className="border p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredAccounts.map(account => (
            <tr key={account.id}>
              <td className="border p-2">{account.id}</td>
              <td className="border p-2">{account.name}</td>
              <td className="border p-2">{account.username}</td>
              <td className="border p-2">••••••••</td>
              <td className="border p-2">
                <button
                  className="bg-gray-200 text-black px-2 py-1 rounded mr-2"
                  onClick={() => handleEdit(account.id)}
                >
                  Edit
                </button>
                <button
                  className="bg-black text-white px-2 py-1 rounded"
                  onClick={() => handleDelete(account.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default AccountManagement

