import React from 'react';
import { Badge } from 'reactstrap';

export default function NavBadge({ color, text }) {
  return <Badge color={color}>{text}</Badge>;
}
