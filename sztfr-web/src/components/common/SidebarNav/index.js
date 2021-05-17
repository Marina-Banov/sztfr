import React from "react";

import navigation from "./navigation";
import { PageAlertContext } from "components/common";
import Logo from "assets/images/vibe-logo.svg";
import NavSingleItem from "./NavSingleItem";
import NavDropdownItem from "./NavDropdownItem";

export default function SidebarNav({ toggleSidebar, isSidebarCollapsed }) {
  const navItems = (items) => {
    return items.map((item, index) => itemType(item, index));
  };

  const itemType = (item, index) => {
    if (item.children) {
      return (
        <NavDropdownItem
          key={index}
          item={item}
          isSidebarCollapsed={isSidebarCollapsed}
        />
      );
    } else if (item.divider) {
      return <li key={index} className="separator" />;
    } else {
      return <NavSingleItem item={item} key={index} />;
    }
  };

  const NavBrand = ({ logo, logoText }) => {
    const screenReaderLabel = isSidebarCollapsed
      ? "Expand Sidebar Navigation"
      : "Collapse Sidebar Navigation";

    return (
      <div className="site-logo-bar">
        <button
          onClick={toggleSidebar}
          className="navbar-brand"
          aria-label={screenReaderLabel}
        >
          {logo && <img src={logo} alt="" />}
          {logoText && <span className="logo-text">{logoText}</span>}
        </button>
      </div>
    );
  };

  return (
    <PageAlertContext.Consumer>
      {(consumer) => {
        const hasPageAlertClass = consumer.alert ? "has-alert" : "";
        return (
          <div>
            <div className={`app-sidebar ${hasPageAlertClass}`}>
              <NavBrand logo={Logo} logoText="SZTFR" />
              <nav>
                <ul id="main-menu">
                  {navItems(navigation.top)}
                  <li className="nav-item nav-item-spacer" />
                  {navItems(navigation.bottom)}
                </ul>
              </nav>
            </div>
            {isSidebarCollapsed && (
              <div className="sidebar-overlay" onClick={toggleSidebar} />
            )}
          </div>
        );
      }}
    </PageAlertContext.Consumer>
  );
}
