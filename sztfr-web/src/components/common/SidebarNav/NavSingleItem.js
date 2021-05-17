import React from "react";
import { NavLink } from "react-router-dom";
import * as Feather from "react-feather";
import { Badge } from "reactstrap";

export default function NavSingleItem({ item }) {
  const Icon = item.icon && Feather[item.icon] ? Feather[item.icon] : null;
  const url = item.url.charAt(0) === "/" ? item.url : `/${item.url}`;

  return (
    <li className="nav-item">
      <NavLink to={url} activeClassName="active">
        {item.icon && Icon && <Icon className="side-nav-icon" />}
        <span className="nav-item-label">{item.name}</span>
        {item.badge && (
          <Badge color={item.badge.variant}>{item.badge.text}</Badge>
        )}
      </NavLink>
    </li>
  );
}
