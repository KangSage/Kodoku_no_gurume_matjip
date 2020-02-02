import React, { ButtonHTMLAttributes, DetailedHTMLProps } from 'react';
import styled from 'styled-components';
import { Button } from '@material-ui/core';

type ButtonSize = 'SMALL' | 'DEFAULT' | 'LARGE' | 'FULL';

type ColorType =
  | 'teal'
  | 'gray'
  | 'darkGray'
  | 'lightGray'
  | 'orange'
  | 'darkBlue'
  | 'white';

type ButtonsProps = DetailedHTMLProps<
  ButtonHTMLAttributes<HTMLButtonElement>,
  HTMLButtonElement
>;

interface ButtonProps extends ButtonsProps {
  color?: ColorType;
  inline?: boolean;
  size: ButtonSize;
  to?: string;
  sticky?: boolean;
  disabled?: boolean;
}

const MaterialStyledButton = styled(Button)`
  background: linear-gradient(45deg, #fe6b8b 30%, #ff8e53 90%);
  border-radius: 3px;
  border: 0;
  color: white;
  height: 48px;
  padding: 0 30px;
  box-shadow: 0 3px 5px 2px rgba(255, 105, 135, 0.3);
  text-transform: none;
`;

const DefaultButton: React.FC = () => {
  return <MaterialStyledButton>Material Styled Button</MaterialStyledButton>;
};

export default DefaultButton;
