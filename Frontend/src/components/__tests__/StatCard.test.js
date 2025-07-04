import { render, screen } from '@testing-library/react';
import StatCard from '../StatCard';
import { FaUserGraduate } from 'react-icons/fa'; // Using a sample icon for the test

describe('StatCard', () => {
  it('should render the title, value, and icon passed as props', () => {
    const testTitle = 'Total Students';
    const testValue = '1,500';
    // The icon can be any React node. We pass one with a test ID to make it findable.
    const testIcon = <FaUserGraduate data-testid="icon" />;

    render(<StatCard title={testTitle} value={testValue} iconCard={testIcon} />);

    // Assert that the title is in the document
    expect(screen.getByText(testTitle)).toBeInTheDocument();

    // Assert that the value is in the document
    expect(screen.getByText(testValue)).toBeInTheDocument();

    // Assert that the icon is in the document by its test ID
    expect(screen.getByTestId('icon')).toBeInTheDocument();
  });
});
