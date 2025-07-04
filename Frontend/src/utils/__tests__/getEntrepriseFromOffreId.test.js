import getEntrepriseFromOffreId from '../getEntrepriseFromOffreId';
import axiosInstance from '@/axiosInstance/axiosInstance';
import MockAdapter from 'axios-mock-adapter';

describe('getEntrepriseFromOffreId', () => {
  let mock;

  beforeEach(() => {
    mock = new MockAdapter(axiosInstance);
  });

  afterEach(() => {
    mock.restore();
  });

  it('should return entreprise data on successful API calls', async () => {
    const offreId = 1;
    const entrepriseId = 123;
    const offreResponse = { data: { entrepriseId: entrepriseId } };
    const entrepriseResponse = { data: { id: entrepriseId, name: 'Test Corp' } };

    // Mock the API calls
    mock.onGet(`/api/offres/${offreId}`).reply(200, offreResponse.data);
    mock.onGet(`/api/entreprises/${entrepriseId}`).reply(200, entrepriseResponse.data);

    const result = await getEntrepriseFromOffreId(offreId);

    expect(result).toEqual(entrepriseResponse.data);
  });

  it('should return null if the first API call fails', async () => {
    const offreId = 1;

    // Mock the API call to return an error
    mock.onGet(`/api/offres/${offreId}`).networkError();

    // Spy on console.error to check if it's called
    const consoleSpy = jest.spyOn(console, 'error').mockImplementation(() => {});

    const result = await getEntrepriseFromOffreId(offreId);

    expect(result).toBeNull();
    expect(consoleSpy).toHaveBeenCalled();

    consoleSpy.mockRestore();
  });

  it('should return null if the second API call fails', async () => {
    const offreId = 1;
    const entrepriseId = 123;
    const offreResponse = { data: { entrepriseId: entrepriseId } };

    // Mock the first call to succeed and the second to fail
    mock.onGet(`/api/offres/${offreId}`).reply(200, offreResponse.data);
    mock.onGet(`/api/entreprises/${entrepriseId}`).networkError();

    const consoleSpy = jest.spyOn(console, 'error').mockImplementation(() => {});

    const result = await getEntrepriseFromOffreId(offreId);

    expect(result).toBeNull();
    expect(consoleSpy).toHaveBeenCalled();

    consoleSpy.mockRestore();
  });
});
