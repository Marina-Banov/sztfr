import React, { useState } from "react";
import * as Feather from "react-feather";
import { Badge } from "reactstrap";

import NavSingleItem from "./NavSingleItem";

export default function NavDropdownItem({ item, isSidebarCollapsed }) {
  const [open, setOpen] = useState(false);
  const Icon = item.icon ? Feather[item.icon] : null;
  const ExpandIcon = open ? Feather.ChevronDown : Feather.ChevronRight;

  function toggle(e) {
    setOpen(!open);
    e.preventDefault();
    e.stopPropagation();
  }

  return (
    <li className={`nav-item has-submenu ${open ? "open" : ""}`}>
      <a href="#!" role="button" onClick={toggle}>
        {item.icon && Icon && <Icon className="side-nav-icon" />}
        <span className="nav-item-label">{item.name}</span>{" "}
        {item.badge && (
          <Badge color={item.badge.variant}>{item.badge.text}</Badge>
        )}
        <ExpandIcon className="menu-expand-icon" />
      </a>
      {(open || isSidebarCollapsed) && (
        <ul className="nav-submenu">
          {item.children.map((item, index) => (
            <NavSingleItem item={item} key={index} />
          ))}
        </ul>
      )}
    </li>
  );
}
